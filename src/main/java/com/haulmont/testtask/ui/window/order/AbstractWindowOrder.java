package com.haulmont.testtask.ui.window.order;

import com.haulmont.testtask.ui.converter.StringToDoubleConverter;
import com.haulmont.testtask.ui.layout.main.MainUI;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import java.util.Date;

public abstract class AbstractWindowOrder extends Window {

    private TextField description = new TextField("Description");
    private NativeSelect selectClient = new NativeSelect("client");
    private NativeSelect selectMechanic = new NativeSelect("Mechanic");
    private DateField dateStart;
    private DateField dateFinish;
    private TextField cost = new TextField("Cost");
    private NativeSelect selectStatus = new NativeSelect("Status");
    private Button ok;
    private Button cancel = new Button("Cancel", event -> close());
    private Date today = new Date();

    protected AbstractWindowOrder(String str){
        super(str);
    }

    protected void preload(){}

    protected void preload(Long id){}

    protected void buildWindow() {
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        description.setMaxLength(100);
        cost.setMaxLength(19);
        ok.setEnabled(false);

        VerticalLayout verticalFields = new VerticalLayout(description, selectClient, selectMechanic,
                dateStart, dateFinish, cost, selectStatus);
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        HorizontalLayout horizonButtons = new HorizontalLayout(ok, cancel);
        horizonButtons.setSpacing(false);
        horizonButtons.setMargin(false);

        VerticalLayout verticalMain = new VerticalLayout(verticalFields, horizonButtons);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        setContent(verticalMain);
    }

    protected void validation() {
        //VALIDATION
        description.addValidator(new StringLengthValidator("Prompt is empty.",
                1, 50, false));

        selectClient.addValueChangeListener(event -> selectChange(event, selectClient));
        selectMechanic.addValueChangeListener(event -> selectChange(event, selectMechanic));
        selectStatus.addValueChangeListener(event -> selectChange(event, selectStatus));

        cost.setRequired(true);
        cost.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        cost.setConverter(new StringToDoubleConverter());
        cost.addValidator(new DoubleRangeValidator("Value is negative", 0.0, Double.MAX_VALUE));

        //What if text field is empty - integer will be null in that case, so show blank when null
        cost.setNullRepresentation("");

        dateStart.addValueChangeListener(event -> dateChange());
        dateFinish.addValueChangeListener(event -> dateChange());

        DateRangeValidator dateStartValidator =  new DateRangeValidator("Wrong date", null, today,
                Resolution.DAY);
        DateRangeValidator dateFinishValidator =  new DateRangeValidator("Wrong date", today,null ,
                Resolution.DAY);

        dateStart.addValidator(dateStartValidator);
        dateFinish.addValidator(dateFinishValidator);

        description.setValidationVisible(true);
        cost.setValidationVisible(true);

        description.setImmediate(true);
        cost.setImmediate(true);

        description.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        cost.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        cost.addTextChangeListener(event -> textChange(event, cost));
        description.addTextChangeListener(event -> textChange(event, description));
    }

    private void commonValidator(){
        try {
            cost.validate();
            description.validate();
            dateStart.validate();
            dateFinish.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField) {
        textField.setValue(event.getText());
        textField.setCursorPosition(event.getCursorPosition());
        commonValidator();
    }

    private void dateChange(){
        if(dateStart.isEmpty()){
            dateStart.setValue(today);
        }
        if(dateFinish.isEmpty()){
            dateFinish.setValue(today);
        }

        commonValidator();
    }

    private void selectChange(Property.ValueChangeEvent event, NativeSelect select) {
        select.setValue(event.getProperty().getValue());
        commonValidator();
    }

    protected TextField getDescript() {
        return description;
    }

    protected NativeSelect getSelectClient() {
        return selectClient;
    }

    protected NativeSelect getSelectMechanic() {
        return selectMechanic;
    }

    protected DateField getDateStart() {
        return dateStart;
    }

    protected DateField getDateFinish() {
        return dateFinish;
    }

    protected TextField getCost() {
        return cost;
    }

    protected NativeSelect getSelectStatus() {
        return selectStatus;
    }

    protected void setDateStart(DateField dateStart){
        this.dateStart=dateStart;
    }

    protected void setDateFinish(DateField dateFinish)
    {
        this.dateFinish=dateFinish;
    }

    protected void setOk(Button ok){
        this.ok=ok;
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }
}

package com.haulmont.testtask.ui.window.mechanic;

import com.haulmont.testtask.ui.converter.StringToDoubleConverter;
import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

import java.sql.SQLException;

import com.haulmont.testtask.ui.layout.main.MainUI;
import com.haulmont.testtask.dao.MechanicDAO;
import com.haulmont.testtask.model.Mechanic;

public class WindowEditMechanic extends Window {
    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField patronymic = new TextField("Patronymic");
    private TextField hourlyPay = new TextField("Hourly Pay");
    private Button ok = new Button("OK", this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private Long id;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowEditMechanic(long id){
        super("Edit client"); // Set window caption
        preload(id);
        buildWindow();
        validation();
    }

    private void buildWindow(){
        center(); //Position of window
        setClosable(true); // Enable the close button
        setModal(true); // Enable modal window mode

        name.setMaxLength(50);
        surname.setMaxLength(50);
        patronymic.setMaxLength(50);
       //hourlyPay.setMaxLength(19);

        VerticalLayout verticalFields = new VerticalLayout (name, surname, patronymic, hourlyPay);
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        HorizontalLayout horizontalButtons = new HorizontalLayout(ok,cancel);
        horizontalButtons.setSpacing(false);
        horizontalButtons.setMargin(false);

        VerticalLayout verticalMain = new VerticalLayout (verticalFields,horizontalButtons);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        setContent(verticalMain);
    }

    private void preload(Long id){
        this.id = id;
        try {
            Mechanic mechanic = MechanicDAO.getInstance().load(id);
            name.setValue(mechanic.getName());
            surname.setValue(mechanic.getSurname());
            patronymic.setValue(mechanic.getPatronymic());
            hourlyPay.setValue(Double.toString(mechanic.getHourlyPay()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validation(){
        name.addValidator(stringLengthValidator);
        surname.addValidator(stringLengthValidator);
        patronymic.addValidator(stringLengthValidator);

        hourlyPay.setRequired(true);
        hourlyPay.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        //hourlyPay.setConverter(new StringToDoubleConverter());
        hourlyPay.setConverter(new StringToDoubleConverter());

        hourlyPay.addValidator(new DoubleRangeValidator("Value is negative",0.0,
                Double.MAX_VALUE));

        //What if text field is empty - integer will be null in that case, so show blank when null
        hourlyPay.setNullRepresentation("");

        name.setValidationVisible(true);
        surname.setValidationVisible(true);
        patronymic.setValidationVisible(true);
        hourlyPay.setValidationVisible(true);

        name.setImmediate(true);
        surname.setImmediate(true);
        patronymic.setImmediate(true);
        hourlyPay.setImmediate(true);

        name.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        surname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        patronymic.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        hourlyPay.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        name.addTextChangeListener(event -> textChange(event, name));
        surname.addTextChangeListener(event -> textChange(event, surname));
        patronymic.addTextChangeListener(event -> textChange(event, patronymic));
        hourlyPay.addTextChangeListener(event -> textChange(event, hourlyPay));
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField){
        try {
            textField.setValue(event.getText());

            textField.setCursorPosition(event.getCursorPosition());

            surname.validate();
            name.validate();
            patronymic.validate();
            hourlyPay.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException| Converter.ConversionException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event){
        try {
            MechanicDAO.getInstance().update(id, name.getValue(), surname.getValue(),
                    patronymic.getValue(),
                    Double.parseDouble(hourlyPay.getConvertedValue().toString()));
            getUI().design.horizontalLayoutTopGrids.verticalGridM.updateGrid();
            getUI().design.horizontalLayoutGridButtonsOrd.updateGrid();
            getUI().design.horizontalLayoutTopGrids.verticalGridM.buttonEditMechanic.setEnabled(false);
            getUI().design.horizontalLayoutTopGrids.verticalGridM.buttonDeleteMechanic.setEnabled(false);
            getUI().design.horizontalLayoutTopGrids.verticalGridM.buttonStatistic.setEnabled(false);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }
}
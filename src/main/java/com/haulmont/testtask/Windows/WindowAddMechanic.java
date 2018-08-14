package com.haulmont.testtask.Windows;

import com.haulmont.testtask.UI.MainUI;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import dao.DAO;

import java.sql.SQLException;

public class WindowAddMechanic extends Window {
    private TextField fieldName = new TextField("Name");
    private TextField fieldSurname = new TextField("Surname");
    private TextField fieldPatronymic = new TextField("Patronymic");
    private TextField fieldHourlyPay = new TextField("Hourly Pay");
    private Button ok = new Button("OK",this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);
    private DoubleRangeValidator doubleRangeValidator = new DoubleRangeValidator("Value is negative",
            0.0,Double.MAX_VALUE);

    public WindowAddMechanic(){
        super("Add Mechanic"); // Set window caption
        buildWindow();
        validation();
    }

    private void buildWindow(){
        center(); //Position of window
        setClosable(true); // Enable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout (fieldName,fieldSurname,fieldPatronymic,fieldHourlyPay);
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        HorizontalLayout horizontalButtons = new HorizontalLayout(ok,cancel);
        horizontalButtons.setSpacing(false);
        horizontalButtons.setMargin(false);

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontalButtons);

        setContent(verticalMain);
    }

    private void validation(){
        //validation
        fieldName.addValidator(stringLengthValidator);
        fieldSurname.addValidator(stringLengthValidator);
        fieldPatronymic.addValidator(stringLengthValidator);

        fieldHourlyPay.setRequired(true);
        fieldHourlyPay.setRequiredError("Prompt is empty.");

        //fieldHourlyPay.setLocale(Locale.ENGLISH);

        //To convert string value to integer before validation
        //fieldHourlyPay.setConverter(new StringToDoubleConverter());
        fieldHourlyPay.setConverter(new toDoubleConverter());
        fieldHourlyPay.addValidator(doubleRangeValidator);

        //What if text field is empty - integer will be null in that case, so show blank when null
        fieldHourlyPay.setNullRepresentation("");

        fieldName.setValidationVisible(true);
        fieldSurname.setValidationVisible(true);
        fieldPatronymic.setValidationVisible(true);
        fieldHourlyPay.setValidationVisible(true);

        fieldName.setImmediate(true);
        fieldSurname.setImmediate(true);
        fieldPatronymic.setImmediate(true);
        fieldHourlyPay.setImmediate(true);

        fieldName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        fieldSurname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        fieldPatronymic.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        fieldHourlyPay.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        fieldName.addTextChangeListener(event -> textChange(event, fieldName));
        fieldSurname.addTextChangeListener(event -> textChange(event, fieldSurname));
        fieldPatronymic.addTextChangeListener(event -> textChange(event, fieldPatronymic));
        fieldHourlyPay.addTextChangeListener(event -> textChange(event, fieldHourlyPay));
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField){
        try {
            textField.setValue(event.getText());

            textField.setCursorPosition(event.getCursorPosition());

            fieldSurname.validate();
            fieldName.validate();
            fieldPatronymic.validate();
            fieldHourlyPay.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event){
        try {
            DAO.getInstance().storeMechanic(fieldName.getValue(), fieldSurname.getValue(),
                    fieldPatronymic.getValue(),
                    Double.parseDouble(fieldHourlyPay.getConvertedValue().toString()));
            getUI().design.horizontalLayoutTopGrids.verticalGridM.UpdateGrid();
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
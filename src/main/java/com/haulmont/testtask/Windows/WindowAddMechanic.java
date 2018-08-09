package com.haulmont.testtask.Windows;

import com.haulmont.testtask.UI.MainUI;
import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
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
    private Button ok;
    private Button cancel;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);
    public WindowAddMechanic(){
        super("Add Mechanic"); // Set window caption
        center(); //Position of window
        setClosable(true); // Enable the close button
        setModal(true); // Enable modal window mode

        fieldName.setMaxLength(50);
        fieldSurname.setMaxLength(50);
        fieldPatronymic.setMaxLength(50);
        fieldHourlyPay.setMaxLength(19);

        //validation
        fieldName.addValidator(stringLengthValidator);
        fieldSurname.addValidator(stringLengthValidator);
        fieldPatronymic.addValidator(stringLengthValidator);

        fieldHourlyPay.setRequired(true);
        fieldHourlyPay.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        fieldHourlyPay.setConverter(new StringToIntegerConverter());
        fieldHourlyPay.addValidator(new IntegerRangeValidator("Value is negative",0,
                Integer.MAX_VALUE));

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

        fieldName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        fieldSurname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        fieldPatronymic.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        fieldHourlyPay.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);

        fieldHourlyPay.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {

                    fieldHourlyPay.setValue(event.getText());

                    fieldHourlyPay.setCursorPosition(event.getCursorPosition());

                    fieldHourlyPay.validate();
                    fieldName.validate();
                    fieldSurname.validate();
                    fieldPatronymic.validate();

                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }

            }
        });

        fieldName.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {
                    fieldName.setValue(event.getText());

                    fieldName.setCursorPosition(event.getCursorPosition());

                    fieldName.validate();
                    fieldSurname.validate();
                    fieldPatronymic.validate();
                    fieldHourlyPay.validate();
                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }
            }
        });

        fieldSurname.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {
                    fieldSurname.setValue(event.getText());

                    fieldSurname.setCursorPosition(event.getCursorPosition());

                    fieldSurname.validate();
                    fieldName.validate();
                    fieldPatronymic.validate();
                    fieldHourlyPay.validate();

                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }
            }
        });

        fieldPatronymic.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {
                    fieldPatronymic.setValue(event.getText());

                    fieldPatronymic.setCursorPosition(event.getCursorPosition());

                    fieldPatronymic.validate();
                    fieldName.validate();
                    fieldSurname.validate();
                    fieldHourlyPay.validate();

                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }
            }
        });

        ok = new Button("OK",event -> {
            try {
                DAO.getInstance().storeMechanic(fieldName.getValue(), fieldSurname.getValue(),
                        fieldPatronymic.getValue(),
                        Integer.parseInt(fieldHourlyPay.getConvertedValue().toString()));
                getUI().design.horizontalLayoutTopGrids.verticalGridM.FillGrid();
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        cancel = new Button("Cancel",event -> close());

        VerticalLayout verticalFields = new VerticalLayout ();

        verticalFields.addComponent(fieldName);
        verticalFields.addComponent(fieldSurname);
        verticalFields.addComponent(fieldPatronymic);
        verticalFields.addComponent(fieldHourlyPay);


        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        HorizontalLayout horizontalButtons = new HorizontalLayout();

        horizontalButtons.setSpacing(false);
        horizontalButtons.setMargin(false);

        VerticalLayout verticalMain = new VerticalLayout ();

        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        horizontalButtons.addComponent(ok);
        horizontalButtons.addComponent(cancel);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontalButtons);

        setContent(verticalMain);
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }
}

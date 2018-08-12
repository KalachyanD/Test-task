package com.haulmont.testtask.Windows;

import com.haulmont.testtask.UI.MainUI;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import dao.DAO;
import models.Mechanic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WindowEditMechanic extends Window {
    private TextField fieldName = new TextField("Name");
    private TextField fieldSurname = new TextField("Surname");
    private TextField fieldPatronymic = new TextField("Patronymic");
    private TextField fieldHourlyPay = new TextField("Hourly Pay");
    private Button ok = new Button("OK", this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private int id;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowEditMechanic(int id){
        super("Edit Client"); // Set window caption
        preload(id);
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

        VerticalLayout verticalMain = new VerticalLayout (verticalFields,horizontalButtons);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        setContent(verticalMain);
    }

    private void preload(int id){
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }

        for (int i = 0; i < mechanics.size(); ++i) {
            if (id == mechanics.get(i).getID()) {
                this.id = i;
            }

        }

        fieldName.setValue(mechanics.get(this.id).getName());
        fieldSurname.setValue(mechanics.get(this.id).getSurname());
        fieldPatronymic.setValue(mechanics.get(this.id).getPatronymic());
        fieldHourlyPay.setValue(Double.toString(mechanics.get(this.id).getHourlyPay()));
    }

    private void validation(){
        fieldName.addValidator(stringLengthValidator);
        fieldSurname.addValidator(stringLengthValidator);
        fieldPatronymic.addValidator(stringLengthValidator);

        fieldHourlyPay.setRequired(true);
        fieldHourlyPay.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        fieldHourlyPay.setConverter(new toDoubleConverter());
        fieldHourlyPay.addValidator(new DoubleRangeValidator("Value is negative",0.0,
                Double.MAX_VALUE));

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
    }

    private void ok(Button.ClickEvent event){
        try {
            DAO.getInstance().updateMechanic(id, fieldName.getValue(), fieldSurname.getValue(),
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

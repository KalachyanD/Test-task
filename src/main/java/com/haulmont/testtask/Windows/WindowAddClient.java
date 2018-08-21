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

public class WindowAddClient extends Window {
    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField patronymic = new TextField("Patronymic");
    private TextField phoneNumber = new TextField("Telephone");
    private Button ok= new Button("OK",this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowAddClient(){
        super("Add Client"); // Set window caption
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
        phoneNumber.setMaxLength(19);

        VerticalLayout verticalFields = new VerticalLayout (name, surname, patronymic, phoneNumber);
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

    private void validation(){
        //VALIDATION
        name.addValidator(stringLengthValidator);
        surname.addValidator(stringLengthValidator);
        patronymic.addValidator(stringLengthValidator);

        phoneNumber.setRequired(true);
        phoneNumber.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        phoneNumber.setConverter(new StringToIntegerConverter());
        phoneNumber.addValidator(new IntegerRangeValidator("Value is negative",0,
                Integer.MAX_VALUE));

        //What if text field is empty - integer will be null in that case, so show blank when null
        phoneNumber.setNullRepresentation("");
        name.setValidationVisible(true);
        surname.setValidationVisible(true);
        patronymic.setValidationVisible(true);
        phoneNumber.setValidationVisible(true);

        name.setImmediate(true);
        surname.setImmediate(true);
        patronymic.setImmediate(true);
        phoneNumber.setImmediate(true);

        name.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        surname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        patronymic.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        phoneNumber.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        name.addTextChangeListener(event -> textChange(event, name));
        surname.addTextChangeListener(event -> textChange(event, surname));
        patronymic.addTextChangeListener(event -> textChange(event, patronymic));
        phoneNumber.addTextChangeListener(event -> textChange(event, phoneNumber));
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField){
        try {
            textField.setValue(event.getText());

            textField.setCursorPosition(event.getCursorPosition());

            surname.validate();
            name.validate();
            patronymic.validate();
            phoneNumber.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event){
        try {
            DAO.getInstance().storeClient(name.getValue(), surname.getValue(),
                    patronymic.getValue(),
                    Integer.parseInt(phoneNumber.getConvertedValue().toString()));
            getUI().design.horizontalLayoutTopGrids.verticalGridC.UpdateGrid();
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
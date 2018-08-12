package com.haulmont.testtask.Windows;

import com.haulmont.testtask.UI.MainUI;
import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WindowEditClient extends Window {
    private TextField fieldName = new TextField("Name");
    private TextField fieldSurname = new TextField("Surname");
    private TextField fieldPatronymic = new TextField("Patronymic");
    private TextField fieldTelephone = new TextField("Telephone");
    private Button ok = new Button("OK",this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private int id;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);
    public WindowEditClient(int id){
        super("Edit Client"); // Set window caption
        preload(id);
        buildWindow();
        validation();
    }

    private void preload(int id){
        //Preload data into fields
        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        for (int i = 0; i < clients.size(); ++i) {
            if (id == clients.get(i).getID()) {
                this.id = i;
            }

        }

        fieldName.setValue(clients.get(this.id).getName());
        fieldSurname.setValue(clients.get(this.id).getSurname());
        fieldPatronymic.setValue(clients.get(this.id).getPatronymic());
        fieldTelephone.setValue(Integer.toString(clients.get(this.id).getTelephone()));
    }

    private void buildWindow(){
        center(); //Position of window
        setClosable(true); // Enable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout (fieldName,fieldSurname,fieldPatronymic,fieldTelephone);
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
        //validation
        fieldName.addValidator(stringLengthValidator);
        fieldSurname.addValidator(stringLengthValidator);
        fieldPatronymic.addValidator(stringLengthValidator);

        fieldTelephone.setRequired(true);
        fieldTelephone.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        fieldTelephone.setConverter(new StringToIntegerConverter());
        fieldTelephone.addValidator(new IntegerRangeValidator("Value is negative",0,
                Integer.MAX_VALUE));

        //What if text field is empty - integer will be null in that case, so show blank when null
        fieldTelephone.setNullRepresentation("");

        fieldName.setValidationVisible(true);
        fieldSurname.setValidationVisible(true);
        fieldPatronymic.setValidationVisible(true);
        fieldTelephone.setValidationVisible(true);

        fieldName.setImmediate(true);
        fieldSurname.setImmediate(true);
        fieldPatronymic.setImmediate(true);
        fieldTelephone.setImmediate(true);

        fieldName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        fieldSurname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        fieldPatronymic.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        fieldTelephone.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        fieldName.addTextChangeListener(event -> textChange(event, fieldName));
        fieldSurname.addTextChangeListener(event -> textChange(event, fieldSurname));
        fieldPatronymic.addTextChangeListener(event -> textChange(event, fieldPatronymic));
        fieldTelephone.addTextChangeListener(event -> textChange(event, fieldTelephone));
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField){
        try {
            textField.setValue(event.getText());

            textField.setCursorPosition(event.getCursorPosition());

            fieldSurname.validate();
            fieldName.validate();
            fieldPatronymic.validate();
            fieldTelephone.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event){
        try {
            DAO.getInstance().updateClient(id, fieldName.getValue(), fieldSurname.getValue(),
                    fieldPatronymic.getValue(),
                    Integer.parseInt(fieldTelephone.getConvertedValue().toString()));
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

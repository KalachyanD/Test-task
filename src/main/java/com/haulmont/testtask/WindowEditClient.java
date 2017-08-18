package com.haulmont.testtask;



import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.*;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 21.07.2017.
 */

public class WindowEditClient extends Window {


    TextField fieldName = new TextField("Name");
    TextField fieldSurname = new TextField("Surname");
    TextField fieldPatronymic = new TextField("Patronymic");
    TextField fieldTelephone = new TextField("Telephone");
    Button ok = new Button();
    Button cancel = new Button();
    boolean a = true;
    boolean b = true;
    boolean c = true;
    boolean d = true;

    int clientID;

    public WindowEditClient(int clientID) {

        super("Edit Client"); // Set window caption
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode


        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        for(int i = 0;i < clients.size();++i){
            if(clientID == clients.get(i).getID()){
                this.clientID = i;
            }

        }


        fieldName.setValue(clients.get(this.clientID).getName());
        fieldSurname.setValue(clients.get(this.clientID).getSurname());
        fieldPatronymic.setValue(clients.get(this.clientID).getPatronymic());
        fieldTelephone.setValue(Integer.toString(clients.get(this.clientID).getTelephone()));

        fieldName.setMaxLength(50);
        fieldSurname.setMaxLength(50);
        fieldPatronymic.setMaxLength(50);
        fieldTelephone.setMaxLength(19);

        fieldName.addValidator(new StringLengthValidator("Prompt is empty.", 1, 50, false));
        fieldSurname.addValidator(new StringLengthValidator("Prompt is empty.", 1, 50, false));
        fieldPatronymic.addValidator(new StringLengthValidator("Prompt is empty.", 1, 50, false));

        fieldTelephone.setRequired(true);
        fieldTelephone.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        fieldTelephone.setConverter(new StringToIntegerConverter());
        fieldTelephone.addValidator(new IntegerRangeValidator("Value is negative",0,Integer.MAX_VALUE));
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


        fieldTelephone.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {
                    fieldTelephone.setValue(event.getText());

                    fieldTelephone.setCursorPosition(event.getCursorPosition());

                    fieldTelephone.validate();

                    a = true;

                } catch (Validator.InvalidValueException e) {
                    a = false;

                }
                if(a == true && b == true && c == true && d == true){
                    ok.setEnabled(true);
                }
                else{
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

                    b = true;

                } catch (Validator.InvalidValueException e) {
                    b = false;
                }
                if(a == true && b == true && c == true && d == true){
                    ok.setEnabled(true);
                }
                else{
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

                    c = true;

                } catch (Validator.InvalidValueException e) {

                    c = false;

                }
                if(a == true && b == true && c == true && d == true){
                    ok.setEnabled(true);
                }
                else{
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

                    d = true;

                } catch (Validator.InvalidValueException e) {
                    d = false;
                }
                if(a == true && b == true && c == true && d == true){
                    ok.setEnabled(true);
                }
                else{
                    ok.setEnabled(false);
                }
            }
        });

        ok = new Button("OK", event -> {
            try {
                DAO.getInstance().updateClient(clientID, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephone.getValue()));
                close();
                Page.getCurrent().reload();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        cancel = new Button("Cancel",event -> close());

        VerticalLayout verticalFields = new VerticalLayout ();

        verticalFields.addComponent(fieldName);
        verticalFields.addComponent(fieldSurname);
        verticalFields.addComponent(fieldPatronymic);
        verticalFields.addComponent(fieldTelephone);


        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        HorizontalLayout horizontButtons = new HorizontalLayout();

        horizontButtons.setSpacing(false);
        horizontButtons.setMargin(false);

        VerticalLayout verticalMain = new VerticalLayout ();

        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        horizontButtons.addComponent(ok);
        horizontButtons.addComponent(cancel);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontButtons);

        setContent(verticalMain);

    }
}

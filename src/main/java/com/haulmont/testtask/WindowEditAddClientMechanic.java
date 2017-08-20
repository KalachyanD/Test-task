package com.haulmont.testtask;



import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.*;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;
import models.Mechanic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 21.07.2017.
 */

public class WindowEditAddClientMechanic extends Window {


    TextField fieldName = new TextField("Name");
    TextField fieldSurname = new TextField("Surname");
    TextField fieldPatronymic = new TextField("Patronymic");
    TextField fieldTelephoneAndHourlyPay;
    Button ok;
    Button cancel;
    int id;
    boolean firstField = true;
    boolean secondField = true;
    boolean thirdField = true;
    boolean fourthField = true;


    public WindowEditAddClientMechanic(int id, String addOrEdit, String clientOrMechanic) {

        super(addOrEdit + " " + clientOrMechanic); // Set window caption
        if(clientOrMechanic == "Client") {
            fieldTelephoneAndHourlyPay = new TextField("Telephone");
        }
        if(clientOrMechanic == "Mechanic") {
            fieldTelephoneAndHourlyPay = new TextField("Hourly Pay");
        }
        center(); //Position of window
        setClosable(true); // Enable the close button
        setModal(true); // Enable modal window mode

        //Preload data into fields
        if(addOrEdit == "Edit" && clientOrMechanic == "Client") {
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
            fieldTelephoneAndHourlyPay.setValue(Integer.toString(clients.get(this.id).getTelephone()));
        }
        if(addOrEdit == "Edit" && clientOrMechanic == "Mechanic") {
            List<Mechanic> clients = new ArrayList<>();
            try {
                clients = DAO.getInstance().LoadAllMechanics();
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
            fieldTelephoneAndHourlyPay.setValue(Integer.toString(clients.get(this.id).getHourlyPay()));
        }

        fieldName.setMaxLength(50);
        fieldSurname.setMaxLength(50);
        fieldPatronymic.setMaxLength(50);
        fieldTelephoneAndHourlyPay.setMaxLength(19);

        fieldName.addValidator(new StringLengthValidator("Prompt is empty.", 1, 50, false));
        fieldSurname.addValidator(new StringLengthValidator("Prompt is empty.", 1, 50, false));
        fieldPatronymic.addValidator(new StringLengthValidator("Prompt is empty.", 1, 50, false));

        fieldTelephoneAndHourlyPay.setRequired(true);
        fieldTelephoneAndHourlyPay.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        fieldTelephoneAndHourlyPay.setConverter(new StringToIntegerConverter());
        fieldTelephoneAndHourlyPay.addValidator(new IntegerRangeValidator("Value is negative",0,Integer.MAX_VALUE));

        //What if text field is empty - integer will be null in that case, so show blank when null
        fieldTelephoneAndHourlyPay.setNullRepresentation("");

        fieldName.setValidationVisible(true);
        fieldSurname.setValidationVisible(true);
        fieldPatronymic.setValidationVisible(true);
        fieldTelephoneAndHourlyPay.setValidationVisible(true);

        fieldName.setImmediate(true);
        fieldSurname.setImmediate(true);
        fieldPatronymic.setImmediate(true);
        fieldTelephoneAndHourlyPay.setImmediate(true);


        fieldTelephoneAndHourlyPay.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {
                    fieldTelephoneAndHourlyPay.setValue(event.getText());

                    fieldTelephoneAndHourlyPay.setCursorPosition(event.getCursorPosition());

                    fieldTelephoneAndHourlyPay.validate();

                    firstField = true;

                } catch (Validator.InvalidValueException e) {
                    firstField = false;

                }
                if(firstField == true && secondField == true && thirdField == true && fourthField == true){
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

                    secondField = true;

                } catch (Validator.InvalidValueException e) {
                    secondField = false;
                }
                if(firstField == true && secondField == true && thirdField == true && fourthField == true){
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

                    thirdField = true;

                } catch (Validator.InvalidValueException e) {

                    thirdField = false;

                }
                if(firstField == true && secondField == true && thirdField == true && fourthField == true){
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

                    fourthField = true;

                } catch (Validator.InvalidValueException e) {
                    fourthField = false;
                }
                if(firstField == true && secondField == true && thirdField == true && fourthField == true){
                    ok.setEnabled(true);
                }
                else{
                    ok.setEnabled(false);
                }
            }
        });

        if(addOrEdit == "Edit" && clientOrMechanic == "Client") {
            ok = new Button("OK", event -> {
                try {
                    DAO.getInstance().updateClient(id, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephoneAndHourlyPay.getConvertedValue().toString()));
                    close();
                    Page.getCurrent().reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        if(addOrEdit == "Edit" && clientOrMechanic == "Mechanic"){
            ok = new Button("OK", event -> {
                try {
                    DAO.getInstance().updateMechanic(id, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephoneAndHourlyPay.getConvertedValue().toString()));
                    close();
                    Page.getCurrent().reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        if(addOrEdit == "Add" && clientOrMechanic == "Mechanic"){
            ok = new Button("OK",event -> {
                try {
                    DAO.getInstance().storeMechanic(fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephoneAndHourlyPay.getConvertedValue().toString()));
                    close();
                    Page.getCurrent().reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
        if(addOrEdit == "Add" && clientOrMechanic == "Client"){
            ok = new Button("OK",event -> {
                try {
                    DAO.getInstance().storeClient(fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephoneAndHourlyPay.getConvertedValue().toString()));
                    close();
                    Page.getCurrent().reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }


        cancel = new Button("Cancel",event -> close());

        VerticalLayout verticalFields = new VerticalLayout ();

        verticalFields.addComponent(fieldName);
        verticalFields.addComponent(fieldSurname);
        verticalFields.addComponent(fieldPatronymic);
        verticalFields.addComponent(fieldTelephoneAndHourlyPay);


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
}

package com.haulmont.testtask.Windows;



import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.*;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;
import models.Mechanic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.event.FieldEvents.*;


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
    StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.", 1, 50, false);

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

        fieldName.addValidator(stringLengthValidator);
        fieldSurname.addValidator(stringLengthValidator);
        fieldPatronymic.addValidator(stringLengthValidator);

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

        fieldName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        fieldSurname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        fieldPatronymic.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        fieldTelephoneAndHourlyPay.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);

        fieldTelephoneAndHourlyPay.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(TextChangeEvent event) {
                try {

                    fieldTelephoneAndHourlyPay.setValue(event.getText());

                    fieldTelephoneAndHourlyPay.setCursorPosition(event.getCursorPosition());

                    fieldTelephoneAndHourlyPay.validate();
                    fieldName.validate();
                    fieldSurname.validate();
                    fieldPatronymic.validate();

                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }

            }
        });

        fieldName.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(TextChangeEvent event) {
                try {
                    fieldName.setValue(event.getText());

                    fieldName.setCursorPosition(event.getCursorPosition());

                    fieldName.validate();
                    fieldSurname.validate();
                    fieldPatronymic.validate();
                    fieldTelephoneAndHourlyPay.validate();
                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }
            }
        });

        fieldSurname.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(TextChangeEvent event) {
                try {
                    fieldSurname.setValue(event.getText());

                    fieldSurname.setCursorPosition(event.getCursorPosition());

                    fieldSurname.validate();
                    fieldName.validate();
                    fieldPatronymic.validate();
                    fieldTelephoneAndHourlyPay.validate();

                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }
            }
        });

        fieldPatronymic.addTextChangeListener(new TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(TextChangeEvent event) {
                try {
                    fieldPatronymic.setValue(event.getText());

                    fieldPatronymic.setCursorPosition(event.getCursorPosition());

                    fieldPatronymic.validate();
                    fieldName.validate();
                    fieldSurname.validate();
                    fieldTelephoneAndHourlyPay.validate();

                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }
            }
        });


        if(addOrEdit == "Edit" && clientOrMechanic == "Client") {
            ok = new Button("OK", event -> {
                try {
                    DAO.getInstance().updateClient(id, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephoneAndHourlyPay.getConvertedValue().toString()));
                    close();
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

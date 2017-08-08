package com.haulmont.testtask;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;


import java.sql.SQLException;


/**
 * Created by User on 21.07.2017.
 */
public class WindowAddClient extends Window  {

    TextField fieldName;
    TextField fieldSurname;
    TextField fieldPatronymic;
    TextField fieldTelephone ;

    public WindowAddClient() {

        super("Add Client"); // Set window caption
        fieldName = new TextField("Name");
        fieldSurname = new TextField("Surname");
        fieldPatronymic = new TextField("Patronymic");
        fieldTelephone = new TextField("Telephone");

        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout ();
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        verticalFields.addComponent(fieldName);
        verticalFields.addComponent(fieldSurname);
        verticalFields.addComponent(fieldPatronymic);
        verticalFields.addComponent(fieldTelephone);

        HorizontalLayout horizontButtons = new HorizontalLayout();
        horizontButtons.setSpacing(false);
        horizontButtons.setMargin(false);
        horizontButtons.addComponent(new Button("OK",event -> {
            try {
                WindowAddClient.EventClickOk(fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephone.getValue()));
                close();
                Page.getCurrent().reload();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        horizontButtons.addComponent(new Button("Cancel",event -> close()));

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontButtons);

        setContent(verticalMain);
    }

    public static void EventClickOk(String fieldName, String fieldSurname, String fieldPatronymic, int fieldTelephone) throws SQLException {
        Client client = new Client(0,fieldName, fieldSurname, fieldPatronymic, fieldTelephone);
        DAO.getInstance().storeClient(client);

    }
}

package com.haulmont.testtask;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;


import java.sql.SQLException;


/**
 * Created by User on 21.07.2017.
 */
public class WindowEditClient extends Window {

    int clientID;
    TextField fieldName;
    TextField fieldSurname;
    TextField fieldPatronymic;
    TextField fieldTelephone ;

    public WindowEditClient(int clientID) {

        super("Edit Client"); // Set window caption
        this.clientID = clientID;
        fieldName = new TextField("Name");
        fieldSurname = new TextField("Surname");
        fieldPatronymic = new TextField("Patronymic");
        fieldTelephone = new TextField("Telephone");

        /*
        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        fieldName.setValue(clients.get(clientID-1).getName());
        fieldSurname.setValue(clients.get(clientID-1).getSurname());
        fieldPatronymic.setValue(clients.get(clientID-1).getPatronymic());
        fieldTelephone.setValue(Integer.toString(clients.get(clientID-1).getTelephone()));
        */

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
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);
        horizontButtons.addComponent(new Button("ОК", event -> {
            try {
                WindowEditClient.EventClickOk(clientID, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephone.getValue()));
                close();
                Page.getCurrent().reload();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        horizontButtons.addComponent(new Button("Отмена",event -> close()));

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontButtons);

        setContent(verticalMain);
    }

    public static void EventClickOk(int clientID, String fieldName, String fieldSurname, String fieldPatronymic, int fieldTelephone) throws SQLException {
        DAO.getInstance().updateClient(clientID , fieldName, fieldSurname, fieldPatronymic, fieldTelephone);
    }

}

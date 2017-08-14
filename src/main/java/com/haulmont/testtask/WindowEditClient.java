package com.haulmont.testtask;

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
        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        horizontButtons.addComponent(new Button("OK", event -> {
            try {
                DAO.getInstance().updateClient(clientID, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldTelephone.getValue()));
                close();
                Page.getCurrent().reload();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));

        horizontButtons.addComponent(new Button("Cancel",event -> close()));

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontButtons);

        setContent(verticalMain);

    }
}

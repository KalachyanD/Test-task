package com.haulmont.testtask;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import dao.DAO;
import models.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.08.2017.
 */
public class VerticalLayoutGridButtonsC extends VerticalLayout {

    Grid gridClients = new Grid("Clients");
    Button buttonDeleteClient = new Button("Delete");
    Button buttonEditClient = new Button("Edit");
    Button buttonAddClient = new Button("Add");
    Client client;

    public VerticalLayoutGridButtonsC(){

        gridClients.setHeight("300");
        addComponent(gridClients);
        addComponent(buttonDeleteClient);
        addComponent(buttonEditClient);
        addComponent(buttonAddClient);

        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        // Have a containerGridClients of some type to contain the data
        BeanItemContainer<Client> containerGridClients = new BeanItemContainer<>(Client.class, clients);
        // Create a gridClients bound to the containerGridClients
        gridClients.removeAllColumns();
        gridClients.setContainerDataSource(containerGridClients);
        gridClients.setSelectionMode(Grid.SelectionMode.SINGLE);

        // Add Client
        buttonAddClient.addClickListener(event -> {
            WindowAddClient window = new WindowAddClient();
            UI.getCurrent().addWindow(window);
        });

        //Selection listener
        gridClients.addSelectionListener(event -> {
            client =(Client)gridClients.getSelectedRow();
        });

        // Delete Client
        buttonDeleteClient.addClickListener(eventButton -> {
            try {
                DAO.getInstance().deleteClient(client.getID());
                Page.getCurrent().reload();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //Edit Client
        buttonEditClient.addClickListener(eventButton -> {
            WindowEditClient window = new WindowEditClient(client.getID());
            UI.getCurrent().addWindow(window);
        });



    }
}

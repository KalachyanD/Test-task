package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.WindowEditAddClientMechanic;
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
    boolean enable = false;

    public void FillTable() {
        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        // Have firstField containerGridClients of some type to contain the data
        BeanItemContainer<Client> containerGridClients = new BeanItemContainer<>(Client.class, clients);

        // Create firstField gridClients bound to the containerGridClients
        gridClients.removeAllColumns();
        gridClients.setContainerDataSource(containerGridClients);
    }

    public VerticalLayoutGridButtonsC(){
        gridClients.setHeight("300");
        gridClients.setSelectionMode(Grid.SelectionMode.SINGLE);
        addComponent(gridClients);
        addComponent(buttonDeleteClient);
        addComponent(buttonEditClient);
        addComponent(buttonAddClient);

        FillTable();

        // Add Client
        buttonAddClient.addClickListener(event -> {
            WindowEditAddClientMechanic window = new WindowEditAddClientMechanic(0,"Add","Client");
            UI.getCurrent().addWindow(window);
        });

        //Selection listener
        gridClients.addSelectionListener(event -> {
            client =(Client)gridClients.getSelectedRow();
            enable = true;
        });

        // Delete Client
        buttonDeleteClient.addClickListener(eventButton -> {
            if(enable == true) {
                try {
                    DAO.getInstance().deleteClient(client.getID());
                    FillTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Edit Client
        buttonEditClient.addClickListener(eventButton -> {
            if(enable == true) {
                WindowEditAddClientMechanic window = new WindowEditAddClientMechanic(client.getID(), "Edit", "Client");
                UI.getCurrent().addWindow(window);
            }
        });



    }
}

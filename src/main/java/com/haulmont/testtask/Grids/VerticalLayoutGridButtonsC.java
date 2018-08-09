package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.*;
import com.vaadin.data.util.BeanItemContainer;
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

    private Grid gridClients = new Grid("Clients");
    private Button buttonDeleteClient = new Button("Delete");
    private Button buttonEditClient = new Button("Edit");
    private Button buttonAddClient = new Button("Add");
    private Client client;
    private boolean enable = false;

    public void FillGrid() {
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

        FillGrid();

        // Add Client
        buttonAddClient.addClickListener(event -> {
            WindowAddClient window = new WindowAddClient();
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
                    FillGrid();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Edit Client
        buttonEditClient.addClickListener(eventButton -> {
            if(enable == true) {
                WindowEditClient window = new WindowEditClient(client.getID());
                UI.getCurrent().addWindow(window);
            }
        });



    }
}

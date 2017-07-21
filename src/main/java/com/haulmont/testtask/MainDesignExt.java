package com.haulmont.testtask;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;
import models.Mechanic;
import models.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 19.07.2017.
 */

public class MainDesignExt extends MainDesign {

    public MainDesignExt(){
        super();


        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }


        // Have a containerGridClients of some type to contain the data
        BeanItemContainer<Client> containerGridClients = new BeanItemContainer<>(Client.class, clients);
        // Create a gridClients bound to the containerGridClients
        gridClients.removeAllColumns();
        gridClients.setContainerDataSource(containerGridClients);
        horizontTopGrids.addComponent(gridClients);

        BeanItemContainer<Mechanic> containerGridMechanics = new BeanItemContainer<>(Mechanic.class, mechanics);
        // Create a gridMechanics bound to the containerMechanics
        gridMechanics.removeAllColumns();
        gridMechanics.setContainerDataSource(containerGridMechanics);
        horizontTopGrids.addComponent(gridMechanics);

        BeanItemContainer<Order> containerGridOrders = new BeanItemContainer<>(Order.class, orders);
        // Create a gridOrders bound to the containerOrders
        gridOrders.removeAllColumns();
        gridOrders.setContainerDataSource(containerGridOrders);
        horizontBottomGrid.addComponent(gridOrders);

        // Add Client
        buttinAddClient.addClickListener(event -> {
            WindowAddClient window = new WindowAddClient();
            // Add it to the root component
            UI.getCurrent().addWindow(window);
        });

        // Delete Client
        gridClients.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridClients.addSelectionListener(event -> {
            Client client =(Client)gridClients.getSelectedRow();
            buttonDeleteClient.addClickListener(eventButton -> {
                try {
                    DAO.getInstance().deleteClient(client.getID());
                    Page.getCurrent().reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        //Edit Client
        gridClients.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridClients.addSelectionListener(event -> {
            Client client =(Client)gridClients.getSelectedRow();
            buttonEditClient.addClickListener(eventButton -> {
                WindowEditClient window = new WindowEditClient(client.getID());
                UI.getCurrent().addWindow(window);
            });
        });

        // Add Mechanic
        buttonAddMechanic.addClickListener(event -> {
            WindowAddMechanic window = new WindowAddMechanic();
            // Add it to the root component
            UI.getCurrent().addWindow(window);
        });

        // Delete Mechanic
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridMechanics.addSelectionListener(event -> {
            Mechanic mechanic =(Mechanic)gridMechanics.getSelectedRow();
            buttonDeleteMechanic.addClickListener(eventButton -> {
                try {
                    DAO.getInstance().deleteMechanic(mechanic.getID());
                    Page.getCurrent().reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        //Edit Mechanic
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridMechanics.addSelectionListener(event -> {
            Mechanic mechanic =(Mechanic)gridMechanics.getSelectedRow();
            buttonEditMechanic.addClickListener(eventButton -> {
                WindowEditMechanic window = new WindowEditMechanic(mechanic.getID());
                UI.getCurrent().addWindow(window);
            });
        });




    }

}



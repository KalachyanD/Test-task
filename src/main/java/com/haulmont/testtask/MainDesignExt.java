package com.haulmont.testtask;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;
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


         
        /*Lists.newArrayList(
                new Client(0, "Name", "Surname", "Johnson", 89371762),
                new Client(1, "Name", "Surname", "Johnson", 89371762),
                new Client(2, "Name", "Surname", "Johnson", 89371762));
        */

        // Create a gridClients bound to the list
        //gridClients.set
        //gridClients.setItems(clients);
        //gridClients.addColumn("Name", Person::getName);
        //gridClients.addColumn("Year of birth", Person::getBirthYear);
        //layout.addComponent(gridClients);

        //Display data from database in grid.

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

        // Some UI logic to open the sub-window
        buttinAddClient.addClickListener(event -> {
            WindowAddClient sub = new WindowAddClient();
            // Add it to the root component
            UI.getCurrent().addWindow(sub);
        });





        
        

        /*gridClients.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent event) {
                Set clients = event.getSelected();
                StringBuilder clientsString = new StringBuilder();
                clients.forEach(item -> clientsString.append(item.toString()).append(" "));
                horizont.addComponent(new Label(clientsString.toString()));
            }
        });
        */



    }

}



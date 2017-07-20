package com.haulmont.testtask;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Label;
import dao.DAO;
import models.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 19.07.2017.
 */

public class DesignExt extends Design {

    public DesignExt(){
        super();
        // Have some data
        List<Client> people = new ArrayList<>();
        try {
            people = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }
         /*Lists.newArrayList(
                new Client(0, "Name", "Surname", "Johnson", 89371762),
                new Client(1, "Name", "Surname", "Johnson", 89371762),
                new Client(2, "Name", "Surname", "Johnson", 89371762));*/
// Create a grid_Clients bound to the list
//        grid_Clients.set
//        grid_Clients.setItems(people);
//        grid_Clients.addColumn("Name", Person::getName);
//        grid_Clients.addColumn("Year of birth", Person::getBirthYear);
//        layout.addComponent(grid_Clients);

        // Have a container of some type to contain the data
        BeanItemContainer<Client> container = new BeanItemContainer<>(Client.class, people);
// Create a grid_Clients bound to the container
        grid_Clients.removeAllColumns();
        grid_Clients.setContainerDataSource(container);
        //grid_Clients.setColumnOrder("№", "Имя","Фамилия","Отчество","Номер телефона");
        horizont.addComponent(grid_Clients);

        grid_Clients.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent event) {
                Set clients = event.getSelected();
                StringBuilder clientsString = new StringBuilder();
                clients.forEach(item -> clientsString.append(item.toString()).append(" "));
                horizont.addComponent(new Label(clientsString.toString()));
            }
        });

    }
}
package com.haulmont.testtask;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Grid;
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
// Create a grid bound to the list
//        grid.set
//        grid.setItems(people);
//        grid.addColumn("Name", Person::getName);
//        grid.addColumn("Year of birth", Person::getBirthYear);
//        layout.addComponent(grid);

        // Have a container of some type to contain the data
        BeanItemContainer<Client> container = new BeanItemContainer<>(Client.class, people);
// Create a grid bound to the container
        grid.removeAllColumns();
        grid.setContainerDataSource(container);
        //grid.setColumnOrder("ID", "Name","Surname","Patronymic","Telephone");
        horizont.addComponent(grid);

        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
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

package com.haulmont.testtask;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import dao.DAO;
import models.Mechanic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.08.2017.
 */
public class VerticalLayoutGridButtonsM extends VerticalLayout {

    Grid gridMechanics = new Grid("Clients");

    Button buttonDeleteMechanic = new Button("Delete");
    Button buttonEditMechanic = new Button("Edit");
    Button buttonAddMechanic = new Button("Add");

    public VerticalLayoutGridButtonsM(){

        gridMechanics.setHeight("300");
        addComponent(gridMechanics);
        addComponent(buttonDeleteMechanic);
        addComponent(buttonEditMechanic);
        addComponent(buttonAddMechanic);



        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }



        BeanItemContainer<Mechanic> containerGridMechanics = new BeanItemContainer<>(Mechanic.class, mechanics);
        // Create a gridMechanics bound to the containerMechanics
        gridMechanics.removeAllColumns();
        gridMechanics.setContainerDataSource(containerGridMechanics);
        //horizontTopGrids.addComponent(gridMechanics);



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

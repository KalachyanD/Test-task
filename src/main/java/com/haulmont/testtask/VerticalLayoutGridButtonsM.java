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

    Grid gridMechanics = new Grid("Mechanics");

    Button buttonDeleteMechanic = new Button("Delete");
    Button buttonEditMechanic = new Button("Edit");
    Button buttonAddMechanic = new Button("Add");
    Button buttonStatistics = new Button("Statistics");
    Mechanic mechanic;

    public VerticalLayoutGridButtonsM(){

        gridMechanics.setHeight("300");
        addComponent(gridMechanics);
        addComponent(buttonDeleteMechanic);
        addComponent(buttonEditMechanic);
        addComponent(buttonAddMechanic);
        addComponent(buttonStatistics);

        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }

        BeanItemContainer<Mechanic> containerGridMechanics = new BeanItemContainer<>(Mechanic.class, mechanics);
        // Create firstField gridMechanics bound to the containerMechanics
        gridMechanics.removeAllColumns();
        gridMechanics.setContainerDataSource(containerGridMechanics);
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);

        // Add Mechanic
        buttonAddMechanic.addClickListener(event -> {
            WindowEditAddClientMechanic window = new WindowEditAddClientMechanic(0,"Add","Mechanic");
            UI.getCurrent().addWindow(window);
        });

        //Selection listener
        gridMechanics.addSelectionListener(event -> {
            mechanic =(Mechanic)gridMechanics.getSelectedRow();
        });

        // Delete Mechanic
        buttonDeleteMechanic.addClickListener(eventButton -> {
            try {
                DAO.getInstance().deleteMechanic(mechanic.getID());
                Page.getCurrent().reload();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //Edit Mechanic
        buttonEditMechanic.addClickListener(eventButton -> {
            WindowEditAddClientMechanic window = new WindowEditAddClientMechanic(mechanic.getID(),"Edit","Mechanic");
            UI.getCurrent().addWindow(window);
        });

        //Statistics
        buttonStatistics.addClickListener(eventButton -> {
            WindowStatistics window = new WindowStatistics(mechanic);
            UI.getCurrent().addWindow(window);
        });


    }
}

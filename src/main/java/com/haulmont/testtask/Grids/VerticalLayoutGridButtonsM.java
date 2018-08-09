package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.*;
import com.vaadin.data.util.BeanItemContainer;
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

    private Grid gridMechanics = new Grid("Mechanics");
    private Button buttonDeleteMechanic = new Button("Delete");
    private Button buttonEditMechanic = new Button("Edit");
    private Button buttonAddMechanic = new Button("Add");
    private Button buttonStatistics = new Button("Statistics");
    private Mechanic mechanic;
    private boolean enable = false;

    public void FillGrid() {
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }

        BeanItemContainer<Mechanic> containerGridMechanics = new BeanItemContainer<>(Mechanic.class, mechanics);
        // Create firstField gridMechanics bound to the containerMechanics
        gridMechanics.removeAllColumns();
        gridMechanics.setContainerDataSource(containerGridMechanics);
    }

    public VerticalLayoutGridButtonsM(){
        gridMechanics.setHeight("300");
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);
        addComponent(gridMechanics);
        addComponent(buttonDeleteMechanic);
        addComponent(buttonEditMechanic);
        addComponent(buttonAddMechanic);
        addComponent(buttonStatistics);

        FillGrid();

        // Add Mechanic
        buttonAddMechanic.addClickListener(event -> {
            WindowAddMechanic window = new WindowAddMechanic();
            UI.getCurrent().addWindow(window);
        });

        //Selection listener
        gridMechanics.addSelectionListener(event -> {
            mechanic =(Mechanic)gridMechanics.getSelectedRow();
            enable = true;
        });

        // Delete Mechanic
        buttonDeleteMechanic.addClickListener(eventButton -> {
            if(enable == true) {
                try {
                    DAO.getInstance().deleteMechanic(mechanic.getID());
                    FillGrid();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Edit Mechanic
        buttonEditMechanic.addClickListener(eventButton -> {
            if(enable == true) {
                WindowEditMechanic window = new WindowEditMechanic(mechanic.getID());
                UI.getCurrent().addWindow(window);
            }
        });

        //Statistics
        buttonStatistics.addClickListener(eventButton -> {
            if(enable == true) {
                WindowStatisticsMechanic window = new WindowStatisticsMechanic(mechanic);
                UI.getCurrent().addWindow(window);
            }
        });


    }
}

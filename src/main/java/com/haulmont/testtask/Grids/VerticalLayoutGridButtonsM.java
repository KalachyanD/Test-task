package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import dao.DAO;
import models.Mechanic;
import models.Order;


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

    public VerticalLayoutGridButtonsM(){
        gridMechanics.setHeight("300");
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);
        addComponent(gridMechanics);
        addComponent(buttonDeleteMechanic);
        addComponent(buttonEditMechanic);
        addComponent(buttonAddMechanic);
        addComponent(buttonStatistics);

        UpdateGrid();

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
                    UpdateGrid();
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
                VerticalLayoutGridButtonsM m = new VerticalLayoutGridButtonsM();
                VerticalLayoutGridButtonsM.WindowStatisticsMechanic window = m.new WindowStatisticsMechanic(mechanic);
                UI.getCurrent().addWindow(window);
            }
        });
    }

    public void UpdateGrid() {
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

    private class WindowStatisticsMechanic extends Window {

        private Label label;
        private int count = 0;

        public WindowStatisticsMechanic(Mechanic mechanic){
            super(mechanic.getName());
            preload(mechanic);
            buildLayout();
        }

        private void preload(Mechanic mechanic){
            List<Order> orders = new ArrayList<>();
            try {
                orders = DAO.getInstance().LoadAllOrders();
            } catch (SQLException e) {

            }

            for(int i = 0;i < orders.size();++i){
                if(mechanic.getID() == orders.get(i).getMechanic().getID()){
                    ++count;
                }
            }
        }

        private void buildLayout(){
            center(); //Position of window
            setClosable(true); // Disable the close button
            setModal(true); // Enable modal window mode

            label = new Label("Orders: "+count+".");

            VerticalLayout verticalMain = new VerticalLayout (label);
            verticalMain.setSpacing(false);
            verticalMain.setMargin(false);

            setContent(verticalMain);
        }
    }
}

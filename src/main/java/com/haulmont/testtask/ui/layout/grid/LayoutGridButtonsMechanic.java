package com.haulmont.testtask.ui.layout.grid;


import com.haulmont.testtask.ui.window.mechanic.WindowAddMechanic;
import com.haulmont.testtask.ui.window.mechanic.WindowEditMechanic;
import com.haulmont.testtask.ui.window.mechanic.WindowStatisticMechanic;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.haulmont.testtask.dao.MechanicDAO;
import com.haulmont.testtask.model.Mechanic;

/**
 * Created by User on 04.08.2017.
 */
public class LayoutGridButtonsMechanic extends VerticalLayout {

    private Grid gridMechanics = new Grid("Mechanics");
    public  Button buttonDeleteMechanic = new Button("Delete", this::deleteMechanic);
    public  Button buttonEditMechanic = new Button("Edit", this::editMechanic);
    private Button buttonAddMechanic = new Button("Add", this::addMechanic);
    public  Button buttonStatistic = new Button("Statistic", this::statisticsMechanic);
    private Mechanic mechanic;

    public LayoutGridButtonsMechanic() {
        buildLayout();
    }

    private void buildLayout() {
        gridMechanics.setHeight("300");
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridMechanics.addSelectionListener(event -> selection());
        addComponents(gridMechanics, buttonDeleteMechanic, buttonEditMechanic, buttonAddMechanic, buttonStatistic);
        updateGrid();
        gridMechanics.removeColumn("id");
        buttonDeleteMechanic.setEnabled(false);
        buttonEditMechanic.setEnabled(false);
        buttonStatistic.setEnabled(false);

    }

    public void updateGrid() {
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = MechanicDAO.getInstance().LoadAll();
        } catch (SQLException e) {
            Notification.show("System error", "Database error",
                    Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }

        BeanItemContainer<Mechanic> containerGridMechanics = new BeanItemContainer<>(Mechanic.class, mechanics);
        gridMechanics.setContainerDataSource(containerGridMechanics);
        gridMechanics.setColumnOrder("name", "surname", "patronymic", "hourlyPay");
    }

    private void addMechanic(Button.ClickEvent event) {
        WindowAddMechanic window = new WindowAddMechanic();
        UI.getCurrent().addWindow(window);
    }

    private void deleteMechanic(Button.ClickEvent event) {
        if (gridMechanics.getSelectedRow() == null) {
            buttonStatistic.setEnabled(false);
            buttonEditMechanic.setEnabled(false);
            buttonDeleteMechanic.setEnabled(false);
        } else {
            try {
                MechanicDAO.getInstance().delete(mechanic.getId());
                updateGrid();
                buttonEditMechanic.setEnabled(false);
                buttonDeleteMechanic.setEnabled(false);
                buttonStatistic.setEnabled(false);
            } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                Notification.show("Deleting is impossible", "This mechanic locate in Order Table.",
                        Notification.Type.WARNING_MESSAGE);
            } catch (SQLException e) {
                Notification.show("System error", "Database error",
                        Notification.Type.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void editMechanic(Button.ClickEvent event) {
        if (gridMechanics.getSelectedRow() == null) {
            buttonStatistic.setEnabled(false);
            buttonEditMechanic.setEnabled(false);
            buttonDeleteMechanic.setEnabled(false);
        } else {
            WindowEditMechanic window = new WindowEditMechanic(mechanic.getId());
            UI.getCurrent().addWindow(window);
        }
    }

    private void statisticsMechanic(Button.ClickEvent event) {
        if (gridMechanics.getSelectedRow() == null) {
            buttonStatistic.setEnabled(false);
            buttonEditMechanic.setEnabled(false);
            buttonDeleteMechanic.setEnabled(false);
        } else {
            WindowStatisticMechanic window = new WindowStatisticMechanic(mechanic);
            UI.getCurrent().addWindow(window);
        }
    }

    private void selection() {
        mechanic = (Mechanic) gridMechanics.getSelectedRow();
        buttonDeleteMechanic.setEnabled(true);
        buttonEditMechanic.setEnabled(true);
        buttonStatistic.setEnabled(true);
    }
}
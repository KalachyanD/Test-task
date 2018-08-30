package com.haulmont.testtask.grids;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.haulmont.testtask.windows.*;
import dao.DAO;
import models.Mechanic;
import models.Order;

/**
 * Created by User on 04.08.2017.
 */
public class VerticalLayoutGridButtonsM extends VerticalLayout {

    private Grid gridMechanics = new Grid("Mechanics");
    public Button buttonDeleteMechanic = new Button("Delete", this::deleteMechanic);
    public Button buttonEditMechanic = new Button("Edit", this::editMechanic);
    private Button buttonAddMechanic = new Button("Add", this::addMechanic);
    public Button buttonStatistic = new Button("Statistic", this::statisticsMechanic);
    private Mechanic mechanic;

    public VerticalLayoutGridButtonsM() {
        buildLayout();
    }

    private void buildLayout() {
        gridMechanics.setHeight("300");
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridMechanics.addSelectionListener(event -> selection());
        addComponents(gridMechanics, buttonDeleteMechanic, buttonEditMechanic, buttonAddMechanic, buttonStatistic);
        updateGrid();
        gridMechanics.removeColumn("ID");
        buttonDeleteMechanic.setEnabled(false);
        buttonEditMechanic.setEnabled(false);
        buttonStatistic.setEnabled(false);

    }

    public void updateGrid() {
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

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
                DAO.getInstance().deleteMechanic(mechanic.getID());
                updateGrid();
                buttonEditMechanic.setEnabled(false);
                buttonDeleteMechanic.setEnabled(false);
                buttonStatistic.setEnabled(false);
            } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                Notification.show("Deleting is impossible", "This mechanic locate in Order Table.",
                        Notification.Type.WARNING_MESSAGE);
            } catch (SQLException e) {
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
            WindowEditMechanic window = new WindowEditMechanic(mechanic.getID());
            UI.getCurrent().addWindow(window);
        }
    }

    private void statisticsMechanic(Button.ClickEvent event) {
        if (gridMechanics.getSelectedRow() == null) {
            buttonStatistic.setEnabled(false);
            buttonEditMechanic.setEnabled(false);
            buttonDeleteMechanic.setEnabled(false);
        } else {

            class WindowStatisticsMechanic extends Window {

                private Label label;
                private int count = 0;

                public WindowStatisticsMechanic(Mechanic mechanic) {
                    super(mechanic.getName());
                    preload(mechanic);
                    buildLayout();
                }

                private void preload(Mechanic mechanic) {
                    List<Order> orders = new ArrayList<>();
                    try {
                        orders = DAO.getInstance().LoadAllOrders();
                    } catch (SQLException e) {

                    }

                    for (int i = 0; i < orders.size(); ++i) {
                        if (mechanic.getID() == orders.get(i).getMechanic().getID()) {
                            ++count;
                        }
                    }
                }

                private void buildLayout() {
                    center(); //Position of window
                    setClosable(true); // Disable the close button
                    setModal(true); // Enable modal window mode

                    label = new Label("Orders: " + count + ".");

                    VerticalLayout verticalMain = new VerticalLayout(label);
                    verticalMain.setSpacing(false);
                    verticalMain.setMargin(false);

                    setContent(verticalMain);
                }
            }

            WindowStatisticsMechanic window = new WindowStatisticsMechanic(mechanic);
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
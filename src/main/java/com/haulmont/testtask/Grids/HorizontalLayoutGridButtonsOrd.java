package com.haulmont.testtask.Grids;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.haulmont.testtask.Windows.*;
import dao.DAO;
import models.Order;

/**
 * Created by User on 04.08.2017.
 */

public class HorizontalLayoutGridButtonsOrd extends HorizontalLayout {

    private Grid gridOrders = new Grid("Orders");
    private Button buttonAddOrder = new Button("Add", this::addOrder);
    public Button buttonEditOrder = new Button("Edit", this::editOrder);
    public Button buttonDeleteOrder = new Button("Delete", this::deleteOrder);
    private Order order;

    public HorizontalLayoutGridButtonsOrd() {
        buildLayout();
    }

    public void UpdateGrid() {

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }
        // Create firstField gridOrders bound to the containerOrders
        BeanItemContainer<Order> containerGridOrders = new BeanItemContainer<>(Order.class, orders);
        gridOrders.setContainerDataSource(containerGridOrders);
        gridOrders.setColumnOrder("description", "client", "mechanic", "startDate", "endDate", "cost", "status");
    }

    private void buildLayout() {
        gridOrders.setWidth("1000");
        gridOrders.setHeight("300");
        gridOrders.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridOrders.addSelectionListener(event -> selection());
        //gridOrders.addRow(filter);

        addComponents(gridOrders, buttonAddOrder, buttonEditOrder, buttonDeleteOrder);
        UpdateGrid();
        gridOrders.removeColumn("ID");
        buttonDeleteOrder.setEnabled(false);
        buttonEditOrder.setEnabled(false);
    }

    private void addOrder(Button.ClickEvent event) {
        WindowAddOrder window = new WindowAddOrder();
        UI.getCurrent().addWindow(window);
    }

    private void selection() {
        order = (Order) gridOrders.getSelectedRow();
        buttonDeleteOrder.setEnabled(true);
        buttonEditOrder.setEnabled(true);
    }

    private void deleteOrder(Button.ClickEvent event) {
        try {
            DAO.getInstance().deleteOrder(order.getID());
            UpdateGrid();
            buttonDeleteOrder.setEnabled(false);
            buttonEditOrder.setEnabled(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void editOrder(Button.ClickEvent event) {
        WindowEditOrder window = new WindowEditOrder(order.getID());
        UI.getCurrent().addWindow(window);
    }
}
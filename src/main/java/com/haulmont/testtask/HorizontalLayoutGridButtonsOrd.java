package com.haulmont.testtask;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import dao.DAO;
import models.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.08.2017.
 */
public class HorizontalLayoutGridButtonsOrd extends HorizontalLayout {

    Grid gridOrders = new Grid("Заказы");
    Button buttonAddOrder = new Button("Add");
    Button buttonEditOrder = new Button("Edit");
    Button buttonDeleteOrder = new Button("Delete");

    public HorizontalLayoutGridButtonsOrd(){

        gridOrders.setWidth("1000");
        gridOrders.setHeight("300");

        addComponent(gridOrders);
        addComponent(buttonAddOrder);
        addComponent(buttonEditOrder);
        addComponent(buttonDeleteOrder);

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }

        BeanItemContainer<Order> containerGridOrders = new BeanItemContainer<>(Order.class, orders);
        // Create a gridOrders bound to the containerOrders
        gridOrders.removeAllColumns();
        gridOrders.setContainerDataSource(containerGridOrders);


        // Add Order
        buttonAddOrder.addClickListener(event -> {
            WindowAddOrder window = new WindowAddOrder();
            // Add it to the root component
            UI.getCurrent().addWindow(window);
        });

        // Delete Order
        gridOrders.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridOrders.addSelectionListener(event -> {
            Order order =(Order)gridOrders.getSelectedRow();
            buttonDeleteOrder.addClickListener(eventButton -> {
                try {
                    DAO.getInstance().deleteOrder(order.getID());
                    Page.getCurrent().reload();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        });

        //Edit Order
        gridOrders.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridOrders.addSelectionListener(event -> {
            Order order =(Order)gridOrders.getSelectedRow();
            buttonEditOrder.addClickListener(eventButton -> {
                WindowEditOrder window = new WindowEditOrder(order.getID());
                UI.getCurrent().addWindow(window);
            });
        });
    }
}

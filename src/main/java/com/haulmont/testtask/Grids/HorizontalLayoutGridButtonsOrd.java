package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.WindowAddOrder;
import com.haulmont.testtask.Windows.WindowEditOrder;
import com.vaadin.data.util.BeanItemContainer;
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

    private Grid gridOrders = new Grid("Orders");
    private Button buttonAddOrder = new Button("Add");
    private Button buttonEditOrder = new Button("Edit");
    private Button buttonDeleteOrder = new Button("Delete");
    private Order order;
    private boolean enable = false;


    public void FillGrid() {

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }

        BeanItemContainer<Order> containerGridOrders = new BeanItemContainer<>(Order.class, orders);
        // Create firstField gridOrders bound to the containerOrders
        gridOrders.removeAllColumns();
        gridOrders.setContainerDataSource(containerGridOrders);
    }

    public HorizontalLayoutGridButtonsOrd() {

        gridOrders.setWidth("1000");
        gridOrders.setHeight("300");
        gridOrders.setSelectionMode(Grid.SelectionMode.SINGLE);

        addComponent(gridOrders);
        addComponent(buttonAddOrder);
        addComponent(buttonEditOrder);
        addComponent(buttonDeleteOrder);

        FillGrid();

        // Add Order
        buttonAddOrder.addClickListener(event -> {
            WindowAddOrder window = new WindowAddOrder();
            UI.getCurrent().addWindow(window);
        });

        //Selection Listener
        gridOrders.addSelectionListener(event -> {
            order =(Order)gridOrders.getSelectedRow();
            enable = true;
        });

        // Delete Order
        buttonDeleteOrder.addClickListener(eventButton -> {
            if(enable == true) {
                try {
                    DAO.getInstance().deleteOrder(order.getID());
                    FillGrid();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Edit Order
        buttonEditOrder.addClickListener(eventButton -> {
            if(enable == true) {
                WindowEditOrder window = new WindowEditOrder(order.getID());
                UI.getCurrent().addWindow(window);
            }
        });
    }
}

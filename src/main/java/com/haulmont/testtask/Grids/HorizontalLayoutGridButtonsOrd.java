package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.WindowAddOrder;
import com.haulmont.testtask.Windows.WindowEditOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
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
    private Button buttonAddOrder = new Button("Add", this::addOrder);
    private Button buttonEditOrder = new Button("Edit",this::editOrder);
    private Button buttonDeleteOrder = new Button("Delete",this::deleteOrder);
    private Order order;
    private boolean enable = false;
    private TextField filter = new TextField("Filter...");

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
        gridOrders.setColumnOrder("description","client","mechanic","startDate","endDate","cost","status");
    }

    private void buildLayout(){
        gridOrders.setWidth("1000");
        gridOrders.setHeight("300");
        gridOrders.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridOrders.addSelectionListener(event -> selectionOrder());
        //gridOrders.addRow(filter);

        addComponents(gridOrders,buttonAddOrder,buttonEditOrder,buttonDeleteOrder);
        UpdateGrid();
        gridOrders.removeColumn("ID");
    }

    private void addOrder(Button.ClickEvent event){
        WindowAddOrder window = new WindowAddOrder();
        UI.getCurrent().addWindow(window);
    }

    private void selectionOrder(){
        order =(Order)gridOrders.getSelectedRow();
        enable = true;
    }

    private void deleteOrder(Button.ClickEvent event){
        if(enable == true) {
            try {
                DAO.getInstance().deleteOrder(order.getID());
                UpdateGrid();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editOrder(Button.ClickEvent event){
        if(enable == true) {
            WindowEditOrder window = new WindowEditOrder(order.getID());
            UI.getCurrent().addWindow(window);
        }
    }
}
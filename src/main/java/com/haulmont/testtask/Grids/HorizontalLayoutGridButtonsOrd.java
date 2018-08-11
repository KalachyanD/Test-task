package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.WindowAddOrder;
import com.haulmont.testtask.Windows.WindowEditOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
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
    private Button buttonAddOrder = new Button("Add", this::addOrder);
    private Button buttonEditOrder = new Button("Edit",this::editOrder);
    private Button buttonDeleteOrder = new Button("Delete",this::deleteOrder);
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
        buildLayout();

    }

    private void buildLayout(){
        gridOrders.setWidth("1000");
        gridOrders.setHeight("300");
        gridOrders.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridOrders.addSelectionListener(event -> selection());

        addComponent(gridOrders);
        addComponent(buttonAddOrder);
        addComponent(buttonEditOrder);
        addComponent(buttonDeleteOrder);

        FillGrid();
    }

    private void addOrder(Button.ClickEvent event){
        WindowAddOrder window = new WindowAddOrder();
        UI.getCurrent().addWindow(window);
    }

    private void selection(){
        order =(Order)gridOrders.getSelectedRow();
        enable = true;
    }

    private void deleteOrder(Button.ClickEvent event){
        if(enable == true) {
            try {
                DAO.getInstance().deleteOrder(order.getID());
                FillGrid();
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

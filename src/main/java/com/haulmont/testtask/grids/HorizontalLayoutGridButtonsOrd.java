package com.haulmont.testtask.grids;

import com.haulmont.testtask.windows.order.WindowAddOrder;
import com.haulmont.testtask.windows.order.WindowEditOrder;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import models.Client;
import models.Order;
import models.Status;

/**
 * Created by User on 04.08.2017.
 */

public class HorizontalLayoutGridButtonsOrd extends HorizontalLayout {

    private Grid gridOrders = new Grid("Orders");
    private Button buttonAddOrder = new Button("Add", this::addOrder);
    public Button buttonEditOrder = new Button("Edit", this::editOrder);
    public Button buttonDeleteOrder = new Button("Delete", this::deleteOrder);
    private Button buttonApplyFilter = new Button("Apply", this::applyFilter);
    private TextField filterFieldDescription = new TextField();
    private NativeSelect filterFieldClient = new NativeSelect();
    private NativeSelect filterFieldStatus = new NativeSelect();
    private Order order;

    public HorizontalLayoutGridButtonsOrd() {
        buildLayout();
    }

    public void updateGrid() {

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Create firstField gridOrders bound to the containerOrders
        BeanItemContainer<Order> containerGridOrders = new BeanItemContainer<>(Order.class, orders);
        gridOrders.setContainerDataSource(containerGridOrders);
        gridOrders.setColumnOrder("description", "client", "status", "mechanic", "startDate", "endDate", "cost");
    }

    private void updateGrid(List<Order> orders){
        BeanItemContainer<Order> containerGridOrders = new BeanItemContainer<>(Order.class, orders);
        gridOrders.setContainerDataSource(containerGridOrders);
        gridOrders.setColumnOrder("description", "client", "status", "mechanic", "startDate", "endDate", "cost");
    }

    private void buildLayout() {
        gridOrders.setWidth("1000");
        gridOrders.setHeight("300");
        gridOrders.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridOrders.addSelectionListener(event -> selection());

        filterFieldDescription.setMaxLength(50);

        addComponents(gridOrders, buttonAddOrder, buttonEditOrder, buttonDeleteOrder);
        updateGrid();
        gridOrders.removeColumn("ID");
        buttonDeleteOrder.setEnabled(false);
        buttonEditOrder.setEnabled(false);

        HeaderRow filterRow = gridOrders.appendHeaderRow();

        HeaderCell descriptionFilter = filterRow.getCell("description");
        filterFieldDescription.setImmediate(true);
        descriptionFilter.setComponent(filterFieldDescription);

        HeaderCell clientFilter = filterRow.getCell("client");
        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        filterFieldClient.addItems(clients);
        filterFieldClient.setNullSelectionAllowed(true);
        filterFieldClient.setValue(null);
        filterFieldClient.setNullSelectionItemId("");
        clientFilter.setComponent(filterFieldClient);

        HeaderCell statusFilter = filterRow.getCell("status");
        filterFieldStatus.addItems(Status.Planned, Status.Completed, Status.Accepted);
        filterFieldStatus.setNullSelectionAllowed(true);
        filterFieldStatus.setValue(null);
        filterFieldStatus.setNullSelectionItemId("");
        statusFilter.setComponent(filterFieldStatus);

        HeaderCell buttonFilter = filterRow.getCell("mechanic");
        buttonFilter.setComponent(buttonApplyFilter);

        filterFieldClient.addValueChangeListener(event -> selectChange(event, filterFieldClient));
        filterFieldDescription.addTextChangeListener(event -> textChange(event, filterFieldDescription));
        filterFieldStatus.addValueChangeListener(event -> selectChange(event, filterFieldStatus));

        filterFieldDescription.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        buttonApplyFilter.setEnabled(false);
    }

    private void selection() {
        order = (Order) gridOrders.getSelectedRow();
        buttonDeleteOrder.setEnabled(true);
        buttonEditOrder.setEnabled(true);
    }

    private void addOrder(Button.ClickEvent event) {
        WindowAddOrder window = new WindowAddOrder();
        UI.getCurrent().addWindow(window);
    }

    private void deleteOrder(Button.ClickEvent event) {
        if (gridOrders.getSelectedRow() == null) {
            buttonDeleteOrder.setEnabled(false);
            buttonEditOrder.setEnabled(false);
        } else {
            try {
                DAO.getInstance().deleteOrder(order.getID());
                updateGrid();
                buttonDeleteOrder.setEnabled(false);
                buttonEditOrder.setEnabled(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editOrder(Button.ClickEvent event) {
        if (gridOrders.getSelectedRow() == null) {
            buttonDeleteOrder.setEnabled(false);
            buttonEditOrder.setEnabled(false);
        } else {
            WindowEditOrder window = new WindowEditOrder(order.getID());
            UI.getCurrent().addWindow(window);
        }
    }

    private void applyFilter(Button.ClickEvent event) {
        List<Order> allOrders = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        try {
            allOrders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!filterFieldDescription.isEmpty() && filterFieldClient.isEmpty() && filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldDescription.getValue().toString().equals(allOrders.get(i).getDescription().toString())){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(filterFieldDescription.isEmpty() && !filterFieldClient.isEmpty() && filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldClient.getValue().toString().equals(allOrders.get(i).getClient().toString())){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(filterFieldDescription.isEmpty() && filterFieldClient.isEmpty() && !filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldStatus.getValue().toString().equals(allOrders.get(i).getStatus().toString())){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(filterFieldDescription.isEmpty() && !filterFieldClient.isEmpty() && !filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldClient.getValue().toString().equals(allOrders.get(i).getClient().toString())  &&
                        filterFieldStatus.getValue() == allOrders.get(i).getStatus()){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(!filterFieldDescription.isEmpty() && !filterFieldClient.isEmpty() && !filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldDescription.getValue().toString().equals(allOrders.get(i).getDescription().toString()) &&
                        filterFieldClient.getValue().toString().equals(allOrders.get(i).getClient().toString())  &&
                        filterFieldStatus.getValue() == allOrders.get(i).getStatus()){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(!filterFieldDescription.isEmpty() && !filterFieldClient.isEmpty() && filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldDescription.getValue().toString().equals(allOrders.get(i).getDescription().toString()) &&
                        filterFieldClient.getValue().toString().equals(allOrders.get(i).getClient().toString())){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(!filterFieldDescription.isEmpty() && filterFieldClient.isEmpty() && !filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldDescription.getValue().toString().equals(allOrders.get(i).getDescription().toString()) &&
                        filterFieldStatus.getValue() == allOrders.get(i).getStatus()){
                    orders.add(allOrders.get(i));
                }
            }
        }
        updateGrid(orders);
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField) {
        textField.setValue(event.getText());
        textField.setCursorPosition(event.getCursorPosition());

        if (filterFieldClient.isEmpty() && filterFieldDescription.isEmpty() && filterFieldStatus.isEmpty()) {
            buttonApplyFilter.setEnabled(false);
            updateGrid();
        } else {
            buttonApplyFilter.setEnabled(true);
        }
    }

    private void selectChange(Property.ValueChangeEvent event, NativeSelect select) {
        select.setValue(event.getProperty().getValue());

        if (filterFieldClient.isEmpty() && filterFieldDescription.isEmpty() && filterFieldStatus.isEmpty()) {
            buttonApplyFilter.setEnabled(false);
            updateGrid();
        } else {
            buttonApplyFilter.setEnabled(true);
        }
    }
}
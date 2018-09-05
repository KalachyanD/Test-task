package com.haulmont.testtask.ui.grids;

import com.haulmont.testtask.ui.windows.order.WindowAddOrder;
import com.haulmont.testtask.ui.windows.order.WindowEditOrder;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.haulmont.testtask.dao.ClientDAO;
import com.haulmont.testtask.dao.OrderDAO;
import com.haulmont.testtask.dao.dto.OrderDTO;
import com.haulmont.testtask.models.Client;
import com.haulmont.testtask.models.Order;
import com.haulmont.testtask.models.Status;

/**
 * Created by User on 04.08.2017.
 */

public class LayoutGridButtonsOrder extends HorizontalLayout {

    private Grid gridOrders = new Grid("Orders");
    private Button buttonAddOrder = new Button("Add", this::addOrder);
    public Button buttonEditOrder = new Button("Edit", this::editOrder);
    public Button buttonDeleteOrder = new Button("Delete", this::deleteOrder);
    private Button buttonApplyFilter = new Button("Apply", this::applyFilter);
    private TextField filterFieldDescription = new TextField();
    private NativeSelect filterFieldClient = new NativeSelect();
    private NativeSelect filterFieldStatus = new NativeSelect();
    private Order order;

    public LayoutGridButtonsOrder() {
        buildLayout();
    }

    public void updateGrid() {

        List<OrderDTO> orders = new ArrayList<>();
        try {
            orders = OrderDAO.getInstance().LoadAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Create firstField gridOrders bound to the containerOrders
        BeanItemContainer<OrderDTO> containerGridOrders = new BeanItemContainer<>(OrderDTO.class, orders);
        gridOrders.setContainerDataSource(containerGridOrders);
        gridOrders.getColumn("clientDTO").setHeaderCaption("Client");
        gridOrders.getColumn("mechanicDTO").setHeaderCaption("Mechanic");
        gridOrders.setColumnOrder("description", "clientDTO", "status", "mechanicDTO", "startDate", "endDate", "cost");
    }

    private void updateGrid(List<OrderDTO> orders){
        BeanItemContainer<OrderDTO> containerGridOrders = new BeanItemContainer<>(OrderDTO.class, orders);
        gridOrders.setContainerDataSource(containerGridOrders);
        gridOrders.setColumnOrder("description", "clientDTO", "status", "mechanicDTO", "startDate", "endDate", "cost");
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

        HeaderCell clientFilter = filterRow.getCell("clientDTO");
        List<Client> clients = new ArrayList<>();
        try {
            clients = ClientDAO.getInstance().LoadAll();
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

        HeaderCell buttonFilter = filterRow.getCell("mechanicDTO");
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
                OrderDAO.getInstance().delete(order.getID());
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
        List<OrderDTO> allOrders = new ArrayList<>();
        List<OrderDTO> orders = new ArrayList<>();
        try {
            allOrders = OrderDAO.getInstance().LoadAll();
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
                if(filterFieldClient.getValue().toString().equals(allOrders.get(i).getClientDTO().toString())){
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
                if(filterFieldClient.getValue().toString().equals(allOrders.get(i).getClientDTO().toString())  &&
                        filterFieldStatus.getValue() == allOrders.get(i).getStatus()){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(!filterFieldDescription.isEmpty() && !filterFieldClient.isEmpty() && !filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldDescription.getValue().toString().equals(allOrders.get(i).getDescription().toString()) &&
                        filterFieldClient.getValue().toString().equals(allOrders.get(i).getClientDTO().toString())  &&
                        filterFieldStatus.getValue() == allOrders.get(i).getStatus()){
                    orders.add(allOrders.get(i));
                }
            }
        }
        if(!filterFieldDescription.isEmpty() && !filterFieldClient.isEmpty() && filterFieldStatus.isEmpty()){
            for(int i = 0;i<allOrders.size();++i){
                if(filterFieldDescription.getValue().toString().equals(allOrders.get(i).getDescription().toString()) &&
                        filterFieldClient.getValue().toString().equals(allOrders.get(i).getClientDTO().toString())){
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
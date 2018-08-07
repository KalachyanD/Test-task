package com.haulmont.testtask.Windows;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.07.2017.
 */
public class WindowEditOrder extends Window  {


    TextField fieldDescription = new TextField("Description");
    TextField fieldClientID  = new TextField("Client ID");
    TextField fieldMechanicID = new TextField("Mechanic ID");
    TextField fieldDateStart = new TextField("Date start");
    TextField fieldDateFinish = new TextField("Date finish");
    TextField fieldCost = new TextField("Cost");
    NativeSelect selectStatus = new NativeSelect("Status");
    int orderID;

    public WindowEditOrder(int orderID) {

        super("Edit Order"); // Set window caption
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }

        for(int i = 0;i < orders.size();++i){
            if(orderID == orders.get(i).getID()){
                this.orderID = i;
            }

        }

        fieldDescription.setValue(orders.get(this.orderID).getDescription());
        fieldClientID.setValue(Integer.toString(orders.get(this.orderID).getClient().getID()));
        fieldMechanicID.setValue(Integer.toString(orders.get(this.orderID).getMechanic().getID()));
        fieldDateStart.setValue(LocalDateTime.now().plusMinutes(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        fieldDateFinish.setValue(LocalDateTime.now().plusMinutes(500).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        fieldCost.setValue(Double.toString(orders.get(this.orderID).getCost()));
        selectStatus.addItem(Order.Status.Start);
        selectStatus.addItem(Order.Status.Process);
        selectStatus.addItem(Order.Status.Finish);
        selectStatus.setValue(orders.get(this.orderID).getStatus());
        selectStatus.setNullSelectionAllowed(false);

        VerticalLayout verticalFields = new VerticalLayout ();
        verticalFields.setSpacing(false);
        verticalFields.setMargin(false);

        verticalFields.addComponent(fieldDescription);
        verticalFields.addComponent(fieldClientID);
        verticalFields.addComponent(fieldMechanicID);
        verticalFields.addComponent(fieldDateStart);
        verticalFields.addComponent(fieldDateFinish);
        verticalFields.addComponent(fieldCost);
        verticalFields.addComponent(selectStatus);

        HorizontalLayout horizontButtons = new HorizontalLayout();
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);

        horizontButtons.addComponent(new Button("OK",event -> {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime dateStart =  LocalDateTime.parse(fieldDateStart.getValue(), formatter);
            LocalDateTime dateFinish = LocalDateTime.parse(fieldDateFinish.getValue(), formatter);
            try {
                Order.Status status = Order.Status.valueOf(selectStatus.getValue().toString());
                DAO.getInstance().updateOrder(orderID, fieldDescription.getValue(), Integer.parseInt(fieldClientID.getValue()), Integer.parseInt(fieldMechanicID.getValue()), dateStart, dateFinish, Double.parseDouble(fieldCost.getValue()), status);
                close();
                Page.getCurrent().reload();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        horizontButtons.addComponent(new Button("Cancel",event -> close()));

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontButtons);

        setContent(verticalMain);


    }

}
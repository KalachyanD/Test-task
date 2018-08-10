package com.haulmont.testtask.Windows;

import com.vaadin.ui.*;
import dao.DAO;
import models.Order;
import com.haulmont.testtask.UI.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.07.2017.
 */

public class WindowAddOrder extends Window  {

    private TextField fieldDescription = new TextField("Description");
    private TextField fieldClientID = new TextField("Client ID");
    private TextField fieldMechanicID = new TextField("Mechanic ID");
    private TextField fieldDateStart = new TextField("Date start");
    private TextField fieldDateFinish = new TextField("Date finish");
    private TextField fieldCost = new TextField("Cost");
    private NativeSelect selectStatus = new NativeSelect("Status");

    public WindowAddOrder() {

        super("Add Order"); // Set window caption

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }

        fieldDescription.setValue(orders.get(1).getDescription());
        fieldClientID.setValue(Integer.toString(orders.get(1).getClient().getID()));
        fieldMechanicID.setValue(Integer.toString(orders.get(1).getMechanic().getID()));
        fieldDateStart.setValue(LocalDateTime.now().plusMinutes(2).format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm:ss")));
        fieldDateFinish.setValue(LocalDateTime.now().plusMinutes(500).format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm:ss")));
        fieldCost.setValue(Double.toString(orders.get(1).getCost()));
        selectStatus.addItem(Order.Status.Planned);
        selectStatus.addItem(Order.Status.Completed);
        selectStatus.addItem(Order.Status.Accepted);
        selectStatus.setValue(Order.Status.Planned);
        selectStatus.setNullSelectionAllowed(false);

        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout ();
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        verticalFields.addComponent(fieldDescription);
        verticalFields.addComponent(fieldClientID);
        verticalFields.addComponent(fieldMechanicID);
        verticalFields.addComponent(fieldDateStart);
        verticalFields.addComponent(fieldDateFinish);
        verticalFields.addComponent(fieldCost);
        verticalFields.addComponent(selectStatus);

        HorizontalLayout horizonButtons = new HorizontalLayout();
        horizonButtons.setSpacing(false);
        horizonButtons.setMargin(false);

        horizonButtons.addComponent(new Button("OK", this::buttonClick));
        horizonButtons.addComponent(new Button("Cancel",event -> close()));

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizonButtons);

        setContent(verticalMain);


    }

    private void buttonClick(Button.ClickEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(fieldDateStart.getValue(), formatter);
        LocalDateTime dateFinish = LocalDateTime.parse(fieldDateFinish.getValue(), formatter);
        try {
            Order.Status status = Order.Status.valueOf(selectStatus.getValue().toString());
            DAO.getInstance().storeOrder(fieldDescription.getValue(), Integer.parseInt(fieldClientID.getValue()),
                    Integer.parseInt(fieldMechanicID.getValue()), dateStart, dateFinish,
                    Double.parseDouble(fieldCost.getValue()), status);
            getUI().design.horizontalLayoutGridButtonsOrd.FillGrid();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }
}

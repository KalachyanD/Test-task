package com.haulmont.testtask;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by User on 21.07.2017.
 */
class WindowAddOrder extends Window  {

    TextField fieldDescription;
    TextField fieldClientID;
    TextField fieldMechanicID;
    TextField fieldDateStart;
    TextField fieldDateFinish;
    TextField fieldCost;

    public WindowAddOrder() {

        super("Add Order"); // Set window caption
        fieldDescription = new TextField("Description");
        fieldClientID  = new TextField("Client ID");
        fieldMechanicID = new TextField("Mechanic ID");
        fieldDateStart = new TextField("Date start");
        fieldDateFinish = new TextField("Date finish");
        fieldCost = new TextField("Cost");

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

        HorizontalLayout horizontButtons = new HorizontalLayout();
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);



        horizontButtons.addComponent(new Button("ОК",event -> {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime dateStart =  LocalDateTime.parse(fieldDateStart.getValue(), formatter);
            LocalDateTime dateFinish = LocalDateTime.parse(fieldDateFinish.getValue(), formatter);
            try {
                DAO.getInstance().storeOrder(fieldDescription.getValue(), Integer.parseInt(fieldClientID.getValue()), Integer.parseInt(fieldMechanicID.getValue()), dateStart, dateFinish, Double.parseDouble(fieldCost.getValue()), Order.Status.START);
                close();
                Page.getCurrent().reload();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        horizontButtons.addComponent(new Button("Отмена",event -> close()));

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontButtons);

        setContent(verticalMain);


    }

}
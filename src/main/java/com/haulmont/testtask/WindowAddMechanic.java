package com.haulmont.testtask;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Mechanic;

import java.sql.SQLException;

/**
 * Created by User on 21.07.2017.
 */
class WindowAddMechanic extends Window  {

    TextField fieldName;
    TextField fieldSurname;
    TextField fieldPatronymic;
    TextField fieldHourlyPay ;

    public WindowAddMechanic() {

        super("Add Mechanic"); // Set window caption
        fieldName = new TextField("Name");
        fieldSurname = new TextField("Surname");
        fieldPatronymic = new TextField("Patronymic");
        fieldHourlyPay = new TextField("Hourly pay");


        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout ();
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        verticalFields.addComponent(fieldName);
        verticalFields.addComponent(fieldSurname);
        verticalFields.addComponent(fieldPatronymic);
        verticalFields.addComponent(fieldHourlyPay);

        HorizontalLayout horizontButtons = new HorizontalLayout();
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);
        horizontButtons.addComponent(new Button("ОК",event -> {
            try {
                WindowAddMechanic.EventClickOk(fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldHourlyPay.getValue()));
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

    public static void EventClickOk(String fieldName, String fieldSurname, String fieldPatronymic, int fieldHourlyPay) throws SQLException {
        Mechanic mechanic = new Mechanic(0,fieldName, fieldSurname, fieldPatronymic, fieldHourlyPay);
        DAO.getInstance().storeMechanic(mechanic);

    }
}
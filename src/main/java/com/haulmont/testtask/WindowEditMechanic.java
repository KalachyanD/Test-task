package com.haulmont.testtask;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;

import java.sql.SQLException;

/**
 * Created by User on 21.07.2017.
 */
public class WindowEditMechanic extends Window {

    int mechanicID;
    TextField fieldName;
    TextField fieldSurname;
    TextField fieldPatronymic;
    TextField fieldHourlyPay ;

    public WindowEditMechanic(int mechanicID) {

        super("Edit Mechanic"); // Set window caption
        fieldName = new TextField("Name");
        fieldSurname = new TextField("Surname");
        fieldPatronymic = new TextField("Patronymic");
        fieldHourlyPay = new TextField("HourlyPay");



        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout ();
        verticalFields.setSpacing(false);
        verticalFields.setMargin(false);

        verticalFields.addComponent(fieldName);
        verticalFields.addComponent(fieldSurname);
        verticalFields.addComponent(fieldPatronymic);
        verticalFields.addComponent(fieldHourlyPay);

        HorizontalLayout horizontButtons = new HorizontalLayout();
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);
        horizontButtons.addComponent(new Button("OK", event -> {
            try {
                WindowEditMechanic.EventClickOk(mechanicID, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldHourlyPay.getValue()));
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

    public static void EventClickOk(int mechanicID, String fieldName, String fieldSurname, String fieldPatronymic, int fieldHourlyPay) throws SQLException {
        DAO.getInstance().updateMechanic(mechanicID , fieldName, fieldSurname, fieldPatronymic, fieldHourlyPay);
    }

}
package com.haulmont.testtask;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;
import models.Mechanic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.07.2017.
 */
public class WindowEditMechanic extends Window {


    TextField fieldName = new TextField("Name");
    TextField fieldSurname = new TextField("Surname");
    TextField fieldPatronymic = new TextField("Patronymic");
    TextField fieldHourlyPay = new TextField("HourlyPay");
    int mechanicID;

    public WindowEditMechanic(int mechanicID) {

        super("Edit Mechanic"); // Set window caption
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }

        for(int i = 0;i < mechanics.size();++i){
            if(mechanicID == mechanics.get(i).getID()){
                this.mechanicID = i;
            }

        }

        fieldName.setValue(mechanics.get(this.mechanicID).getName());
        fieldSurname.setValue(mechanics.get(this.mechanicID).getSurname());
        fieldPatronymic.setValue(mechanics.get(this.mechanicID).getPatronymic());
        fieldHourlyPay.setValue(Integer.toString(mechanics.get(this.mechanicID).getHourlyPay()));

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
                DAO.getInstance().updateMechanic(mechanicID, fieldName.getValue(), fieldSurname.getValue(), fieldPatronymic.getValue(), Integer.parseInt(fieldHourlyPay.getValue()));
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
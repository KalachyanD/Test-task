package com.haulmont.testtask.ui.window.client;

import com.vaadin.ui.*;

import java.sql.SQLException;

import com.haulmont.testtask.dao.ClientDAO;

public class WindowAddClient extends AbstractWindowClient {

    public WindowAddClient(){
        super("Add client"); // Set window caption
        setOk(new Button("OK", this::ok));
        buildWindow();
        validation();
    }

    private void ok(Button.ClickEvent event){
        try {
            ClientDAO.getInstance().create(
                    getName().getValue(),
                    getName().getValue(),
                    getPatronymic().getValue(),
                    Long.parseLong(getPhoneNumber().getConvertedValue().toString()));
            getUI().design.horizontalLayoutTopGrids.verticalGridC.updateGrid();
            close();
        } catch (SQLException e) {
            Notification.show("System error", "Database error", Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

}
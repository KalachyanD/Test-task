package com.haulmont.testtask.ui.window.client;

import com.haulmont.testtask.model.Client;
import com.vaadin.ui.*;

import java.sql.SQLException;

import com.haulmont.testtask.dao.ClientDAO;

public class WindowEditClient extends AbstractWindowClient {

    private Long id;

    public WindowEditClient(Long id){
        super("Edit client"); // Set window caption
        setOk(new Button("OK", this::ok));
        buildWindow();
        preload(id);
        validation();
    }

    @Override
    protected void preload(Long id){
        this.id = id;
        try {
            Client client = ClientDAO.getInstance().get(id);
            getName().setValue(client.getName());
            getSurname().setValue(client.getSurname());
            getPatronymic().setValue(client.getPatronymic());
            getPhoneNumber().setValue(Long.toString(client.getPhoneNumber()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ok(Button.ClickEvent event){
        try {
            ClientDAO.getInstance().edit(
                    id,
                    getName().getValue(),
                    getSurname().getValue(),
                    getPatronymic().getValue(),
                    Long.parseLong(getPhoneNumber().getConvertedValue().toString()));
            getUI().design.horizontalLayoutTopGrids.verticalGridC.updateGrid();
            getUI().design.horizontalLayoutGridButtonsOrd.updateGrid();
            getUI().design.horizontalLayoutTopGrids.verticalGridC.buttonEditClient.setEnabled(false);
            getUI().design.horizontalLayoutTopGrids.verticalGridC.buttonDeleteClient.setEnabled(false);
            close();
        } catch (SQLException e) {
            Notification.show("System error", "Database error", Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }
}
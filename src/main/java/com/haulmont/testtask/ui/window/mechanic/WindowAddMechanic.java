package com.haulmont.testtask.ui.window.mechanic;

import com.vaadin.ui.*;

import com.haulmont.testtask.dao.MechanicDAO;

import java.sql.SQLException;

public class WindowAddMechanic extends AbstractWindowMechanic {

    public WindowAddMechanic() {
        super("Add Mechanic"); // Set window caption
        setOk(new Button("OK", this::ok));
        buildWindow();
        validation();
    }

    private void ok(Button.ClickEvent event) {
        try {
            MechanicDAO.getInstance().create(
                    getName().getValue(),
                    getSurname().getValue(),
                    getPatronymic().getValue(),
                    Double.parseDouble(getHourlyPay().getConvertedValue().toString()));
            getUI().design.horizontalLayoutTopGrids.verticalGridM.updateGrid();
            close();
        } catch (SQLException e) {
            Notification.show("System error", "Database error", Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }
}
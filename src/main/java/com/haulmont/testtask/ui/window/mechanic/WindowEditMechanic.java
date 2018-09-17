package com.haulmont.testtask.ui.window.mechanic;

import com.vaadin.ui.*;

import java.sql.SQLException;

import com.haulmont.testtask.dao.MechanicDAO;
import com.haulmont.testtask.model.Mechanic;

public class WindowEditMechanic extends AbstractWindowMechanic {

    private Long id;

    public WindowEditMechanic(Long id){
        super("Edit mechanic"); // Set window caption
        setOk(new Button("OK", this::ok));
        preload(id);
        buildWindow();
        validation();
    }

    @Override
    protected void preload(Long id){
        this.id = id;
        try {
            Mechanic mechanic = MechanicDAO.getInstance().get(id);
            getName().setValue(mechanic.getName());
            getSurname().setValue(mechanic.getSurname());
            getPatronymic().setValue(mechanic.getPatronymic());
            getHourlyPay().setValue(Double.toString(mechanic.getHourlyPay()));
        } catch (SQLException e) {
            Notification.show("System error", "Database error", Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    private void ok(Button.ClickEvent event){
        try {
            MechanicDAO.getInstance().edit(
                    id,
                    getName().getValue(),
                    getSurname().getValue(),
                    getPatronymic().getValue(),
                    Double.parseDouble(getHourlyPay().getConvertedValue().toString()));
            getUI().design.horizontalLayoutTopGrids.verticalGridM.updateGrid();
            getUI().design.horizontalLayoutGridButtonsOrd.updateGrid();
            getUI().design.horizontalLayoutTopGrids.verticalGridM.buttonEditMechanic.setEnabled(false);
            getUI().design.horizontalLayoutTopGrids.verticalGridM.buttonDeleteMechanic.setEnabled(false);
            getUI().design.horizontalLayoutTopGrids.verticalGridM.buttonStatistic.setEnabled(false);
            close();
        } catch (SQLException e) {
            Notification.show("System error", "Database error",
                    Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }
}
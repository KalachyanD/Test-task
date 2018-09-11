package com.haulmont.testtask.ui.window.mechanic;

import com.haulmont.testtask.dao.MechanicDAO;
import com.haulmont.testtask.model.Mechanic;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.sql.SQLException;

public class WindowStatisticMechanic extends Window {

    private Label label;
    private long count = 0;

    public WindowStatisticMechanic(Mechanic mechanic) {
        super(mechanic.getName());
        preload(mechanic);
        buildLayout();
    }

    private void preload(Mechanic mechanic) {
        try {
            count = MechanicDAO.getInstance().statistic(mechanic.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void buildLayout() {
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        label = new Label("Orders: " + count + ".");

        VerticalLayout verticalMain = new VerticalLayout(label);
        verticalMain.setSpacing(false);
        verticalMain.setMargin(false);

        setContent(verticalMain);
    }
}
package com.haulmont.testtask.ui.windows.mechanic;

import com.haulmont.testtask.dao.OrderDAO;
import com.haulmont.testtask.dao.dto.OrderDTO;
import com.haulmont.testtask.models.Mechanic;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WindowStatisticMechanic extends Window {

    private Label label;
    private int count = 0;

    public WindowStatisticMechanic(Mechanic mechanic) {
        super(mechanic.getName());
        preload(mechanic);
        buildLayout();
    }

    private void preload(Mechanic mechanic) {
        List<OrderDTO> orders = new ArrayList<>();
        try {
            orders = OrderDAO.getInstance().LoadAll();
        } catch (SQLException e) {

        }

        for (int i = 0; i < orders.size(); ++i) {
            if (mechanic.getID() == orders.get(i).getMechanicDTO().getId()) {
                ++count;
            }
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
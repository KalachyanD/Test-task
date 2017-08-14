package com.haulmont.testtask;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import dao.DAO;
import models.Mechanic;
import models.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 08.08.2017.
 */

public class WindowStatistics extends Window {

    Label label;
    int count;

    public WindowStatistics(Mechanic mechanic){

        super(mechanic.getName());
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode
        count = 0;

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }

        for(int i = 0;i < orders.size();++i){
            if(mechanic.getID() == orders.get(i).getMechanic().getID()){
                ++count;
            }
        }

        label = new Label("Orders: "+count+".");

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(false);
        verticalMain.setMargin(false);

        verticalMain.addComponent(label);
        setContent(verticalMain);

    }
}

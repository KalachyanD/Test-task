package com.haulmont.testtask;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;
import models.Mechanic;
import models.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 26.07.2017.
 */
public class VerticalLayoutUI extends VerticalLayout {

    HorizontalLayoutTopGrids horizontalLayoutTopGrids = new HorizontalLayoutTopGrids();
    HorizontalLayoutGridButtonsOrd horizontalLayoutGridButtonsOrd = new HorizontalLayoutGridButtonsOrd();

    public VerticalLayoutUI() {
        horizontalLayoutTopGrids.setMargin(true);
        horizontalLayoutTopGrids.setSpacing(true);
        addComponent(horizontalLayoutTopGrids);

        horizontalLayoutGridButtonsOrd.setMargin(true);
        horizontalLayoutGridButtonsOrd.setSpacing(true);
        addComponent(horizontalLayoutGridButtonsOrd);

    }
}
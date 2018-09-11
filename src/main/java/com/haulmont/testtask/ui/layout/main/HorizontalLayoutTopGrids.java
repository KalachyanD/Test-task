package com.haulmont.testtask.ui.layout.main;

import com.vaadin.ui.HorizontalLayout;

import com.haulmont.testtask.ui.layout.grid.LayoutGridButtonsClient;
import com.haulmont.testtask.ui.layout.grid.LayoutGridButtonsMechanic;

/**
 * Created by User on 04.08.2017.
 */
public class HorizontalLayoutTopGrids extends HorizontalLayout {

    public LayoutGridButtonsClient verticalGridC = new LayoutGridButtonsClient();
    public LayoutGridButtonsMechanic verticalGridM = new LayoutGridButtonsMechanic();

    public HorizontalLayoutTopGrids(){
        verticalGridC.setMargin(true);
        verticalGridC.setSpacing(true);
        verticalGridM.setMargin(true);
        verticalGridM.setSpacing(true);

        addComponents(verticalGridC,verticalGridM);

    }
}
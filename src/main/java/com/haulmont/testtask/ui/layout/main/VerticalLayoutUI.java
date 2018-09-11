package com.haulmont.testtask.ui.layout.main;

import com.vaadin.ui.*;

import com.haulmont.testtask.ui.layout.grid.LayoutGridButtonsOrder;


/**
 * Created by User on 26.07.2017.
 */
public class VerticalLayoutUI extends VerticalLayout {

    public HorizontalLayoutTopGrids horizontalLayoutTopGrids = new HorizontalLayoutTopGrids();
    public LayoutGridButtonsOrder horizontalLayoutGridButtonsOrd = new LayoutGridButtonsOrder();

    public VerticalLayoutUI() {
        horizontalLayoutTopGrids.setMargin(true);
        horizontalLayoutTopGrids.setSpacing(true);

        horizontalLayoutGridButtonsOrd.setMargin(true);
        horizontalLayoutGridButtonsOrd.setSpacing(true);

        addComponents(horizontalLayoutTopGrids,horizontalLayoutGridButtonsOrd);

    }
}
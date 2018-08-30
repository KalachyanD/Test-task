package com.haulmont.testtask.ui;

import com.vaadin.ui.*;

import com.haulmont.testtask.grids.HorizontalLayoutGridButtonsOrd;


/**
 * Created by User on 26.07.2017.
 */
public class VerticalLayoutUI extends VerticalLayout {

    public HorizontalLayoutTopGrids horizontalLayoutTopGrids = new HorizontalLayoutTopGrids();
    public HorizontalLayoutGridButtonsOrd horizontalLayoutGridButtonsOrd = new HorizontalLayoutGridButtonsOrd();

    public VerticalLayoutUI() {
        horizontalLayoutTopGrids.setMargin(true);
        horizontalLayoutTopGrids.setSpacing(true);

        horizontalLayoutGridButtonsOrd.setMargin(true);
        horizontalLayoutGridButtonsOrd.setSpacing(true);

        addComponents(horizontalLayoutTopGrids,horizontalLayoutGridButtonsOrd);

    }
}
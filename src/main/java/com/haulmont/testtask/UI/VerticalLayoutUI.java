package com.haulmont.testtask.UI;


import com.haulmont.testtask.Grids.HorizontalLayoutGridButtonsOrd;
import com.vaadin.ui.*;


/**
 * Created by User on 26.07.2017.
 */
public class VerticalLayoutUI extends VerticalLayout {

    public HorizontalLayoutTopGrids horizontalLayoutTopGrids = new HorizontalLayoutTopGrids();
    public HorizontalLayoutGridButtonsOrd horizontalLayoutGridButtonsOrd = new HorizontalLayoutGridButtonsOrd();

    public VerticalLayoutUI() {
        horizontalLayoutTopGrids.setMargin(true);
        horizontalLayoutTopGrids.setSpacing(true);
        addComponent(horizontalLayoutTopGrids);

        horizontalLayoutGridButtonsOrd.setMargin(true);
        horizontalLayoutGridButtonsOrd.setSpacing(true);
        addComponent(horizontalLayoutGridButtonsOrd);

    }
}
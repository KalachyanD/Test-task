package com.haulmont.testtask;

import com.vaadin.ui.HorizontalLayout;

/**
 * Created by User on 04.08.2017.
 */
public class HorizontalLayoutTopGrids extends HorizontalLayout {

    VerticalLayoutGridButtonsC verticalGridC = new VerticalLayoutGridButtonsC();
    VerticalLayoutGridButtonsM verticalGridM = new VerticalLayoutGridButtonsM();



    public HorizontalLayoutTopGrids(){
        verticalGridC.setMargin(true);
        verticalGridC.setSpacing(true);
        verticalGridM.setMargin(true);
        verticalGridM.setSpacing(true);

        addComponent(verticalGridC);
        addComponent(verticalGridM);

    }
}

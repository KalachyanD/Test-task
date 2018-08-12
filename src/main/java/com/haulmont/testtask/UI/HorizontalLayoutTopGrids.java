package com.haulmont.testtask.UI;

import com.haulmont.testtask.Grids.VerticalLayoutGridButtonsC;
import com.haulmont.testtask.Grids.VerticalLayoutGridButtonsM;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by User on 04.08.2017.
 */
public class HorizontalLayoutTopGrids extends HorizontalLayout {

    public VerticalLayoutGridButtonsC verticalGridC = new VerticalLayoutGridButtonsC();
    public VerticalLayoutGridButtonsM verticalGridM = new VerticalLayoutGridButtonsM();

    public HorizontalLayoutTopGrids(){
        verticalGridC.setMargin(true);
        verticalGridC.setSpacing(true);
        verticalGridM.setMargin(true);
        verticalGridM.setSpacing(true);

        addComponents(verticalGridC,verticalGridM);
    }
}

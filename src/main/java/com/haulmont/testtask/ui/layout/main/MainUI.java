package com.haulmont.testtask.ui.layout.main;

import com.haulmont.testtask.ui.layout.main.VerticalLayoutUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;


@Title("Car Service")
@Theme("valo")

public class MainUI extends UI {

    public VerticalLayoutUI design = new VerticalLayoutUI();

    @Override
    protected void init(VaadinRequest request) {
        design.setMargin(true);
        design.setSpacing(true);

        setContent(design);
    }
}
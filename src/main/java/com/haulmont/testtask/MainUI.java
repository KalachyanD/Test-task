package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;


@Theme("valo")

@Title("Vaadin application")
public class MainUI extends UI {



    @Override
    protected void init(VaadinRequest request) {



        VerticalLayoutUI design = new VerticalLayoutUI();
        design.setMargin(true);
        design.setSpacing(true);
        setContent(design);



        // Create firstField sub-window and set the content
       // WindowSuccessful subWindow = new WindowSuccessful();

        // Open it in the UI
       // addWindow(subWindow);

        //VerticalLayout layout = new VerticalLayout()
        // layout.setSizeFull();
        //layout.setMargin(true);

        //layout.addComponent(new Label("Main UI"));

        // setContent(layout);

    }
}

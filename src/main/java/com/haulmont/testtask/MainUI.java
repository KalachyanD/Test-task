package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;


@Theme("valo")

@Title("Vaadin application")
public class MainUI extends UI {

    //@WebServlet(Value = "/*", asyncSupported = true)
    //@VaadinServletConfiguration(productionMode = false, ui = MainDesignExt.class)
    // public static class Servlet extends Vaadinservlet {

    //}

    @Override
    protected void init(VaadinRequest request) {

        MainDesignExt design = new MainDesignExt();

        setContent(design);



        // Create a sub-window and set the content
       // WindowAddClient subWindow = new WindowAddClient();

        // Open it in the UI
       // addWindow(subWindow);

        //VerticalLayout layout = new VerticalLayout()
        // layout.setSizeFull();
        //layout.setMargin(true);

        //layout.addComponent(new Label("Main UI"));

        // setContent(layout);

    }
}

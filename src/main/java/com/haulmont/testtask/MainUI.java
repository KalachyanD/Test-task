package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;


@Theme("valo")

@Title("Vaadin application")
public class MainUI extends UI {

    //@WebServlet(Value = "/*", asyncSupported = true)
    //@VaadinServletConfiguration(productionMode = false, ui = DesignExt.class)
   // public static class Servlet extends Vaadinservlet {

    //}

    @Override
    protected void init(VaadinRequest request) {

        DesignExt design = new DesignExt();

        setContent(design);

        //VerticalLayout layout = new VerticalLayout();
       // layout.setSizeFull();
        //layout.setMargin(true);

        //layout.addComponent(new Label("Main UI"));

       // setContent(layout);

    }
}

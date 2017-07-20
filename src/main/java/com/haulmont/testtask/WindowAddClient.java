package com.haulmont.testtask;

import com.vaadin.ui.*;

/**
 * Created by User on 21.07.2017.
 */
class WindowAddClient extends Window {

    public WindowAddClient() {

        super("Add Client"); // Set window caption
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout ();
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);
        verticalFields.addComponent(new TextField("Name"));
        verticalFields.addComponent(new TextField("Surname"));
        verticalFields.addComponent(new TextField("Patronymic"));
        verticalFields.addComponent(new TextField("Telephone"));

        HorizontalLayout horizontButtons = new HorizontalLayout();
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);
        horizontButtons.addComponent(new Button("ОК"));
        horizontButtons.addComponent(new Button("Отмена",event -> close()));

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        verticalMain.addComponent(verticalFields);
        verticalMain.addComponent(horizontButtons);

        setContent(verticalMain);


    }
}

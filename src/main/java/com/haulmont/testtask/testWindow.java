package  com.haulmont.testtask;

import com.vaadin.ui.*;
import dao.DAO;
import models.Client;

import java.sql.SQLException;

class TestWindow extends Window {

    TextArea text;

    public TestWindow(TextArea text) {

        super("Test"); // Set window caption
        this.text = text;


        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalMain = new VerticalLayout ();
        verticalMain.addComponent(text);

        setContent(verticalMain);
    }

}

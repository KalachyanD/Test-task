package  com.haulmont.testtask;

import com.vaadin.ui.*;




class WindowSuccessful extends Window {



    public WindowSuccessful() {

        super("Successful"); // Set window caption
        Label text =new Label("Successful.");
        Button button = new Button("ОК",event -> close());


        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalMain = new VerticalLayout ();
        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.addComponent(button);
        verticalMain.addComponent(text);
        verticalMain.addComponent(horizontal);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);


        setContent(verticalMain);
    }

}

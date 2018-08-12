package com.haulmont.testtask.Windows;

import com.haulmont.testtask.UI.MainUI;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;
import models.Mechanic;
import models.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.07.2017.
 */
public class WindowEditOrder extends Window  {

    private TextField fieldDescription = new TextField("Description");
    private NativeSelect selectClient = new NativeSelect("Client");
    private NativeSelect selectMechanic = new NativeSelect("Mechanic");
    private TextField fieldDateStart = new TextField("Date start");
    private TextField fieldDateFinish = new TextField("Date finish");
    private TextField fieldCost = new TextField("Cost");
    private NativeSelect selectStatus = new NativeSelect("Status");
    private Button ok = new Button("OK", this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private int orderID;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowEditOrder(int orderID) {
        super("Edit Order"); // Set window caption
        buildWindow();
        preload(orderID);
        validation();
    }

    private void buildWindow(){

        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout (fieldDescription,selectClient,selectMechanic,fieldDateStart,fieldDateFinish,
                fieldCost,selectStatus);
        verticalFields.setSpacing(false);
        verticalFields.setMargin(false);

        HorizontalLayout horizontButtons = new HorizontalLayout(ok,cancel);
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);

        VerticalLayout verticalMain = new VerticalLayout (verticalFields,horizontButtons);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        setContent(verticalMain);
    }

    private void preload(int orderID) {

        List<Order> orders = new ArrayList<>();
        try {
            orders = DAO.getInstance().LoadAllOrders();
        } catch (SQLException e) {

        }

        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }

        for(int i = 0;i < orders.size();++i){
            if(orderID == orders.get(i).getID()){
                this.orderID = i;
            }
        }



        selectClient.addItems(clients);
        selectClient.setValue(clients.get(orders.get(this.orderID).getClient().getID()-1));
        selectClient.setNullSelectionAllowed(false);

        selectMechanic.addItems(mechanics);
        selectMechanic.setValue(mechanics.get(orders.get(this.orderID).getMechanic().getID()-1));
        selectMechanic.setNullSelectionAllowed(false);

        fieldDescription.setValue(orders.get(this.orderID).getDescription());
        fieldDateStart.setValue(LocalDateTime.now().plusMinutes(2).format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm:ss")));
        fieldDateFinish.setValue(LocalDateTime.now().plusMinutes(500).format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm:ss")));

        fieldCost.setValue(Double.toString(orders.get(this.orderID).getCost()));
        selectStatus.addItems(Order.Status.Planned, Order.Status.Completed, Order.Status.Accepted);
        selectStatus.setValue(orders.get(this.orderID).getStatus());
        selectStatus.setNullSelectionAllowed(false);
    }

    private void validation(){
        //VALIDATION
        fieldDescription.addValidator(stringLengthValidator);

        fieldCost.setRequired(true);
        fieldCost.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        fieldCost.setConverter(new toDoubleConverter());
        fieldCost.addValidator(new DoubleRangeValidator("Value is negative",0.0,
                Double.MAX_VALUE));

        //What if text field is empty - integer will be null in that case, so show blank when null
        fieldCost.setNullRepresentation("");

        fieldDescription.setValidationVisible(true);
        fieldCost.setValidationVisible(true);

        fieldDescription.setImmediate(true);
        fieldCost.setImmediate(true);

        fieldDescription.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        fieldCost.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        fieldCost.addTextChangeListener(new FieldEvents.TextChangeListener() {
            //private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {

                    fieldCost.setValue(event.getText());
                    fieldCost.setCursorPosition(event.getCursorPosition());
                    fieldCost.validate();
                    fieldDescription.validate();
                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }

            }
        });

        fieldDescription.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                try {
                    fieldDescription.setValue(event.getText());
                    fieldDescription.setCursorPosition(event.getCursorPosition());
                    fieldDescription.validate();
                    fieldCost.validate();
                    ok.setEnabled(true);
                } catch (Validator.InvalidValueException e) {
                    ok.setEnabled(false);
                }
            }
        });
    }

    private void ok(Button.ClickEvent event) {DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime dateStart =  LocalDateTime.parse(fieldDateStart.getValue(), formatter);
        LocalDateTime dateFinish = LocalDateTime.parse(fieldDateFinish.getValue(), formatter);
        try {
            Order.Status status = Order.Status.valueOf(selectStatus.getValue().toString());
            DAO.getInstance().updateOrder(orderID+1, fieldDescription.getValue(),((Client)selectClient.getValue()).getID(),
                    ((Mechanic)selectMechanic.getValue()).getID(),
                    dateStart, dateFinish, Double.parseDouble(fieldCost.getValue()), status);
            getUI().design.horizontalLayoutGridButtonsOrd.UpdateGrid();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }}

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }

}
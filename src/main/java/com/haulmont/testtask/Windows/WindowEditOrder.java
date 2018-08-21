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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 21.07.2017.
 */
public class WindowEditOrder extends Window  {

    private TextField description = new TextField("Description");
    private NativeSelect selectClient = new NativeSelect("Client");
    private NativeSelect selectMechanic = new NativeSelect("Mechanic");
    private DateField dateStart = new DateField("Date start");
    private DateField dateFinish = new DateField("Date finish");
    private TextField cost = new TextField("Cost");
    private NativeSelect selectStatus = new NativeSelect("Status");
    private Button ok = new Button("OK", this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private long orderID;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowEditOrder(long orderID) {
        super("Edit Order"); // Set window caption
        buildWindow();
        preload(orderID);
        validation();
    }

    private void buildWindow(){

        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        description.setMaxLength(100);
        cost.setMaxLength(19);

        VerticalLayout verticalFields = new VerticalLayout (description,selectClient,selectMechanic, dateStart, dateFinish,
                cost,selectStatus);
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

    private void preload(long orderID) {

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
        selectClient.setValue(clients.get((int) orders.get((int)this.orderID).getClient().getID()-1));
        selectClient.setNullSelectionAllowed(false);

        selectMechanic.addItems(mechanics);
        selectMechanic.setValue(mechanics.get((int) orders.get((int) this.orderID).getMechanic().getID()-1));
        selectMechanic.setNullSelectionAllowed(false);

        description.setValue(orders.get((int) this.orderID).getDescription());

        dateStart.setValue(new Date());
        dateFinish.setValue(new Date());

        dateFinish.validate();
        dateStart.validate();

        dateFinish.setValidationVisible(true);
        dateStart.setValidationVisible(true);

        cost.setValue(Double.toString(orders.get((int) this.orderID).getCost()));
        selectStatus.addItems(Order.Status.Planned, Order.Status.Completed, Order.Status.Accepted);
        selectStatus.setValue(orders.get((int) this.orderID).getStatus());
        selectStatus.setNullSelectionAllowed(false);
    }

    private void validation(){
        //VALIDATION
        description.addValidator(stringLengthValidator);

        cost.setRequired(true);
        cost.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        cost.setConverter(new toDoubleConverter());
        cost.addValidator(new DoubleRangeValidator("Value is negative",0.0,
                Double.MAX_VALUE));

        //What if text field is empty - integer will be null in that case, so show blank when null
        cost.setNullRepresentation("");

        description.setValidationVisible(true);
        cost.setValidationVisible(true);

        description.setImmediate(true);
        cost.setImmediate(true);

        description.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        cost.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);

        cost.addTextChangeListener(event -> textChange(event, cost));
        description.addTextChangeListener(event -> textChange(event, description));
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField){
        try {
            textField.setValue(event.getText());

            textField.setCursorPosition(event.getCursorPosition());

            cost.validate();
            description.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event) {DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDate dateStart = this.dateStart.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFinish = this.dateFinish.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            Order.Status status = Order.Status.valueOf(selectStatus.getValue().toString());
            DAO.getInstance().updateOrder(orderID+1, description.getValue(),((Client)selectClient.getValue()).getID(),
                    ((Mechanic)selectMechanic.getValue()).getID(),
                    dateStart, dateFinish, Double.parseDouble(cost.getValue()), status);
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
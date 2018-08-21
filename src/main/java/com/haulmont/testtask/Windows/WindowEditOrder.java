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
    private long id;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowEditOrder(long id) {
        super("Edit Order"); // Set window caption
        buildWindow();
        preload(id);
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

    private void preload(long id) {
        this.id =id;
        try {
            Order order = DAO.getInstance().loadOrder(id);

            List<Client> clients = new ArrayList<>();
            clients = DAO.getInstance().LoadAllClients();

            List<Mechanic> mechanics = new ArrayList<>();
            mechanics = DAO.getInstance().LoadAllMechanics();

            selectClient.addItems(clients);
            selectClient.setValue(clients.get((int) order.getClient().getID()-1));
            selectClient.setNullSelectionAllowed(false);

            selectMechanic.addItems(mechanics);
            selectMechanic.setValue(mechanics.get((int) order.getMechanic().getID()-1));
            selectMechanic.setNullSelectionAllowed(false);

            description.setValue(order.getDescription());

            cost.setValue(Double.toString(order.getCost()));

            selectStatus.addItems(Order.Status.Planned, Order.Status.Completed, Order.Status.Accepted);
            selectStatus.setValue(order.getStatus());
            selectStatus.setNullSelectionAllowed(false);

            LocalDate localDateStart = order.getStartDate();
            LocalDate localDateEnd = order.getEndDate();

            dateStart.setValue(Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dateFinish.setValue(Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        }catch (SQLException e){
            e.printStackTrace();
        }

        dateFinish.validate();
        dateStart.validate();

        dateFinish.setValidationVisible(true);
        dateStart.setValidationVisible(true);
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

    private void ok(Button.ClickEvent event) {
        LocalDate dateStart = this.dateStart.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFinish = this.dateFinish.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            Order.Status status = Order.Status.valueOf(selectStatus.getValue().toString());
            DAO.getInstance().updateOrder(id, description.getValue(),((Client)selectClient.getValue()).getID(),
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
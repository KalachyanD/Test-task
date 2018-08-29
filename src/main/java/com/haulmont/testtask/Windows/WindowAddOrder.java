package com.haulmont.testtask.Windows;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.DAO;
import models.Client;
import models.Mechanic;
import models.Order;
import com.haulmont.testtask.UI.*;

/**
 * Created by User on 21.07.2017.
 */

public class WindowAddOrder extends Window {

    private TextField description = new TextField("Description");
    private NativeSelect selectClient = new NativeSelect("Client");
    private NativeSelect selectMechanic = new NativeSelect("Mechanic");
    private Date start = new Date();
    private DateField dateStart = new DateField("Date start",start);
    private DateField dateFinish = new DateField("Date finish",new Date(start.getYear(),start.getMonth(),start.getDate()+7));
    private TextField cost = new TextField("Cost");
    private NativeSelect selectStatus = new NativeSelect("Status");
    private Button ok = new Button("OK", this::ok);
    private Button cancel = new Button("Cancel", event -> close());

    public WindowAddOrder() {
        super("Add Order"); // Set window caption
        preload();
        buildWindow();
        validation();
    }

    private void preload() {
        try {
            List<Client> clients = new ArrayList<>();
            clients = DAO.getInstance().LoadAllClients();

            List<Mechanic> mechanics = new ArrayList<>();
            mechanics = DAO.getInstance().LoadAllMechanics();

            selectClient.addItems(clients);
            selectClient.setValue(clients.get(0));
            selectClient.setNullSelectionAllowed(false);

            selectMechanic.addItems(mechanics);
            selectMechanic.setValue(mechanics.get(0));
            selectMechanic.setNullSelectionAllowed(false);

            dateFinish.validate();
            dateStart.validate();

            dateFinish.setValidationVisible(true);
            dateStart.setValidationVisible(true);

            selectStatus.addItems(Order.Status.Planned, Order.Status.Completed, Order.Status.Accepted);
            selectStatus.setValue(Order.Status.Planned);
            selectStatus.setNullSelectionAllowed(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void buildWindow() {
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        description.setMaxLength(100);
        cost.setMaxLength(19);
        ok.setEnabled(false);

        VerticalLayout verticalFields = new VerticalLayout(description, selectClient, selectMechanic,
                dateStart, dateFinish, cost, selectStatus);
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        HorizontalLayout horizonButtons = new HorizontalLayout(ok, cancel);
        horizonButtons.setSpacing(false);
        horizonButtons.setMargin(false);

        VerticalLayout verticalMain = new VerticalLayout(verticalFields, horizonButtons);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        setContent(verticalMain);
    }

    private void validation() {
        //VALIDATION
        description.addValidator(new StringLengthValidator("Prompt is empty.",
                1, 50, false));
        cost.setRequired(true);
        cost.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        cost.setConverter(new toDoubleConverter());
        cost.addValidator(new DoubleRangeValidator("Value is negative", 0.0, Double.MAX_VALUE));

        dateStart.addValueChangeListener(event -> dateChange());
        dateFinish.addValueChangeListener(event -> dateChange());

        dateStart.addValidator(new DateRangeValidator("Wrong date",null,dateStart.getValue(),Resolution.DAY));
        dateFinish.addValidator(new DateRangeValidator("Wrong date",dateStart.getValue(),null,Resolution.DAY));

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

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField) {
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

    private void dateChange(){
        try {

            dateStart.validate();
            dateFinish.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event) {
        LocalDate dateStart = this.dateStart.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFinish = this.dateFinish.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            DAO.getInstance().storeOrder(description.getValue(), ((Client) selectClient.getValue()).getID(),
                    ((Mechanic) selectMechanic.getValue()).getID(),
                    dateStart, dateFinish, Double.parseDouble(cost.getValue()),
                    Order.Status.valueOf(selectStatus.getValue().toString()));
            getUI().design.horizontalLayoutGridButtonsOrd.updateGrid();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }
}
package com.haulmont.testtask.Windows;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import dao.DAO;
import models.Client;
import models.Mechanic;
import models.Order;
import com.haulmont.testtask.UI.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.07.2017.
 */

public class WindowAddOrder extends Window  {

    private TextField fieldDescription = new TextField("Description");
    private NativeSelect selectClient = new NativeSelect("Client");
    private NativeSelect selectMechanic = new NativeSelect("Mechanic");
    private TextField fieldDateStart = new TextField("Date start");
    private TextField fieldDateFinish = new TextField("Date finish");
    private TextField fieldCost = new TextField("Cost");
    private NativeSelect selectStatus = new NativeSelect("Status");
    private Button ok = new Button("OK", this::ok);
    private Button cancel = new Button("Cancel",event -> close());
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowAddOrder() {
        super("Add Order"); // Set window caption
        preload();
        buildWindow();
        validation();
    }

    private void preload() {

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

        selectClient.addItems(clients);
        selectClient.setValue(clients.get(0));
        selectClient.setNullSelectionAllowed(false);

        selectMechanic.addItems(mechanics);
        selectMechanic.setValue(mechanics.get(0));
        selectMechanic.setNullSelectionAllowed(false);

        fieldDateStart.setValue(LocalDateTime.now().plusMinutes(2).format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm:ss")));
        fieldDateFinish.setValue(LocalDateTime.now().plusMinutes(500).format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm:ss")));

        selectStatus.addItems(Order.Status.Planned, Order.Status.Completed, Order.Status.Accepted);
        selectStatus.setValue(Order.Status.Planned);
        selectStatus.setNullSelectionAllowed(false);
    }

    private void buildWindow(){
        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        VerticalLayout verticalFields = new VerticalLayout (fieldDescription,selectClient,selectMechanic,
                fieldDateStart,fieldDateFinish,fieldCost,selectStatus);
        verticalFields.setSpacing(true);
        verticalFields.setMargin(true);

        HorizontalLayout horizonButtons = new HorizontalLayout(ok, cancel);
        horizonButtons.setSpacing(false);
        horizonButtons.setMargin(false);

        VerticalLayout verticalMain = new VerticalLayout (verticalFields,horizonButtons);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        setContent(verticalMain);
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

        fieldCost.addTextChangeListener(event -> textChange(event, fieldCost));
        fieldDescription.addTextChangeListener(event -> textChange(event, fieldDescription));
    }

    private void textChange(FieldEvents.TextChangeEvent event, TextField textField){
        try {
            textField.setValue(event.getText());

            textField.setCursorPosition(event.getCursorPosition());

            fieldCost.validate();
            fieldDescription.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(fieldDateStart.getValue(), formatter);
        LocalDateTime dateFinish = LocalDateTime.parse(fieldDateFinish.getValue(), formatter);
        try {
            Order.Status status = Order.Status.valueOf(selectStatus.getValue().toString());
            DAO.getInstance().storeOrder(fieldDescription.getValue(),((Client)selectClient.getValue()).getID(),
                    ((Mechanic)selectMechanic.getValue()).getID(), dateStart, dateFinish,
                    Double.parseDouble(fieldCost.getValue()), status);
            getUI().design.horizontalLayoutGridButtonsOrd.UpdateGrid();
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

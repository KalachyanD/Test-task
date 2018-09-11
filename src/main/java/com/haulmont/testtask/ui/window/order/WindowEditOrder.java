package com.haulmont.testtask.ui.window.order;

import com.haulmont.testtask.dao.dto.FullNameDTO;
import com.haulmont.testtask.ui.converter.StringToDoubleConverter;
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

import com.haulmont.testtask.ui.layout.main.MainUI;
import com.haulmont.testtask.dao.ClientDAO;
import com.haulmont.testtask.dao.MechanicDAO;
import com.haulmont.testtask.dao.OrderDAO;
import com.haulmont.testtask.dao.dto.OrderDTO;
import com.haulmont.testtask.model.Status;

/**
 * Created by User on 21.07.2017.
 */

public class WindowEditOrder extends Window {

    private TextField description = new TextField("Description");
    private NativeSelect selectClient = new NativeSelect("client");
    private NativeSelect selectMechanic = new NativeSelect("Mechanic");
    private DateField dateStart = new DateField("Date start");
    private DateField dateFinish = new DateField("Date finish");
    private TextField cost = new TextField("Cost");
    private NativeSelect selectStatus = new NativeSelect("Status");
    private Button ok = new Button("OK", this::ok);
    private Button cancel = new Button("Cancel", event -> close());
    private Long id;
    private StringLengthValidator stringLengthValidator = new StringLengthValidator("Prompt is empty.",
            1, 50, false);

    public WindowEditOrder(Long id) {
        super("Edit Order"); // Set window caption
        buildWindow();
        preload(id);
        validation();
    }

    private void buildWindow() {

        center(); //Position of window
        setClosable(true); // Disable the close button
        setModal(true); // Enable modal window mode

        description.setMaxLength(100);
        cost.setMaxLength(19);

        VerticalLayout verticalFields = new VerticalLayout(description, selectClient, selectMechanic, dateStart, dateFinish,
                cost, selectStatus);
        verticalFields.setSpacing(false);
        verticalFields.setMargin(false);

        HorizontalLayout horizontButtons = new HorizontalLayout(ok, cancel);
        horizontButtons.setSpacing(true);
        horizontButtons.setMargin(true);

        VerticalLayout verticalMain = new VerticalLayout(verticalFields, horizontButtons);
        verticalMain.setSpacing(true);
        verticalMain.setMargin(true);

        setContent(verticalMain);
    }

    private void preload(Long id) {
        this.id = id;
        try {
            OrderDTO order = OrderDAO.getInstance().load(id);

            List<FullNameDTO> clients = new ArrayList<>();
            clients = ClientDAO.getInstance().LoadAllFullName();

            List<FullNameDTO> mechanics = new ArrayList<>();
            mechanics = MechanicDAO.getInstance().LoadAllFullName();

            selectClient.addItems(clients);
            selectClient.setValue(clients.get(order.getClientDTO().getId().intValue()-1));
            selectClient.setNullSelectionAllowed(false);

            selectMechanic.addItems(mechanics);
            selectMechanic.setValue(mechanics.get(order.getMechanicDTO().getId().intValue()-1));
            selectMechanic.setNullSelectionAllowed(false);

            description.setValue(order.getDescription());

            cost.setValue(Double.toString(order.getCost()));

            selectStatus.addItems(Status.Planned, Status.Completed, Status.Accepted);
            selectStatus.setValue(order.getStatus());
            selectStatus.setNullSelectionAllowed(false);

            LocalDate localDateStart = order.getStartDate();
            LocalDate localDateEnd = order.getEndDate();

            dateStart.setValue(Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dateFinish.setValue(Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } catch (SQLException e) {
            Notification.show("System error", "Database error",
                    Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }

        dateFinish.validate();
        dateStart.validate();

        dateFinish.setValidationVisible(true);
        dateStart.setValidationVisible(true);
    }

    private void validation() {
        //VALIDATION
        description.addValidator(stringLengthValidator);

        cost.setRequired(true);
        cost.setRequiredError("Prompt is empty.");

        //To convert string value to integer before validation
        cost.setConverter(new StringToDoubleConverter());
        cost.addValidator(new DoubleRangeValidator("Value is negative", 0.0,
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

        dateStart.addValueChangeListener(event -> dateChange());
        dateFinish.addValueChangeListener(event -> dateChange());

        dateStart.addValidator(new DateRangeValidator("Wrong date", null, dateStart.getValue(), Resolution.DAY));
        dateFinish.addValidator(new DateRangeValidator("Wrong date", dateStart.getValue(), null, Resolution.DAY));
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

    private void dateChange() {
        try {
            dateStart.validate();
            dateFinish.validate();

            ok.setEnabled(true);
        } catch (Validator.InvalidValueException e) {
            ok.setEnabled(false);
        }
    }

    private void ok(Button.ClickEvent event) {
        try {
            OrderDAO.getInstance().update(id, description.getValue(), ((FullNameDTO) selectClient.getValue()).getId(),
                    ((FullNameDTO) selectMechanic.getValue()).getId(),
                    this.dateStart.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    this.dateFinish.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    Double.parseDouble(cost.getValue()),
                    Status.valueOf(selectStatus.getValue().toString()));
            getUI().design.horizontalLayoutGridButtonsOrd.updateGrid();
            getUI().design.horizontalLayoutGridButtonsOrd.buttonDeleteOrder.setEnabled(false);
            getUI().design.horizontalLayoutGridButtonsOrd.buttonEditOrder.setEnabled(false);
            close();
        } catch (SQLException e) {
            Notification.show("System error", "Database error",
                    Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }

}
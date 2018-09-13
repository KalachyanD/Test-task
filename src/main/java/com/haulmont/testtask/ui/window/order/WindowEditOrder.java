package com.haulmont.testtask.ui.window.order;

import com.vaadin.ui.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haulmont.testtask.dao.ClientDAO;
import com.haulmont.testtask.dao.MechanicDAO;
import com.haulmont.testtask.dao.OrderDAO;
import com.haulmont.testtask.dao.dto.OrderDTO;
import com.haulmont.testtask.model.Status;
import com.haulmont.testtask.dao.dto.FullNameDTO;

public class WindowEditOrder extends AbstractWindowOrder {

    private Long id;

    public WindowEditOrder(Long id) {
        super("Edit Order"); // Set window caption
        setOk(new Button("OK", this::ok));
        setDateStart(new DateField("Date start"));
        setDateFinish(new DateField("Date finish"));
        buildWindow();
        preload(id);
        validation();
    }

    private void preload(Long id) {
        this.id = id;
        try {
            OrderDTO order = OrderDAO.getInstance().get(id);

            List<FullNameDTO> clients = new ArrayList<>();
            clients = ClientDAO.getInstance().getAllFullName();

            List<FullNameDTO> mechanics = new ArrayList<>();
            mechanics = MechanicDAO.getInstance().getAllFullName();

            getSelectClient().addItems(clients);
            getSelectClient().setValue(clients.get(order.getClientDTO().getId().intValue()-1));
            getSelectClient().setNullSelectionAllowed(false);

            getSelectMechanic().addItems(mechanics);
            getSelectMechanic().setValue(mechanics.get(order.getMechanicDTO().getId().intValue()-1));
            getSelectMechanic().setNullSelectionAllowed(false);

            getDescript().setValue(order.getDescription());

            getCost().setValue(Double.toString(order.getCost()));

            getSelectStatus().addItems(Status.Planned, Status.Completed, Status.Accepted);
            getSelectStatus().setValue(order.getStatus());
            getSelectStatus().setNullSelectionAllowed(false);

            LocalDate localDateStart = order.getStartDate();
            LocalDate localDateEnd = order.getEndDate();

            getDateStart().setValue(Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            getDateFinish().setValue(Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } catch (SQLException e) {
            Notification.show("System error", "Database error",
                    Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }

        getDateStart().validate();
        getDateStart().setValidationVisible(true);

        getDateFinish().validate();
        getDateFinish().setValidationVisible(true);

    }

    private void ok(Button.ClickEvent event) {
        try {
            OrderDAO.getInstance().edit(id, getDescript().getValue(),
                    ((FullNameDTO) getSelectStatus().getValue()).getId(),
                    ((FullNameDTO) getSelectMechanic().getValue()).getId(),
                    getDateStart().getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    getDateFinish().getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    Double.parseDouble(getCost().getValue()),
                    Status.valueOf(getSelectStatus().getValue().toString()));
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
}
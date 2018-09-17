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
import com.haulmont.testtask.model.Status;
import com.haulmont.testtask.dao.dto.FullNameDTO;

public class WindowAddOrder extends AbstractWindowOrder {

    public WindowAddOrder() {
        super("Add Order"); // Set window caption
        setOk(new Button("OK", this::ok));
        Date start = new Date();
        setDateStart(new DateField("Date start",start));
        setDateFinish(new DateField("Date finish",new Date(start.getYear(),start.getMonth(),
                start.getDate()+7)));
        buildWindow();
        preload();
        validation();
    }

    @Override
    protected void preload() {
        try {
            List<FullNameDTO> clients = new ArrayList<>();
            clients = ClientDAO.getInstance().getAllFullName();

            List<FullNameDTO> mechanics = new ArrayList<>();
            mechanics = MechanicDAO.getInstance().getAllFullName();

            getSelectClient().addItems(clients);
            getSelectClient().setValue(clients.get(0));
            getSelectClient().setNullSelectionAllowed(false);

            getSelectMechanic().addItems(mechanics);
            getSelectMechanic().setValue(mechanics.get(0));
            getSelectMechanic().setNullSelectionAllowed(false);

            getDateStart().validate();
            getDateStart().setValidationVisible(true);

            getDateFinish().validate();
            getDateFinish().setValidationVisible(true);

            getSelectStatus().addItems(Status.Planned, Status.Completed, Status.Accepted);
            getSelectStatus().setValue(Status.Planned);
            getSelectStatus().setNullSelectionAllowed(false);
        } catch (SQLException e) {
            Notification.show("System error", "Database error",
                    Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    private void ok(Button.ClickEvent event) {
        LocalDate dateStart = this.getDateStart().getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFinish = this.getDateFinish().getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            OrderDAO.getInstance().create(
                    getDescript().getValue(),
                    ((FullNameDTO) getSelectClient().getValue()).getId(),
                    ((FullNameDTO) getSelectMechanic().getValue()).getId(),
                    dateStart, dateFinish, Double.parseDouble(getCost().getValue()),
                    Status.valueOf(getSelectStatus().getValue().toString()));

            getUI().design.horizontalLayoutGridButtonsOrd.updateGrid();
            close();
        } catch (SQLException e) {
            Notification.show("System error", "Database error",
                    Notification.Type.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }
}
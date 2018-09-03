package com.haulmont.testtask.grids;

import com.haulmont.testtask.windows.client.WindowAddClient;
import com.haulmont.testtask.windows.client.WindowEditClient;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.haulmont.testtask.ui.MainUI;
import dao.DAO;
import models.Client;

public class VerticalLayoutGridButtonsC extends VerticalLayout {

    public Grid gridClients = new Grid("Clients");
    public Button buttonDeleteClient = new Button("Delete", this::deleteClient);
    public Button buttonEditClient = new Button("Edit", this::editClient);
    private Button buttonAddClient = new Button("Add", this::addClient);
    private Client client;

    public VerticalLayoutGridButtonsC() {
        buildLayout();
    }

    public void updateGrid() {
        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Have firstField containerGridClients of some type to contain the data
        BeanItemContainer<Client> containerGridClients = new BeanItemContainer<>(Client.class, clients);

        // Create firstField gridClients bound to the containerGridClients
        gridClients.setContainerDataSource(containerGridClients);
        gridClients.setColumnOrder("name", "surname", "patronymic", "phoneNumber");
    }

    private void buildLayout() {
        gridClients.setHeight("300");
        gridClients.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridClients.addSelectionListener(event -> selection());
        addComponents(gridClients, buttonDeleteClient, buttonEditClient, buttonAddClient);
        updateGrid();
        gridClients.removeColumn("ID");
        buttonDeleteClient.setEnabled(false);
        buttonEditClient.setEnabled(false);
    }

    private void addClient(Button.ClickEvent event) {
        WindowAddClient window = new WindowAddClient();
        UI.getCurrent().addWindow(window);
    }

    private void deleteClient(Button.ClickEvent event) {
        if (gridClients.getSelectedRow() == null) {
            buttonDeleteClient.setEnabled(false);
            buttonEditClient.setEnabled(false);
        } else {
            try {
                DAO.getInstance().deleteClient(client.getID());
                updateGrid();
                buttonDeleteClient.setEnabled(false);
                buttonEditClient.setEnabled(false);
            } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                Notification.show("Deleting is impossible", "This client locate in Order Table.",
                        Notification.Type.WARNING_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editClient(Button.ClickEvent event) {
        if (gridClients.getSelectedRow() == null) {
            buttonDeleteClient.setEnabled(false);
            buttonEditClient.setEnabled(false);
        } else {
            WindowEditClient window = new WindowEditClient(client.getID());
            UI.getCurrent().addWindow(window);
        }
    }

    private void selection() {
        client = (Client) gridClients.getSelectedRow();
        buttonDeleteClient.setEnabled(true);
        buttonEditClient.setEnabled(true);
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }
}
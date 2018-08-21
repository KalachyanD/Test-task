package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import dao.DAO;
import models.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.08.2017.
 */
public class VerticalLayoutGridButtonsC extends VerticalLayout {

    private Grid gridClients = new Grid("Clients");
    private Button buttonDeleteClient = new Button("Delete",this::deleteClient);
    private Button buttonEditClient = new Button("Edit",this::editClient);
    private Button buttonAddClient = new Button("Add",this::addClient);
    private Client client;
    private boolean enable = false;

    public VerticalLayoutGridButtonsC(){
        buildLayout();
    }

    public void UpdateGrid() {
        List<Client> clients = new ArrayList<>();
        try {
            clients = DAO.getInstance().LoadAllClients();
        } catch (SQLException e) {

        }

        // Have firstField containerGridClients of some type to contain the data
        BeanItemContainer<Client> containerGridClients = new BeanItemContainer<>(Client.class, clients);

        // Create firstField gridClients bound to the containerGridClients
        gridClients.setContainerDataSource(containerGridClients);
        gridClients.removeColumn("ID");
        gridClients.setColumnOrder("name","surname","patronymic","phoneNumber");
    }

    private void buildLayout(){
        gridClients.setHeight("300");
        gridClients.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridClients.addSelectionListener(event -> selectionOrder());

        addComponents(gridClients,buttonDeleteClient,buttonEditClient,buttonAddClient);
        UpdateGrid();
    }

    private void addClient(Button.ClickEvent event){
        WindowAddClient window = new WindowAddClient();
        UI.getCurrent().addWindow(window);
    }

    private void deleteClient(Button.ClickEvent event){
        if(enable == true) {
            try {
                DAO.getInstance().deleteClient(client.getID());
                UpdateGrid();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editClient(Button.ClickEvent event){
        if(enable == true) {
            WindowEditClient window = new WindowEditClient(client.getID());
            UI.getCurrent().addWindow(window);
        }
    }

    private void selectionOrder(){
        client =(Client)gridClients.getSelectedRow();
        enable = true;
    }
}
package com.haulmont.testtask.Grids;

import com.haulmont.testtask.Windows.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import dao.DAO;
import models.Mechanic;
import models.Order;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.08.2017.
 */
public class VerticalLayoutGridButtonsM extends VerticalLayout {

    private Grid gridMechanics = new Grid("Mechanics");
    private Button buttonDeleteMechanic = new Button("Delete",this::deleteMechanic);
    private Button buttonEditMechanic = new Button("Edit",this::editMechanic);
    private Button buttonAddMechanic = new Button("Add",this::addMechanic);
    private Button buttonStatistic = new Button("Statistic",this::statisticsMechanic);
    private Mechanic mechanic;
    private Label label = new Label();
    private boolean enable = false;

    public VerticalLayoutGridButtonsM(){
        buildLayout();
    }

    private void buildLayout(){
        gridMechanics.setHeight("300");
        gridMechanics.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridMechanics.addSelectionListener(event -> selectionOrder());
        addComponents(gridMechanics,buttonDeleteMechanic,buttonEditMechanic,buttonAddMechanic, buttonStatistic,label);
        UpdateGrid();
        gridMechanics.removeColumn("ID");
    }

    public void UpdateGrid() {
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            mechanics = DAO.getInstance().LoadAllMechanics();
        } catch (SQLException e) {

        }

        BeanItemContainer<Mechanic> containerGridMechanics = new BeanItemContainer<>(Mechanic.class, mechanics);
        gridMechanics.setContainerDataSource(containerGridMechanics);
        gridMechanics.setColumnOrder("name","surname","patronymic","hourlyPay");
    }

    private void addMechanic(Button.ClickEvent event){
        WindowAddMechanic window = new WindowAddMechanic();
        UI.getCurrent().addWindow(window);
    }

    private void deleteMechanic(Button.ClickEvent event){
        if(enable == true) {
            try {
                DAO.getInstance().deleteMechanic(mechanic.getID());
                UpdateGrid();
            }catch (java.sql.SQLIntegrityConstraintViolationException e) {
                Notification.show("Deleting is impossible", "This mechanic locate in Order Table.",
                        Notification.Type.WARNING_MESSAGE);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editMechanic(Button.ClickEvent event){
        if(enable == true) {
            WindowEditMechanic window = new WindowEditMechanic(mechanic.getID());
            UI.getCurrent().addWindow(window);
        }
    }

    private void statisticsMechanic(Button.ClickEvent event){
        if(enable == true) {
             class WindowStatisticsMechanic extends Window {

                private Label label;
                private int count = 0;

                public WindowStatisticsMechanic(Mechanic mechanic){
                    super(mechanic.getName());
                    preload(mechanic);
                    buildLayout();
                }

                private void preload(Mechanic mechanic){
                    List<Order> orders = new ArrayList<>();
                    try {
                        orders = DAO.getInstance().LoadAllOrders();
                    } catch (SQLException e) {

                    }

                    for(int i = 0;i < orders.size();++i){
                        if(mechanic.getID() == orders.get(i).getMechanic().getID()){
                            ++count;
                        }
                    }
                }

                private void buildLayout(){
                    center(); //Position of window
                    setClosable(true); // Disable the close button
                    setModal(true); // Enable modal window mode

                    label = new Label("Orders: "+count+".");

                    VerticalLayout verticalMain = new VerticalLayout (label);
                    verticalMain.setSpacing(false);
                    verticalMain.setMargin(false);

                    setContent(verticalMain);
                }
            }

            WindowStatisticsMechanic window = new WindowStatisticsMechanic(mechanic);
            UI.getCurrent().addWindow(window);
        }
    }

    private void selectionOrder(){
        mechanic =(Mechanic)gridMechanics.getSelectedRow();
        enable = true;
    }
}
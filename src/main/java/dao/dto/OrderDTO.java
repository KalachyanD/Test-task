package dao.dto;


import models.Status;

import java.time.LocalDate;

public class OrderDTO{
    private Long id;
    private String description;
    private ClientMechanicDTO client;
    private ClientMechanicDTO mechanic;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double cost;
    private Status status;

    public OrderDTO(long id, String description, ClientMechanicDTO client, ClientMechanicDTO mechanic, LocalDate startDate,
                 LocalDate endDate, double cost, Status status) {
        this.id = id;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.status = status;
    }

    public OrderDTO(OrderDTO order) {
        this(order.getID(), order.getDescription(), order.getClientDTO(), order.getMechanicDTO(), order.getStartDate(),
                order.getEndDate(), order.getCost(), order.getStatus());
    }

    public long getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientMechanicDTO getClientDTO() {
        return client;
    }

    public void setClientMechanicDTO(ClientMechanicDTO client) {
        this.client = client;
    }

    public ClientMechanicDTO getMechanicDTO() {
        return mechanic;
    }

    public void setMechanicDTO(ClientMechanicDTO mechanic) {
        this.mechanic = mechanic;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
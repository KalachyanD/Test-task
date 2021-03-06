package com.haulmont.testtask.model;

import java.time.LocalDate;

public class Order {
    private Long id;
    private String description;
    private Client client;
    private Mechanic mechanic;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double cost;
    private Status status;

    public Order(Long id, String description, Client client, Mechanic mechanic, LocalDate startDate,
                 LocalDate endDate, Double cost, Status status) {
        this.id = id;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.status = status;
    }

    public Order(Order order) {
        this(order.getId(), order.getDescription(), order.getClient(), order.getMechanic(), order.getStartDate(),
                order.getEndDate(), order.getCost(), order.getStatus());
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
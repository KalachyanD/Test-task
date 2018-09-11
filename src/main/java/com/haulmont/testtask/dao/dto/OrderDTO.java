package com.haulmont.testtask.dao.dto;


import com.haulmont.testtask.model.Status;

import java.time.LocalDate;

public class OrderDTO{
    private Long id;
    private String description;
    private FullNameDTO client;
    private FullNameDTO mechanic;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double cost;
    private Status status;

    public OrderDTO(Long id, String description, FullNameDTO client, FullNameDTO mechanic,
                    LocalDate startDate, LocalDate endDate, Double cost, Status status) {
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
        this(order.getId(), order.getDescription(), order.getClientDTO(), order.getMechanicDTO(), order.getStartDate(),
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

    public FullNameDTO getClientDTO() {
        return client;
    }

    public void setClientMechanicDTO(FullNameDTO client) {
        this.client = client;
    }

    public FullNameDTO getMechanicDTO() {
        return mechanic;
    }

    public void setMechanicDTO(FullNameDTO mechanic) {
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
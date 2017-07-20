package models;


import java.time.LocalDateTime;


public class Order {
    public static enum Status {
        START, PROCESS, FINISH
    }
    private int id;
    private String description;
    private Client client;
    private Mechanic mechanic;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double cost;
    private Status status;

    public Order(int id, String description, Client client, Mechanic mechanic, LocalDateTime startDate, LocalDateTime endDate, double cost, Status status) {
        this.id = id;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.status = status;
    }

    public int getID() {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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

package com.haulmont.testtask.model;

public class Client {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Long phoneNumber;

    public Client(Long id, String name, String surname, String patronymic, Long telephoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = telephoneNumber;
    }

    public Client(Client client) {
        this(client.getId(), client.getName(), client.getSurname(), client.getPatronymic(), client.getPhoneNumber());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" ");
        builder.append(getSurname());
        builder.append(" ");
        builder.append(getPatronymic());
        return builder.toString();
    }
}
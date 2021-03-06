package com.haulmont.testtask.model;


public class Mechanic {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Double hourlyPay;

    public Mechanic(long id, String name, String surname, String patronymic, double hourlyPay) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.hourlyPay= hourlyPay;

    }

    public Mechanic(Mechanic mechanic) {
        this(mechanic.getId(), mechanic.getName(), mechanic.getSurname(), mechanic.getPatronymic(), mechanic.getHourlyPay());
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

    public Double getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(Double hourlyPay) {
        this.hourlyPay = hourlyPay;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( getName() );
        builder.append( " ");
        builder.append( getSurname() );
        builder.append( " ");
        builder.append( getPatronymic() );
        return builder.toString();
    }
}
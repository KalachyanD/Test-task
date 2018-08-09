package models;

import java.lang.Object;

public class Client {

    private int id;

    private String name;
    //Surname
    private String surname;
    //Patronymic
    private String patronymic;
    //Telephone number
    private int telephone;

    public Client(int id, String name, String surname, String patronymic, int telephoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.telephone = telephoneNumber;
    }

    public Client(Client client) {
        this(client.getID(), client.getName(), client.getSurname(), client.getPatronymic(), client.getTelephone());
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

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public int getID() {
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

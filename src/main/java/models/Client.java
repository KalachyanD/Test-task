package models;

public class Client {

    private int id;

    private String name;
    //Surname
    private String surname;
    //Patronymic
    private String patronymic;
    //Telephone number
    private int phoneNumber;

    public Client(int id, String name, String surname, String patronymic, int telephoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = telephoneNumber;
    }

    public Client(Client client) {
        this(client.getID(), client.getName(), client.getSurname(), client.getPatronymic(), client.getPhoneNumber());
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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
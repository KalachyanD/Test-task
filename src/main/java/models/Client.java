package models;

public class Client {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Long phoneNumber;

    public Client(long id, String name, String surname, String patronymic, long telephoneNumber) {
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

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getID() {
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
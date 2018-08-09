package models;


public class Mechanic {

    private int id;

    private String name;
    //Surname
    private String surname;
    //Patronymic
    private String patronymic;

    private int hourlyPay;

    public Mechanic(int id, String name, String surname, String patronymic, int telephoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.hourlyPay= telephoneNumber;

    }

    public Mechanic(Mechanic mechanic) {
        this(mechanic.getID(), mechanic.getName(), mechanic.getSurname(), mechanic.getPatronymic(), mechanic.getHourlyPay());
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

    public int getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(int hourlyPay) {
        this.hourlyPay = hourlyPay;
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

package com.haulmont.testtask.dao.dto;

public class FullNameDTO {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;

    public FullNameDTO(Long id, String name, String surname, String patronymic){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public FullNameDTO(FullNameDTO clientDTO){
        this(clientDTO.getId(),clientDTO.getName(),clientDTO.getSurname(),clientDTO.getPatronymic());
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getPatronymic(){
        return patronymic;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setPatronymic(String patronymic){
        this.patronymic =patronymic;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" ");
        builder.append(getSurname());
        builder.append(" ");
        builder.append(getPatronymic());
        return builder.toString();
    }
}

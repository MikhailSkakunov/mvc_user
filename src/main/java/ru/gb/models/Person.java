package ru.gb.models;

import javax.validation.constraints.*;


public class Person {
    private int id;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    private String fullName;

    @Min(value = 1900, message = "Год должен быть больше, чем 1900")
    private int yearOfBirth;

    public Person() {
    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
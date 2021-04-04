package com.example.oracleapp.entities;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;

@Parcel
public class AppSettings {
    String name;
    String surname;
    Date date;
    String gender;

    public AppSettings() {
    }

    public AppSettings(String name, String surname, Date date, String gender) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.gender = gender;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return "AppSettings{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", date='" + sdf.format(date) + '\'' +
                ", gender='" + gender + '\'' +
                '}';
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}

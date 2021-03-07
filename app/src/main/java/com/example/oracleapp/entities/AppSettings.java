package com.example.oracleapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppSettings implements Parcelable {
    private String name;
    private String surname;
    private Date date;
    private String gender;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.gender);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.surname = source.readString();
        long tmpDate = source.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.gender = source.readString();
    }

    protected AppSettings(Parcel in) {
        this.name = in.readString();
        this.surname = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.gender = in.readString();
    }

    public static final Parcelable.Creator<AppSettings> CREATOR = new Parcelable.Creator<AppSettings>() {
        @Override
        public AppSettings createFromParcel(Parcel source) {
            return new AppSettings(source);
        }

        @Override
        public AppSettings[] newArray(int size) {
            return new AppSettings[size];
        }
    };
}

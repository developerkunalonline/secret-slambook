package com.qdat.secretslambook.utility;

import java.util.ArrayList;
import java.util.List;

public class MyDate {

    private int day;
    private int month;
    private int year;

    private String date;

    String monthName[]={
            "Janauary",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };



    public MyDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return ""+day+" "+monthName[getMonth()]+" "+year;
    }

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = ""+day+" "+monthName[getMonth()]+" "+year;
    }
    public MyDate(int day, String month, int year) {
        this.day = day;
        this.year = year;

        for (int i = 0 ;i < monthName.length;i++){
            if (month.equals(monthName[i])){
                this.month = i+1;
                break;
            }
        }
        this.date = ""+day+"/ "+month+" / "+year;
    }
}

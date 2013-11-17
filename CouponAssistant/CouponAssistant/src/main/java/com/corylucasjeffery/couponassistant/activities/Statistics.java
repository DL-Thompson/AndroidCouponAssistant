package com.corylucasjeffery.couponassistant.activities;

/**
 * Created by Caleb on 11/16/13.
 */
public class Statistics {
    private String bought;
    private String total;
    private String day;
    private String week;
    private String month;
    private String year;

    private final String TAG = "STATISTICS";

    public Statistics(String bought, String total, String day, String week, String month, String year){
        this.bought = bought;
        this.total = total;
        this.day = day;
        this.week = week;
        this.month = month;
        this.year = year;




    }

    public String getBought() {return bought;}
    public String getTotal() {return total;}
    public String getDay() {return day;}
    public String getWeek() {return week;}
    public String getMonth() {return month;}
    public String getYear() {return year;}
}

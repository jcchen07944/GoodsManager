package jcchen.goodsmanager.entity;

import java.io.Serializable;

public class DateInfo implements Serializable {
    private String Date;
    private int Day;

    public DateInfo(String Date, int Day) {
        this.Date = Date;
        this.Day = Day;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }
}

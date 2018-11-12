package jcchen.goodsmanager.entity;

import java.io.Serializable;

public class DateInfo implements Serializable {

    private String YM_MAP[] = {"T", "E", "R", "S", "F", "W", "L", "C", "B", "K", "T", "J", "Q"};

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

    public String encode() {
        String year, month, code = "";
        year = Date.split("年")[0];
        month = Date.split("年")[1].split("月")[0];

        if (!year.substring(2, 3).equals("1"))
            code = YM_MAP[Integer.parseInt(year.substring(2, 3))] + code;
        code += YM_MAP[Integer.parseInt(year.substring(3, 4))] +
                YM_MAP[Integer.parseInt(month)] +
                Character.toString ((char) (64 + Day));
        return code;
    }
}

package jcchen.goodsmanager.entity;

import java.io.Serializable;

public class DateInfo implements Serializable {

    private static final String YM_MAP[] = {"T", "E", "R", "S", "F", "W", "L", "C", "B", "K", "T", "J", "Q"};

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

    public static int decode(String Code) {
        int result = 0;
        if (Code.length() < 2 || Code.length() > 3)
            return Integer.MAX_VALUE;

        // Parse year code.
        int StartIndex = 0;
        for (int i = 0; i <= YM_MAP.length; i++) {
            if (i == YM_MAP.length)
                return Integer.MAX_VALUE;
            if (YM_MAP[i].equals(Code.substring(StartIndex, StartIndex + 1))) {
                result = result * 100 + i;
                if (StartIndex == 1)
                    break;
                else if (Code.length() == 3) {
                    i = 0;
                    StartIndex = 1;
                }
                else
                    break;
            }
        }

        for (int i = 1; i <= YM_MAP.length; i++) {
            if (i == YM_MAP.length)
                return Integer.MAX_VALUE;
            if (YM_MAP[i].equals(Code.substring(Code.length() - 1, Code.length()))) {
                result = result * 100 + i;
                break;
            }
        }

        return result;
    }
}

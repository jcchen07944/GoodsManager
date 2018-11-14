package jcchen.goodsmanager.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingProfile implements Serializable {

    public static final int TIME_DIALOG_MODE_EVERYDAY = 0;
    public static final int TIME_DIALOG_MODE_ONCE = 1;

    private int TimeDialogShowFreq;
    private String DbIpAddress;
    private int DbPort;
    private String LastDialogShowTimeStamp;

    public SettingProfile() {
        this.TimeDialogShowFreq = TIME_DIALOG_MODE_EVERYDAY;
        this.DbIpAddress = "0.0.0.0";
        this.DbPort = 0;
        this.LastDialogShowTimeStamp = (new SimpleDateFormat("yyyy年MM月dd日"))
                                                .format(Calendar.getInstance().getTime());
    }

    public int getTimeDialogShowFreq() {
        return TimeDialogShowFreq;
    }

    public void setTimeDialogShowFreq(int mTimeDialogShowFreq) {
        this.TimeDialogShowFreq = mTimeDialogShowFreq;
    }

    public String getDbIpAddress() {
        return DbIpAddress;
    }

    public void setDbIpAddress(String mDbIpAddress) {
        this.DbIpAddress = mDbIpAddress;
    }

    public int getDbPort() {
        return DbPort;
    }

    public void setDbPort(int mDbPort) {
        this.DbPort = mDbPort;
    }

    public String getLastDialogShowTimeStamp() {
        return LastDialogShowTimeStamp;
    }

    public void setLastDialogShowTimeStamp(String mLastDialogShowTimeStamp) {
        this.LastDialogShowTimeStamp = mLastDialogShowTimeStamp;
    }
}

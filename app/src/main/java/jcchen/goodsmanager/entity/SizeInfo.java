package jcchen.goodsmanager.entity;

import java.io.Serializable;

public class SizeInfo implements Serializable {
    // public static final int TYPE_

    private String Name;
    private String Code;
    private int Type;

    public SizeInfo(String Name, String Code) {
        this.Name = Name;
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}

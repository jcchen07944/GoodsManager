package jcchen.goodsmanager.entity;

public class ColorInfo {
    private String Name;
    private String Code;

    public ColorInfo(String Name, String Code) {
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

}

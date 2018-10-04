package jcchen.goodsmanager.entity;

import java.util.Vector;

/**
 * Created by JCChen on 2018/9/21.
 */

public class TypeInfo {

    private String Type;

    private Vector<String> Column;

    public TypeInfo(String Type) {
        this.Type = Type;
    }

    public TypeInfo(String Type, Vector<String> Column) {
        this.Type = Type;
        this.Column = Column;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Vector<String> getColumn() {
        return Column;
    }

    public void setColumn(Vector<String> column) {
        Column = column;
    }
}

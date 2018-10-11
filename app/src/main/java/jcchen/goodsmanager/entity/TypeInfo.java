package jcchen.goodsmanager.entity;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by JCChen on 2018/9/21.
 */

public class TypeInfo implements Serializable {

    private String Type;

    private Vector<String> Column;

    public TypeInfo(String Type) {
        this.Type = Type;
    }

    public TypeInfo(String Type, Vector<String> Column) {
        this.Type = Type;
        if (Column == null)
            Column = new Vector<>();
        this.Column = (Vector<String>) Column.clone();
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

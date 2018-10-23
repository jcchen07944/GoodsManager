package jcchen.goodsmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by JCChen on 2018/9/21.
 */

public class TypeInfo implements Serializable {

    private String Type;

    private ArrayList<String> Column;

    public TypeInfo(String Type) {
        this.Type = Type;
    }

    public TypeInfo(String Type, ArrayList<String> Column) {
        this.Type = Type;
        if (Column == null)
            Column = new ArrayList<>();
        this.Column = (ArrayList<String>) Column.clone();
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public ArrayList<String> getColumn() {
        return Column;
    }

    public void setColumn(ArrayList<String> column) {
        Column = column;
    }
}

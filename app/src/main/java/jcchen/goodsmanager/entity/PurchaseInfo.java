package jcchen.goodsmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchaseInfo implements Serializable {

    private boolean Upload;
    private String FileName;

    public static class SizeStruct implements Serializable {
        private final int COLUMN_SIZE = 8;

        private String SizeName;
        private String[] Column;
        private String Note;
        private String Append;

        public SizeStruct() {
            this.Column = new String[COLUMN_SIZE];
        }

        public String getSizeName() {
            return SizeName;
        }

        public void setSizeName(String sizeName) {
            SizeName = sizeName;
        }

        public int getMaxColumnSize() {
            return COLUMN_SIZE;
        }

        public String getColumn(int index) {
            if (index >= COLUMN_SIZE || index < 0)
                return "";
            return Column[index];
        }

        public void setColumn(int index, String column) {
            if (index >= COLUMN_SIZE || index < 0)
                return;
            Column[index] = column;
        }

        public String getNote() {
            return Note;
        }

        public void setNote(String note) {
            Note = note;
        }

        public String getAppend() {
            return Append;
        }

        public void setAppend(String append) {
            Append = append;
        }
    }
    private TypeInfo typeInfo;
    private String Numbers;
    private String Mall;
    private String Position;
    private String Name;
    private int ListPrice;
    private int ActualPrice;
    private int IncomeK;
    private int IncomeT;
    private String Flexible;
    private ArrayList<ColorInfo> ColorList;
    private String Material;
    private ArrayList<SizeInfo> SizeList;
    private ArrayList<SizeStruct> SizeStructList;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public void setUpload(boolean upload) {
        this.Upload = upload;
    }

    public boolean isUpload() {
        return Upload;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public String getNumbers() {
        return Numbers;
    }

    public void setNumbers(String numbers) {
        Numbers = numbers;
    }

    public String getMall() {
        return Mall;
    }

    public void setMall(String mall) {
        Mall = mall;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getListPrice() {
        return ListPrice;
    }

    public void setListPrice(int listPrice) {
        ListPrice = listPrice;
    }

    public int getActualPrice() {
        return ActualPrice;
    }

    public void setActualPrice(int actualPrice) {
        ActualPrice = actualPrice;
    }

    public int getIncomeK() {
        return IncomeK;
    }

    public void setIncomeK(int incomeK) {
        IncomeK = incomeK;
    }

    public int getIncomeT() {
        return IncomeT;
    }

    public void setIncomeT(int incomeT) {
        IncomeT = incomeT;
    }

    public String getFlexible() {
        return Flexible;
    }

    public void setFlexible(String flexible) {
        Flexible = flexible;
    }

    public ArrayList<ColorInfo> getColorList() {
        return ColorList;
    }

    public void setColorList(ArrayList<ColorInfo> colorList) {
        ColorList = colorList;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public ArrayList<SizeInfo> getSizeList() {
        return SizeList;
    }

    public void setSizeList(ArrayList<SizeInfo> sizeList) {
        SizeList = sizeList;
    }

    public ArrayList<SizeStruct> getSizeStructList() {
        return SizeStructList;
    }

    public void setSizeStructList(ArrayList<SizeStruct> sizeStructList) {
        SizeStructList = sizeStructList;
    }
}

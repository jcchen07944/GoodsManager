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
        private String SizeName;
        private String Column0;
        private String Column1;
        private String Column2;
        private String Column3;
        private String Column4;
        private String Column5;
        private String Column6;
        private String Column7;
        private String Note;
        private String Append;

        public String getSizeName() {
            return SizeName;
        }

        public void setSizeName(String sizeName) {
            SizeName = sizeName;
        }

        public String getColumn0() {
            return Column0;
        }

        public void setColumn0(String column0) {
            Column0 = column0;
        }

        public String getColumn1() {
            return Column1;
        }

        public void setColumn1(String column1) {
            Column1 = column1;
        }

        public String getColumn2() {
            return Column2;
        }

        public void setColumn2(String column2) {
            Column2 = column2;
        }

        public String getColumn3() {
            return Column3;
        }

        public void setColumn3(String column3) {
            Column3 = column3;
        }

        public String getColumn4() {
            return Column4;
        }

        public void setColumn4(String column4) {
            Column4 = column4;
        }

        public String getColumn5() {
            return Column5;
        }

        public void setColumn5(String column5) {
            Column5 = column5;
        }

        public String getColumn6() {
            return Column6;
        }

        public void setColumn6(String column6) {
            Column6 = column6;
        }

        public String getColumn7() {
            return Column7;
        }

        public void setColumn7(String column7) {
            Column7 = column7;
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
    private boolean Flexible;
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

    public boolean isFlexible() {
        return Flexible;
    }

    public void setFlexible(boolean flexible) {
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

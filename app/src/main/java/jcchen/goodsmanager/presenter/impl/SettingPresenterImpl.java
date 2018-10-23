package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.SettingPresenter;
import jcchen.goodsmanager.view.MainActivity;

public class SettingPresenterImpl implements SettingPresenter {

    private Context context;

    public SettingPresenterImpl(Context context) {
        this.context = context;
    }

    public void saveExchangeRate(float exRate) {
        try {
            String fileName = "ExchangeRate";
            FileOutputStream mFileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            mFileOutputStream.write(MainActivity.toByteArray(exRate));
            mFileOutputStream.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public float getExchangeRate() {
        File dir = context.getFilesDir();
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            FileInputStream mFileInputStream;
            for (File file : subFiles) {
                try {
                    if (!file.getName().contains("ExchangeRate"))
                        continue;

                    byte buffer[] = new byte[(int) file.length()];
                    mFileInputStream = context.openFileInput(file.getName());
                    mFileInputStream.read(buffer);
                    mFileInputStream.close();
                    return (float) MainActivity.toObject(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        saveExchangeRate(35.0f);
        return 35.0f;
    }


    public void addType(TypeInfo typeInfo) {

    }

    public void addColor(ColorInfo colorInfo) {

    }

    public void addSize(SizeInfo sizeInfo) {

    }

    /* Get type list from model(maybe from DB or from setting) */
    @Override
    public ArrayList<TypeInfo> getTypeList() {
        // Now using static list.
        ArrayList<TypeInfo> typeList = new ArrayList<>();
        ArrayList<String> column = new ArrayList<>();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("上衣", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("腿寬");
        column.add("褲口寬");
        column.add("褲襠");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("褲子", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("臀寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        typeList.add(new TypeInfo("洋裝", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("內外", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("下擺寬");
        column.add("");
        column.add("");
        column.add("");
        column.add("");
        typeList.add(new TypeInfo("裙子", column));
        return typeList;
    }

    @Override
    public ArrayList<ColorInfo> getColorList() {
        // Now using static list.
        ArrayList<ColorInfo> colorList = new ArrayList<>();
        colorList.add(new ColorInfo("丈青", "NV"));
        colorList.add(new ColorInfo("黑", "KK"));
        colorList.add(new ColorInfo("米白", "IV"));
        colorList.add(new ColorInfo("杏", "CC"));
        colorList.add(new ColorInfo("均", "XX"));
        return colorList;
    }

    @Override
    public ArrayList<SizeInfo> getSizeList() {
        // Now using static list.
        ArrayList<SizeInfo> sizeList = new ArrayList<>();
        sizeList.add(new SizeInfo("XS", "XS"));
        sizeList.add(new SizeInfo("S", "S"));
        sizeList.add(new SizeInfo("M", "M"));
        sizeList.add(new SizeInfo("L", "L"));
        sizeList.add(new SizeInfo("XL", "XL"));
        sizeList.add(new SizeInfo("XXL", "XXL"));
        return sizeList;
    }
}

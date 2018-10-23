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

    @Override
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

    @Override
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



    @Override
    public void saveType(ArrayList<TypeInfo> list) {

        ArrayList<TypeInfo> typeList = (ArrayList<TypeInfo>) list.clone();

        if (typeList.size() == 0)
            typeList = getDefaultTypeList();

        for (int i = typeList.size() - 1; i >= 0; i--)
            if (typeList.get(i) == null)
                typeList.remove(i);

        if (typeList.size() == 0)
            typeList = getDefaultTypeList();

        try {
            String fileName = "TypeList";
            FileOutputStream mFileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            mFileOutputStream.write(MainActivity.toByteArray(typeList));
            mFileOutputStream.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveColor(ArrayList<ColorInfo> list) {

        ArrayList<ColorInfo> colorList = (ArrayList<ColorInfo>) list.clone();

        if (colorList.size() == 0)
            colorList = getDefaultColorList();

        for (int i = colorList.size() - 1; i >= 0; i--)
            if (colorList.get(i) == null)
                colorList.remove(i);

        if (colorList.size() == 0)
            colorList = getDefaultColorList();

        try {
            String fileName = "ColorList";
            FileOutputStream mFileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            mFileOutputStream.write(MainActivity.toByteArray(colorList));
            mFileOutputStream.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveSize(ArrayList<SizeInfo> list) {

        ArrayList<SizeInfo> sizeList = (ArrayList<SizeInfo>) list.clone();

        if (sizeList.size() == 0)
            sizeList = getDefaultSizeList();

        for (int i = sizeList.size() - 1; i >= 0; i--)
            if (sizeList.get(i) == null)
                sizeList.remove(i);

        if (sizeList.size() == 0)
            sizeList = getDefaultSizeList();

        try {
            String fileName = "SizeList";
            FileOutputStream mFileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            mFileOutputStream.write(MainActivity.toByteArray(sizeList));
            mFileOutputStream.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *  Get type list from model(maybe from DB or from setting)
     *
     *  Now getting from setting.
     **/
    @Override
    public ArrayList<TypeInfo> getTypeList() {
        File dir = context.getFilesDir();
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            FileInputStream mFileInputStream;
            for (File file : subFiles) {
                try {
                    if (!file.getName().contains("TypeList"))
                        continue;

                    byte buffer[] = new byte[(int) file.length()];
                    mFileInputStream = context.openFileInput(file.getName());
                    mFileInputStream.read(buffer);
                    mFileInputStream.close();
                    return (ArrayList<TypeInfo>) MainActivity.toObject(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ArrayList<TypeInfo> typeList = getDefaultTypeList();
        saveType(typeList);
        return typeList;
    }

    @Override
    public ArrayList<ColorInfo> getColorList() {
        File dir = context.getFilesDir();
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            FileInputStream mFileInputStream;
            for (File file : subFiles) {
                try {
                    if (!file.getName().contains("ColorList"))
                        continue;

                    byte buffer[] = new byte[(int) file.length()];
                    mFileInputStream = context.openFileInput(file.getName());
                    mFileInputStream.read(buffer);
                    mFileInputStream.close();
                    return (ArrayList<ColorInfo>) MainActivity.toObject(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ArrayList<ColorInfo> colorList = getDefaultColorList();
        saveColor(colorList);
        return colorList;
    }

    @Override
    public ArrayList<SizeInfo> getSizeList() {
        File dir = context.getFilesDir();
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            FileInputStream mFileInputStream;
            for (File file : subFiles) {
                try {
                    if (!file.getName().contains("SizeList"))
                        continue;

                    byte buffer[] = new byte[(int) file.length()];
                    mFileInputStream = context.openFileInput(file.getName());
                    mFileInputStream.read(buffer);
                    mFileInputStream.close();
                    return (ArrayList<SizeInfo>) MainActivity.toObject(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ArrayList<SizeInfo> sizeList = getDefaultSizeList();
        saveSize(sizeList);
        return sizeList;
    }

    private ArrayList<TypeInfo> getDefaultTypeList() {
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

    private ArrayList<ColorInfo> getDefaultColorList() {
        ArrayList<ColorInfo> colorList = new ArrayList<>();
        colorList.add(new ColorInfo("丈青", "NV"));
        colorList.add(new ColorInfo("黑", "KK"));
        colorList.add(new ColorInfo("米白", "IV"));
        colorList.add(new ColorInfo("杏", "CC"));
        colorList.add(new ColorInfo("均", "XX"));
        return colorList;
    }

    private ArrayList<SizeInfo> getDefaultSizeList() {
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

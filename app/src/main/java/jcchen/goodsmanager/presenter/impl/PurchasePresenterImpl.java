package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.PurchasePresenter;
import jcchen.goodsmanager.view.MainActivity;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchasePresenterImpl implements PurchasePresenter {

    private Context context;

    public PurchasePresenterImpl(Context context) {
        this.context = context;
    }

    /* Get type list from model(maybe from DB or from setting) */
    @Override
    public Vector<TypeInfo> getTypeList() {
        // Now using static list.
        Vector<TypeInfo> typeList = new Vector<>();
        Vector<String> column = new Vector<>();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        typeList.add(new TypeInfo("上衣", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("腿寬");
        column.add("褲口寬");
        column.add("褲襠");
        typeList.add(new TypeInfo("褲子", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("臀寬");
        column.add("下擺寬");
        column.add("袖長");
        typeList.add(new TypeInfo("洋裝", column));
        column.clear();
        column.add("全長");
        column.add("肩寬");
        column.add("胸寬");
        column.add("腰寬");
        column.add("下擺寬");
        column.add("袖長");
        typeList.add(new TypeInfo("內外", column));
        column.clear();
        column.add("全長");
        column.add("腰寬");
        column.add("臀寬");
        column.add("下擺寬");
        typeList.add(new TypeInfo("裙子", column));
        return typeList;
    }

    @Override
    public Vector<ColorInfo> getColorList() {
        // Now using static list.
        Vector<ColorInfo> colorList = new Vector<>();
        colorList.add(new ColorInfo("丈青", "NV"));
        colorList.add(new ColorInfo("黑", "KK"));
        colorList.add(new ColorInfo("米白", "IV"));
        colorList.add(new ColorInfo("杏", "CC"));
        colorList.add(new ColorInfo("均", "XX"));
        return colorList;
    }

    @Override
    public Vector<SizeInfo> getSizeList() {
        // Now using static list.
        Vector<SizeInfo> sizeList = new Vector<>();
        sizeList.add(new SizeInfo("XS", "XS"));
        sizeList.add(new SizeInfo("S", "S"));
        sizeList.add(new SizeInfo("M", "M"));
        sizeList.add(new SizeInfo("L", "L"));
        sizeList.add(new SizeInfo("XL", "XL"));
        sizeList.add(new SizeInfo("XXL", "XXL"));
        return sizeList;
    }

    @Override
    public Vector<PurchaseInfo> getPurchaseList() {
        Vector<PurchaseInfo> purchaseList = new Vector<>();

        File dir = context.getFilesDir();
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            FileInputStream mFileInputStream;
            for (File file : subFiles) {
                try {
                    if (!file.getName().contains("PurchaseInfo"))
                        continue;

                    byte buffer[] = new byte[(int) file.length()];
                    mFileInputStream = context.openFileInput(file.getName());
                    mFileInputStream.read(buffer);
                    mFileInputStream.close();
                    purchaseList.add((PurchaseInfo) MainActivity.toObject(buffer));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return purchaseList;
    }

    @Override
    public void sendPurchaseInfo(PurchaseInfo purchaseInfo) {

    }

    @Override
    public void savePurchaseInfo(PurchaseInfo purchaseInfo) {
        try {
            String fileName = "PurchaseInfo-" + (System.currentTimeMillis() / 1000);
            purchaseInfo.setFileName(fileName);
            FileOutputStream mFileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            mFileOutputStream.write(MainActivity.toByteArray(purchaseInfo));
            mFileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePurchaseInfo(PurchaseInfo purchaseInfo) {
        File dir = context.getFilesDir();
        (new File(dir, purchaseInfo.getFileName())).delete();
    }
}

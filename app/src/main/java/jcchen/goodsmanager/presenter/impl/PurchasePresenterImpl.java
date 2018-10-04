package jcchen.goodsmanager.presenter.impl;

import java.util.Vector;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.PurchasePresenter;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchasePresenterImpl implements PurchasePresenter {

    /* Get type list from model(maybe from DB or from setting) */
    @Override
    public Vector<TypeInfo> getTypeList() {
        // Now using static list.
        Vector<TypeInfo> typeList = new Vector<>();
        typeList.add(new TypeInfo("上衣"));
        typeList.add(new TypeInfo("褲子"));
        typeList.add(new TypeInfo("洋裝"));
        typeList.add(new TypeInfo("內外"));
        typeList.add(new TypeInfo("裙子"));
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
    public void sendPurchaseInfo(PurchaseInfo purchaseInfo) {

    }

    @Override
    public void savePurchaseInfo(PurchaseInfo purchaseInfo) {

    }
}

package jcchen.goodsmanager.presenter.impl;

import java.util.Vector;

import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.PurchasePresenter;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchasePresenterImpl implements PurchasePresenter {

    /* Get type list from model(maybe from DB or from setting) */
    public Vector<TypeInfo> getTypeList() {
        // Now using static type list.
        Vector<TypeInfo> typeList = new Vector<>();
        typeList.add(new TypeInfo("上衣"));
        typeList.add(new TypeInfo("褲子"));
        typeList.add(new TypeInfo("洋裝"));
        typeList.add(new TypeInfo("內外"));
        typeList.add(new TypeInfo("裙子"));
        return typeList;
    }

    public void sendPurchaseInfo(PurchaseInfo purchaseInfo) {

    }
}

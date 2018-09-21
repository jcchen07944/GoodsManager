package jcchen.goodsmanager.presenter.impl;

import java.util.Vector;

import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.PurchasePresenter;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchasePresenterImpl implements PurchasePresenter {

    public Vector<TypeInfo> getTypeList() {
        return new Vector<TypeInfo>();
    }

    public void sendPurchaseInfo(PurchaseInfo purchaseInfo) {

    }
}

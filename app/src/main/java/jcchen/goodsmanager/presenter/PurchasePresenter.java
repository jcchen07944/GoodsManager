package jcchen.goodsmanager.presenter;

import java.util.Vector;

import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.TypeInfo;

/**
 * Created by JCChen on 2018/9/21.
 */

public interface PurchasePresenter {
    Vector<TypeInfo> getTypeList();
    void sendPurchaseInfo(PurchaseInfo purchaseInfo);
}

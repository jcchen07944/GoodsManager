package jcchen.goodsmanager.presenter;


import java.util.Vector;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;

/**
 * Created by JCChen on 2018/9/21.
 */

public interface PurchasePresenter {
    Vector<TypeInfo> getTypeList();
    Vector<ColorInfo> getColorList();
    Vector<SizeInfo> getSizeList();
    Vector<PurchaseInfo> getPurchaseList();
    void sendPurchaseInfo(PurchaseInfo purchaseInfo);
    void removePurchaseInfo(PurchaseInfo purchaseInfo);
    void savePurchaseInfo(PurchaseInfo purchaseInfo);
    void updatePurchaseInfo(PurchaseInfo oldPurchaseInfo, PurchaseInfo newPurchaseInfo);
}

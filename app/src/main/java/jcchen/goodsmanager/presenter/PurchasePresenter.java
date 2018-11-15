package jcchen.goodsmanager.presenter;


import java.util.ArrayList;
import java.util.Vector;

import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.listener.OnPurchaseInfoUploadListener;

/**
 * Created by JCChen on 2018/9/21.
 */

public interface PurchasePresenter {
    ArrayList<PurchaseInfo> getPurchaseList();
    void uploadAllPurchaseInfo(OnPurchaseInfoUploadListener listener);
    void removePurchaseInfo(PurchaseInfo purchaseInfo);
    void savePurchaseInfo(PurchaseInfo purchaseInfo);
    void updatePurchaseInfo(PurchaseInfo oldPurchaseInfo, PurchaseInfo newPurchaseInfo);
}

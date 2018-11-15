package jcchen.goodsmanager.view.listener;

import jcchen.goodsmanager.entity.PurchaseInfo;

public interface OnPurchaseInfoUploadListener {
    void onUploadUpdate(PurchaseInfo mPurchaseInfo);
    void onUploadEnd();
}

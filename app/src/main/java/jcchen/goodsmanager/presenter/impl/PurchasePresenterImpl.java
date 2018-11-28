package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.model.impl.LocalModelImpl;
import jcchen.goodsmanager.presenter.PurchasePresenter;
import jcchen.goodsmanager.view.MainActivity;
import jcchen.goodsmanager.view.listener.OnPurchaseInfoUploadListener;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchasePresenterImpl implements PurchasePresenter {

    private Context context;

    private LocalModelImpl mLocalModel;

    public PurchasePresenterImpl(Context context) {
        this.context = context;
        this.mLocalModel = new LocalModelImpl(context);
    }

    @Override
    public ArrayList<PurchaseInfo> getPurchaseList() {
        ArrayList<PurchaseInfo> purchaseList = new ArrayList<>();

        Object object;
        while ((object = mLocalModel.getPrivateObject("PurchaseInfo", true)) != null) {
            try {
                purchaseList.add((PurchaseInfo) object);
            } catch (Exception e) {
                // if cast fail.
                e.printStackTrace();
            }
        }

        return purchaseList;
    }

    @Override
    public void uploadAllPurchaseInfo(OnPurchaseInfoUploadListener listener) {
        ArrayList<PurchaseInfo> purchaseList = getPurchaseList();
        for (int i = 0; i < purchaseList.size(); i++) {
            if (!purchaseList.get(i).isUpload()) {
                // Upload
                listener.onUploadUpdate(purchaseList.get(i));
            }
        }
        listener.onUploadEnd();
    }

    @Override
    public void savePurchaseInfo(PurchaseInfo purchaseInfo) {
        mLocalModel.savePrivateObject("PurchaseInfo", purchaseInfo, true);
    }

    @Override
    public void removePurchaseInfo(PurchaseInfo purchaseInfo) {
        mLocalModel.removePrivateObject(purchaseInfo.getFileName());
    }

    @Override
    public void updatePurchaseInfo(PurchaseInfo oldPurchaseInfo, PurchaseInfo newPurchaseInfo) {
        removePurchaseInfo(oldPurchaseInfo);
        savePurchaseInfo(newPurchaseInfo);
    }
}

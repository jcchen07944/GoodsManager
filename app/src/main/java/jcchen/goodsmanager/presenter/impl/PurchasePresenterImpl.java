package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import jcchen.goodsmanager.entity.DateInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.model.impl.LocalModelImpl;
import jcchen.goodsmanager.presenter.PurchasePresenter;
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
        return sortPurchaseList(purchaseList);
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
        String objectName = mLocalModel.savePrivateObject("PurchaseInfo", purchaseInfo, true);
        purchaseInfo.setFileName(objectName);
        mLocalModel.updatePrivateObject(objectName, purchaseInfo);
    }

    @Override
    public void removePurchaseInfo(PurchaseInfo purchaseInfo) {
        mLocalModel.removePrivateObject(purchaseInfo.getFileName());
    }

    @Override
    public void updatePurchaseInfo(PurchaseInfo oldPurchaseInfo, PurchaseInfo newPurchaseInfo) {
        newPurchaseInfo.setFileName(oldPurchaseInfo.getFileName());
        mLocalModel.updatePrivateObject(oldPurchaseInfo.getFileName(), newPurchaseInfo);
    }

    private ArrayList<PurchaseInfo> sortPurchaseList(ArrayList<PurchaseInfo> purchaseList) {
        ArrayList<Integer> priorityList = new ArrayList<>();
        for (int i = 0; i < purchaseList.size(); i++) {
            int priority = Integer.MAX_VALUE;
            String Code = purchaseList.get(i).getNumbers();
            if (Code.length() >= 5) {
                priority = DateInfo.decode(Code.substring(0, Code.length() - 3));
                priority = isInteger(Code.substring(Code.length() - 2, Code.length()))?
                        priority : Integer.MAX_VALUE;
                if (priority != Integer.MAX_VALUE) {
                    priority = priority * 1000 +
                            (Code.charAt(Code.length() - 3) - 'A') * 100 +
                            (99 - Integer.parseInt(Code.substring(Code.length() - 2, Code.length())));
                }
            }
            priorityList.add(priority);
        }

        // Bubble sort
        for (int i = 0; i < purchaseList.size(); i++) {
            for (int j = 0; j < purchaseList.size() - 1; j++) {
                if (priorityList.get((j)) < priorityList.get(j + 1)) {
                    Collections.swap(purchaseList, j, j + 1);
                    Collections.swap(priorityList, j, j + 1);
                }
            }
        }

        return purchaseList;
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

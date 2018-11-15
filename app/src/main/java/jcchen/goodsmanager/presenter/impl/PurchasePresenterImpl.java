package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.presenter.PurchasePresenter;
import jcchen.goodsmanager.view.MainActivity;
import jcchen.goodsmanager.view.listener.OnPurchaseInfoUploadListener;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchasePresenterImpl implements PurchasePresenter {

    private Context context;

    public PurchasePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<PurchaseInfo> getPurchaseList() {
        ArrayList<PurchaseInfo> purchaseList = new ArrayList<>();

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
        try {
            String fileName = "PurchaseInfo-" + System.currentTimeMillis();
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
        try {
            (new File(dir, purchaseInfo.getFileName())).delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updatePurchaseInfo(PurchaseInfo oldPurchaseInfo, PurchaseInfo newPurchaseInfo) {
        removePurchaseInfo(oldPurchaseInfo);
        savePurchaseInfo(newPurchaseInfo);
    }
}

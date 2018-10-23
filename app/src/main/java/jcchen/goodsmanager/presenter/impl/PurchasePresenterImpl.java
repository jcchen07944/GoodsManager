package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.presenter.PurchasePresenter;
import jcchen.goodsmanager.view.MainActivity;

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
    public void sendPurchaseInfo(PurchaseInfo purchaseInfo) {

    }

    @Override
    public void savePurchaseInfo(PurchaseInfo purchaseInfo) {
        try {
            String fileName = "PurchaseInfo-" + (System.currentTimeMillis() / 1000);
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
        (new File(dir, purchaseInfo.getFileName())).delete();
    }

    @Override
    public void updatePurchaseInfo(PurchaseInfo oldPurchaseInfo, PurchaseInfo newPurchaseInfo) {
        removePurchaseInfo(oldPurchaseInfo);
        savePurchaseInfo(newPurchaseInfo);
    }
}

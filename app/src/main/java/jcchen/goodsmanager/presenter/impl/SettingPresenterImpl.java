package jcchen.goodsmanager.presenter.impl;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.presenter.SettingPresenter;
import jcchen.goodsmanager.view.MainActivity;

public class SettingPresenterImpl implements SettingPresenter {

    private Context context;

    public SettingPresenterImpl(Context context) {
        this.context = context;
    }

    public void saveExchangeRate(float exRate) {
        try {
            String fileName = "ExchangeRate";
            FileOutputStream mFileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            mFileOutputStream.write(MainActivity.toByteArray(exRate));
            mFileOutputStream.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public float getExchangeRate() {
        File dir = context.getFilesDir();
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            FileInputStream mFileInputStream;
            for (File file : subFiles) {
                try {
                    if (!file.getName().contains("ExchangeRate"))
                        continue;

                    byte buffer[] = new byte[(int) file.length()];
                    mFileInputStream = context.openFileInput(file.getName());
                    mFileInputStream.read(buffer);
                    mFileInputStream.close();
                    return (float) MainActivity.toObject(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        saveExchangeRate(35.0f);
        return 35.0f;
    }
}

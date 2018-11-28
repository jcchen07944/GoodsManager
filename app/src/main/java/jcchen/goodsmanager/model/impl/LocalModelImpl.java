package jcchen.goodsmanager.model.impl;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import jcchen.goodsmanager.model.LocalModel;

public class LocalModelImpl implements LocalModel {

    private Context context;

    private File privateDir;

    // For getObject in turn.
    private String lastObjectName;
    private int nextObjectIndex;

    public LocalModelImpl(Context context) {
        this.context = context;
        this.privateDir = context.getFilesDir();
        this.lastObjectName = "";
    }

    @Override
    public String savePrivateObject(String objectName, Object object, boolean hasTimeStamp) {
        if (hasTimeStamp)
            objectName += "-" + System.currentTimeMillis();
        try {
            FileOutputStream mFileOutputStream = context.openFileOutput(objectName, Context.MODE_PRIVATE);
            mFileOutputStream.write(toByteArray(object));
            mFileOutputStream.close();
            return objectName;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public Object getPrivateObject(String objectName, boolean hasTimeStamp) {
        if (objectName.equals(""))
            return null;

        File[] subFiles = privateDir.listFiles();
        if (subFiles != null) {
            if (!objectName.equals(lastObjectName) && hasTimeStamp) {
                nextObjectIndex = 0;
                lastObjectName = objectName;
            }
            else if (!objectName.equals(lastObjectName)) {
                nextObjectIndex = 0;
                lastObjectName = "";
            }

            FileInputStream mFileInputStream;
            for (int i = nextObjectIndex; i < subFiles.length; i++) {
                try {
                    nextObjectIndex = i + 1;
                    if (!subFiles[i].getName().contains(objectName))
                        continue;

                    byte buffer[] = new byte[(int) subFiles[i].length()];
                    mFileInputStream = context.openFileInput(subFiles[i].getName());
                    mFileInputStream.read(buffer);
                    mFileInputStream.close();
                    return toObject(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        nextObjectIndex = 0;
        lastObjectName = "";
        return null;
    }

    @Override
    public void removePrivateObject(String objectName) {
        try {
            (new File(privateDir, objectName)).delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }

    private Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }
}

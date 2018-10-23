package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jcchen.goodsmanager.view.listener.OnSettingEditListener;

public class EditSettingDialogFragment extends DialogFragment {

    public static final String TAG = "EditSettingDialogFragment";

    private int resID = -1;

    private OnSettingEditListener mOnSettingEditListener;

    public void setResID(int resID) {
        this.resID = resID;
    }

    public void setListener(OnSettingEditListener mOnSettingEditListener) {
        this.mOnSettingEditListener = mOnSettingEditListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (resID != -1)
            return inflater.inflate(resID, container);
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mOnSettingEditListener != null) {
            mOnSettingEditListener.onEditStart(view);
        }
    }
}

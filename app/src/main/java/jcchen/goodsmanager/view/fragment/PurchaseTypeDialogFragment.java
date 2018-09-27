package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jcchen.goodsmanager.R;

/**
 * Created by JCChen on 2018/9/26.
 */

public class PurchaseTypeDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchase_type_dialog_layout, container);
        return view;
    }
}

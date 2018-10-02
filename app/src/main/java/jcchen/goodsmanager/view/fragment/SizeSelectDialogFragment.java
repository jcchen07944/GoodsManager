package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.listener.OnSizeSelectedListener;

public class SizeSelectDialogFragment extends DialogFragment {

    private PurchasePresenterImpl presenter;
    private OnSizeSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.size_select_dialog_layout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public void setPresenter(PurchasePresenterImpl presenter) {
        this.presenter = presenter;
    }

    public void setListener(OnSizeSelectedListener listener) {
        this.listener = listener;
    }

    public void init() {

    }
}

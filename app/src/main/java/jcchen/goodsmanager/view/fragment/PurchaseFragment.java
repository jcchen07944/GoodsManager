package jcchen.goodsmanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.container.Container;

public class PurchaseFragment extends Fragment {

    public static final String TAG = "PurchaseFragment";

    private TypeInfo selectedType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchase_layout, container, false);
        ((Container) view).showItem(selectedType);
        return view;
    }

    public void setSelectedType(TypeInfo selectedType) {
        this.selectedType = selectedType;
    }
}

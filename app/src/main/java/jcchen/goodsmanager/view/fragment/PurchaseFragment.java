package jcchen.goodsmanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.container.Container;

public class PurchaseFragment extends Fragment {

    public static final String TAG = "PurchaseFragment";

    private TypeInfo selectedType;

    private View savedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (savedView == null)
            savedView = inflater.inflate(R.layout.purchase_layout, container, false);
        view = savedView;
        ((ScrollView) view).fullScroll(ScrollView.FOCUS_UP);
        ((Container) view).showItem(selectedType);
        return view;
    }

    public void setSelectedType(TypeInfo selectedType) {
        this.selectedType = selectedType;
    }
}

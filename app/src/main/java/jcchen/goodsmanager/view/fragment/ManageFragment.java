package jcchen.goodsmanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.container.Container;

public class ManageFragment extends Fragment {

    public static final String TAG = "ManageFragment";

    private View oldView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (oldView == null)
            oldView = inflater.inflate(R.layout.manage_layout, container, false);
        view = oldView;
        return view;
    }

    public void onBackPressed() {
        if (oldView != null)
            ((Container) oldView).onBackPressed();
    }
}

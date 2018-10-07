package jcchen.goodsmanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jcchen.goodsmanager.R;

public class ManageFragment extends Fragment {

    public static final String TAG = "ManageFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_layout, container, false);
        return view;
    }
}

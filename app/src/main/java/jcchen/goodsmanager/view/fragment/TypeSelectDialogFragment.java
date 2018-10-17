package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.container.Container;
import jcchen.goodsmanager.view.container.TypeSelectDialogContainer;

/**
 * Created by JCChen on 2018/9/26.
 */

public class TypeSelectDialogFragment extends DialogFragment {

    public static final String TAG = "TypeSelectDialogFragment";

    private TypeInfo typeInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.type_select_dialog_layout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((Container) view).showItem(typeInfo);
        return view;
    }

    public void setDefaultType(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }
}

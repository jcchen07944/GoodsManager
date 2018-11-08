package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;

import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListView;

public class TypeFocusListAdapter extends FocusListView.FocusListViewAdapter<TypeInfo> {

    public TypeFocusListAdapter(Context context, ListView listView, ArrayList<TypeInfo> list) {
        super(context, listView, list);
    }

    public int getItemPosition(TypeInfo typeInfo) {
        for (int i = getCount() / 2 - 1; ; i++) {
            if (typeInfo.getType().equals(((TypeInfo) getItem(i)).getType()))
                return i;
        }
    }

}

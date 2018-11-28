package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;

import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListView;

public class TypeFocusListAdapter extends FocusListView.FocusListViewAdapter<TypeInfo> {

    private ArrayList<TypeInfo> typeList;

    public TypeFocusListAdapter(Context context, ListView listView, ArrayList<TypeInfo> list) {
        super(context, listView, list);
        typeList = list;
    }

    public int getItemPosition(TypeInfo typeInfo) {
        int i, cnt;
        for (i = getCount() / 2 - 1, cnt = 0; cnt < typeList.size(); i++, cnt++) {
            if (typeInfo.getType().equals(((TypeInfo) getItem(i)).getType()))
                return i;
        }
        return i;
    }
}

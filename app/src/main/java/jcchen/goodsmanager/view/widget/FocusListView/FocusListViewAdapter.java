package jcchen.goodsmanager.view.widget.FocusListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListViewContent;

/**
 * Created by JCChen on 2018/9/26.
 */

public class FocusListViewAdapter extends BaseAdapter {

    private final int count = 10000;  // Unlimited content.

    private Context context;
    private ListView mListView;
    private ArrayList<TypeInfo> typeList;

    public FocusListViewAdapter(Context context, ListView listView, ArrayList<TypeInfo> typeList) {
        this.context = context;
        this.mListView = listView;
        this.typeList = typeList;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return typeList.get((int) getItemId(position));
    }

    @Override
    public long getItemId(int position) {
        if(position >= count / 2)
            return (position - count / 2) % typeList.size();
        else
            return Math.abs((count / 2 - position - 1) % typeList.size() - typeList.size() + 1);
    }

    public int getItemPosition(TypeInfo typeInfo) {
        for (int i = getCount() / 2 - 1; ; i++) {
            if (typeInfo.getType().equals(((TypeInfo) getItem(i)).getType()))
                return i;
        }
    }


    public int getListSize() {
        return typeList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.focus_listview_content, null);
        }
        ((FocusListViewContent) convertView).setParentHeight(mListView.getHeight());
        TextView mTextView = (TextView) convertView.findViewById(R.id.focus_listview_text);
        mTextView.setText(((TypeInfo) getItem(position)).getType());
        return convertView;
    }
}

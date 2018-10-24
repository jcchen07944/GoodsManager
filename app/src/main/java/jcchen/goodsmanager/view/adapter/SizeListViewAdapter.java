package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.SizeInfo;

public class SizeListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SizeInfo> sizeList;
    private boolean[] selectList;

    private LayoutInflater inflater;

    public SizeListViewAdapter(Context context, ArrayList<SizeInfo> sizeList) {
        this.context = context;
        this.sizeList = sizeList;
        selectList = new boolean[sizeList.size()];
        for(int i = 0; i < sizeList.size(); i++)
            selectList[i] = false;
    }

    @Override
    public int getCount() {
        return sizeList.size();
    }

    @Override
    public Object getItem(int i) {
        return sizeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public long getItemId(String Name) {
        for(int j = 0; j < sizeList.size(); j++)
            if(sizeList.get(j).getName().equals(Name))
                return j;
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.size_listview_content, null);

        TextView sizeName = (TextView) view.findViewById(R.id.size_name);
        sizeName.setText(sizeList.get(i).getName());
        if(selectList[(int) getItemId(i)]) {
            view.setBackground(ContextCompat.getDrawable(context, R.color.colorSecondaryLight));
            sizeName.setTextColor(ContextCompat.getColor(context, R.color.colorTextOnSecondary));
        } else {
            view.setBackground(ContextCompat.getDrawable(context, R.color.colorObjectBackground));
            sizeName.setTextColor(ContextCompat.getColor(context, R.color.colorTextOnBackground));
        }
        return view;
    }

    public boolean isExist(SizeInfo sizeInfo) {
        for (int i = 0; i < sizeList.size(); i++)
            if (sizeList.get(i).getName().equals(sizeInfo.getName()))
                return true;
        return false;
    }

    public void setSelected(int id, boolean state) {
        selectList[id] = state;
        notifyDataSetChanged();
    }

    public boolean isSelected(int id) {
        return selectList[id];
    }
}

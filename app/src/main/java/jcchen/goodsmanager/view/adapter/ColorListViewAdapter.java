package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;

public class ColorListViewAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<ColorInfo> colorList, filterList;
    private boolean[] selectList;

    private LayoutInflater inflater;
    private ColorFilter mColorFilter;

    public ColorListViewAdapter(Context context, ArrayList<ColorInfo> colorList) {
        this.context = context;
        this.colorList = colorList;
        filterList = new ArrayList<>(colorList);
        selectList = new boolean[colorList.size()];
        for(int i = 0; i < colorList.size(); i++)
            selectList[i] = false;
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public Object getItem(int i) {
        return colorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        for(int j = 0; j < filterList.size(); j++)
            if(filterList.get(j).getName().equals(colorList.get(i).getName()))
                return j;
        return -1;
    }

    public long getItemId(String Name) {
        for(int j = 0; j < filterList.size(); j++)
            if(filterList.get(j).getName().equals(Name))
                return j;
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.color_listview_content, null);

        TextView colorName = (TextView) view.findViewById(R.id.color_name);
        colorName.setText(colorList.get(i).getName());
        if(selectList[(int) getItemId(i)]) {
            view.setBackground(ContextCompat.getDrawable(context, R.color.colorSecondaryLight));
            colorName.setTextColor(ContextCompat.getColor(context, R.color.colorTextOnSecondary));
        } else {
            view.setBackground(ContextCompat.getDrawable(context, R.color.colorObjectBackground));
            colorName.setTextColor(ContextCompat.getColor(context, R.color.colorTextOnBackground));
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if(mColorFilter == null)
            mColorFilter = new ColorFilter();
        return mColorFilter;
    }

    public boolean isExist(ColorInfo colorInfo) {
        for (int i = 0; i < filterList.size(); i++)
            if (filterList.get(i).getName().equals(colorInfo.getName()))
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


    public ArrayList<ColorInfo> sort(ArrayList<ColorInfo> unSortList) {
        if (unSortList.size() == 0)
            return unSortList;

        ArrayList<ColorInfo> newList = new ArrayList<>();
        for (int i = 0; i < filterList.size(); i++)
            for (int j = 0; j < unSortList.size(); j++)
                if (filterList.get(i).getName().equals(unSortList.get(j).getName())) {
                    newList.add(unSortList.get(j));
                    unSortList.remove(j);
                    break;
                }
        return newList;
    }

    private class ColorFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults mFilterResults = new FilterResults();

            if(constraint != null && constraint.length() > 0) {
                ArrayList<ColorInfo> newColorList = new ArrayList<>();
                for(int i = 0; i < filterList.size(); i++) {
                    if(filterList.get(i).getName().contains(constraint.toString())) {
                        newColorList.add(filterList.get(i));
                    }
                }
                mFilterResults.count = newColorList.size();
                mFilterResults.values = newColorList;
            }
            else {
                mFilterResults.count = filterList.size();
                mFilterResults.values = filterList;
            }
            return mFilterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            colorList = (ArrayList<ColorInfo>) results.values;
            notifyDataSetChanged();
        }
    }
}

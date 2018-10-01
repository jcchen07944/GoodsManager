package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;

public class ColorListViewAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private Vector<ColorInfo> colorList, filterList;

    private LayoutInflater inflater;
    private ColorFilter mColorFilter;

    public ColorListViewAdapter(Context context, Vector<ColorInfo> colorList) {
        this.context = context;
        this.colorList = colorList;
        filterList = new Vector<>(colorList);
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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.color_listview_content, null);

        TextView colorName = (TextView) view.findViewById(R.id.color_name);
        colorName.setText(colorList.get(i).getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        if(mColorFilter == null)
            mColorFilter = new ColorFilter();
        return mColorFilter;
    }

    private class ColorFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults mFilterResults = new FilterResults();

            if(constraint != null && constraint.length() > 0) {
                Vector<ColorInfo> newColorList = new Vector<>();
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
            colorList = (Vector<ColorInfo>) results.values;
            notifyDataSetChanged();
        }
    }
}

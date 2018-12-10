package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.view.container.SizePurchaseViewPagerContainer;

public class SizePurchaseViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<SizePurchaseViewPagerContainer> pageList;

    public SizePurchaseViewPagerAdapter(Context context, ArrayList<SizePurchaseViewPagerContainer> pageList) {
        this.context = context;
        this.pageList = pageList;
    }

    @Override
    public int getCount() {
        return pageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pageList.get(position));
        return pageList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public ArrayList<PurchaseInfo.SizeStruct> collectSizeStruct() {
        ArrayList<PurchaseInfo.SizeStruct> sizeStructList = new ArrayList<>();
        for (int i = 0; i < pageList.size(); i++) {
            sizeStructList.add(new PurchaseInfo.SizeStruct());
            sizeStructList.get(i).setSizeName(((EditText) pageList.get(i).findViewById(R.id.content_size_text)).getText().toString());
            sizeStructList.get(i).setColumn(0, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_1)).getText().toString());
            sizeStructList.get(i).setColumn(1, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_2)).getText().toString());
            sizeStructList.get(i).setColumn(2, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_3)).getText().toString());
            sizeStructList.get(i).setColumn(3, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_4)).getText().toString());
            sizeStructList.get(i).setColumn(4, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_5)).getText().toString());
            sizeStructList.get(i).setColumn(5, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_6)).getText().toString());
            sizeStructList.get(i).setColumn(6, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_7)).getText().toString());
            sizeStructList.get(i).setColumn(7, ((EditText) pageList.get(i).findViewById(R.id.purchase_size_8)).getText().toString());
            sizeStructList.get(i).setAppend(((EditText) pageList.get(i).findViewById(R.id.purchase_append)).getText().toString());
            sizeStructList.get(i).setNote(((EditText) pageList.get(i).findViewById(R.id.purchase_note)).getText().toString());
        }
        return sizeStructList;
    }
}

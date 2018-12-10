package jcchen.goodsmanager.view.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.adapter.SizeDetailViewPagerAdapter;
import jcchen.goodsmanager.view.container.SizeDetailViewPagerContainer;

public class SizeDetailDialogFragment extends DialogFragment {

    public static final String TAG = "SizeDetailDialogFragment";

    private ArrayList<PurchaseInfo.SizeStruct> sizeDetail;
    private TypeInfo typeInfo;

    private ViewPager mViewPager;
    private CirclePageIndicator mCirclePageIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.size_detail_layout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ArrayList<SizeDetailViewPagerContainer> pageList = new ArrayList<>();
        for (int i = 0; i < sizeDetail.size(); i++) {
            pageList.add(new SizeDetailViewPagerContainer(getActivity()));
            if (!sizeDetail.get(i).getSizeName().equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_name)).setText(sizeDetail.get(i).getSizeName());
            if (!sizeDetail.get(i).getColumn(0).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_1)).setText(sizeDetail.get(i).getColumn(0));
            if (!typeInfo.getColumn().get(0).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_1_text)).setText("(" + typeInfo.getColumn().get(0) + ")");
            if (!sizeDetail.get(i).getColumn(1).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_2)).setText(sizeDetail.get(i).getColumn(1));
            if (!typeInfo.getColumn().get(1).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_2_text)).setText("(" + typeInfo.getColumn().get(1) + ")");
            if (!sizeDetail.get(i).getColumn(2).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_3)).setText(sizeDetail.get(i).getColumn(2));
            if (!typeInfo.getColumn().get(2).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_3_text)).setText("(" + typeInfo.getColumn().get(2) + ")");
            if (!sizeDetail.get(i).getColumn(3).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_4)).setText(sizeDetail.get(i).getColumn(3));
            if (!typeInfo.getColumn().get(3).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_4_text)).setText("(" + typeInfo.getColumn().get(3) + ")");
            if (!sizeDetail.get(i).getColumn(4).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_5)).setText(sizeDetail.get(i).getColumn(4));
            if (!typeInfo.getColumn().get(4).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_5_text)).setText("(" + typeInfo.getColumn().get(4) + ")");
            if (!sizeDetail.get(i).getColumn(5).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_6)).setText(sizeDetail.get(i).getColumn(5));
            if (!typeInfo.getColumn().get(5).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_6_text)).setText("(" + typeInfo.getColumn().get(5) + ")");
            if (!sizeDetail.get(i).getColumn(6).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_7)).setText(sizeDetail.get(i).getColumn(6));
            if (!typeInfo.getColumn().get(6).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_7_text)).setText("(" + typeInfo.getColumn().get(6) + ")");
            if (!sizeDetail.get(i).getColumn(7).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_8)).setText(sizeDetail.get(i).getColumn(7));
            if (!typeInfo.getColumn().get(7).equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_size_8_text)).setText("(" + typeInfo.getColumn().get(7) + ")");
            if (!sizeDetail.get(i).getNote().equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_note)).setText(sizeDetail.get(i).getNote());
            if (!sizeDetail.get(i).getAppend().equals(""))
                ((TextView) pageList.get(i).findViewById(R.id.detail_append)).setText(sizeDetail.get(i).getAppend());
        }
        SizeDetailViewPagerAdapter adapter = new SizeDetailViewPagerAdapter(getActivity(), pageList);
        mViewPager = (ViewPager) view.findViewById(R.id.detail_viewpager);
        mViewPager.setAdapter(adapter);

        mCirclePageIndicator = (CirclePageIndicator) view.findViewById(R.id.detail_indicator);
        mCirclePageIndicator.setViewPager(mViewPager);
    }

    public void setSizeDetail(ArrayList<PurchaseInfo.SizeStruct> sizeDetail) {
        this.sizeDetail = sizeDetail;
    }

    public void setType(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }
}

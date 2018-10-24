package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.container.ColorSettingContainer;
import jcchen.goodsmanager.view.container.NormalSettingContainer;
import jcchen.goodsmanager.view.container.SizeSettingContainer;
import jcchen.goodsmanager.view.container.TypeSettingContainer;
import jcchen.goodsmanager.view.widget.NonSwipeViewPager;

public class SettingDialogFragment extends DialogFragment {

    public static final String TAG = "SettingDialogFragment";

    private TabLayout mTabLayout;
    private NonSwipeViewPager mViewPager;

    private Vector<FrameLayout> pageList;
    private String[] pageTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.setting_dialog_layout, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pageTitle = new String[4];
        pageList = new Vector<>();
        pageTitle[0] = getActivity().getResources().getString(R.string.normal);
        pageList.add(new NormalSettingContainer(getActivity()));
        pageTitle[1] = getActivity().getResources().getString(R.string.type);
        pageList.add(new TypeSettingContainer(getActivity()));
        pageTitle[2] = getActivity().getResources().getString(R.string.color);
        pageList.add(new ColorSettingContainer(getActivity()));
        pageTitle[3] = getActivity().getResources().getString(R.string.size);
        pageList.add(new SizeSettingContainer(getActivity()));

        mViewPager = (NonSwipeViewPager) view.findViewById(R.id.setting_pager);
        mViewPager.setAdapter(new ViewPagerAdapter());
        mTabLayout = (TabLayout) view.findViewById(R.id.setting_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class ViewPagerAdapter extends PagerAdapter {

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

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }
    }
}
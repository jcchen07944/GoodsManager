package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.PagerAdapter;

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

    private NormalSettingContainer mNormalSettingContainer;
    private TypeSettingContainer mTypeSettingContainer;
    private ColorSettingContainer mColorSettingContainer;
    private SizeSettingContainer mSizeSettingContainer;

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
        mNormalSettingContainer = new NormalSettingContainer(getActivity());
        pageList.add(mNormalSettingContainer);
        pageTitle[1] = getActivity().getResources().getString(R.string.type);
        mTypeSettingContainer = new TypeSettingContainer(getActivity());
        pageList.add(mTypeSettingContainer);
        pageTitle[2] = getActivity().getResources().getString(R.string.color);
        mColorSettingContainer = new ColorSettingContainer(getActivity());
        pageList.add(mColorSettingContainer);
        pageTitle[3] = getActivity().getResources().getString(R.string.size);
        mSizeSettingContainer = new SizeSettingContainer(getActivity());
        pageList.add(mSizeSettingContainer);

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
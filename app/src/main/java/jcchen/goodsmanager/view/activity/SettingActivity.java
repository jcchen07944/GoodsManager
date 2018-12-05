package jcchen.goodsmanager.view.activity;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.container.ColorSettingContainer;
import jcchen.goodsmanager.view.container.NormalSettingContainer;
import jcchen.goodsmanager.view.container.SizeSettingContainer;
import jcchen.goodsmanager.view.container.TypeSettingContainer;
import jcchen.goodsmanager.view.widget.NonSwipeViewPager;

public class SettingActivity extends AppCompatActivity {

    public static final String TAG = "SettingActivity";

    private TabLayout mTabLayout;
    private NonSwipeViewPager mViewPager;

    private Vector<FrameLayout> pageList;
    private String[] pageTitle;

    private NormalSettingContainer mNormalSettingContainer;
    private TypeSettingContainer mTypeSettingContainer;
    private ColorSettingContainer mColorSettingContainer;
    private SizeSettingContainer mSizeSettingContainer;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        pageTitle = new String[4];
        pageList = new Vector<>();
        pageTitle[0] = this.getResources().getString(R.string.normal);
        mNormalSettingContainer = new NormalSettingContainer(this);
        pageList.add(mNormalSettingContainer);
        pageTitle[1] = this.getResources().getString(R.string.type);
        mTypeSettingContainer = new TypeSettingContainer(this);
        pageList.add(mTypeSettingContainer);
        pageTitle[2] = this.getResources().getString(R.string.color);
        mColorSettingContainer = new ColorSettingContainer(this);
        pageList.add(mColorSettingContainer);
        pageTitle[3] = this.getResources().getString(R.string.size);
        mSizeSettingContainer = new SizeSettingContainer(this);
        pageList.add(mSizeSettingContainer);

        mViewPager = (NonSwipeViewPager) findViewById(R.id.setting_pager);
        mViewPager.setAdapter(new ViewPagerAdapter());
        mTabLayout = (TabLayout) findViewById(R.id.setting_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
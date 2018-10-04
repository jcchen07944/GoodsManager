package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.adapter.PurchaseSizeViewPagerAdapter;
import jcchen.goodsmanager.view.fragment.ColorSelectDialogFragment;
import jcchen.goodsmanager.view.fragment.SizeSelectDialogFragment;
import jcchen.goodsmanager.view.listener.OnColorSelectedListener;
import jcchen.goodsmanager.view.listener.OnSizeSelectedListener;

public class PurchaseContainer extends ScrollView implements Container, OnColorSelectedListener, OnSizeSelectedListener {

    private Context context;

    private Button colorSelect, sizeSelect;
    private TextView sizeText, colorText;
    private ViewPager mViewPager;

    private ColorSelectDialogFragment mColorSelectDialogFragment;
    private SizeSelectDialogFragment mSizeSelectDialogFragment;
    private PurchasePresenterImpl presenter;
    private PurchaseSizeViewPagerAdapter mPurchaseSizeViewPagerAdapter;

    private Vector<FrameLayout> pagerList;
    private Vector<ColorInfo> colorSelectList;
    private Vector<SizeInfo> sizeSelectList;

    public PurchaseContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PurchaseContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        colorSelect = (Button) findViewById(R.id.purchase_color);
        colorSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mColorSelectDialogFragment.loadSavedData(colorSelectList);
                mColorSelectDialogFragment.show(((Activity) context).getFragmentManager(), "ColorSelectDialogFragment");
            }
        });

        sizeSelect = (Button) findViewById(R.id.purchase_size_select);
        sizeSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mSizeSelectDialogFragment.show(((Activity) context).getFragmentManager(), "SizeSelectDialogFragment");
            }
        });

        colorText = (TextView) findViewById(R.id.purchase_color_text);

        sizeText = (TextView) findViewById(R.id.purchase_size_text);

        mViewPager = (ViewPager) findViewById(R.id.purchase_pager);
        mViewPager.setAdapter(mPurchaseSizeViewPagerAdapter);

        LinePageIndicator pageIndicator = (LinePageIndicator) findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(mViewPager);
    }

    @Override
    public void init() {
        presenter = new PurchasePresenterImpl();

        // Color select.
        mColorSelectDialogFragment = new ColorSelectDialogFragment();
        mColorSelectDialogFragment.setPresenter(presenter);
        mColorSelectDialogFragment.setListener(this);

        // Size select.
        mSizeSelectDialogFragment = new SizeSelectDialogFragment();
        mSizeSelectDialogFragment.setPresenter(presenter);
        mSizeSelectDialogFragment.setListener(this);

        pagerList = new Vector<>();
        pagerList.add(new PurchaseSizeViewPagerContainer(context));
        pagerList.add(new PurchaseSizeViewPagerContainer(context));
        pagerList.add(new PurchaseSizeViewPagerContainer(context));
        mPurchaseSizeViewPagerAdapter= new PurchaseSizeViewPagerAdapter(context, pagerList);
    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void postResult() {

    }

    @Override
    public void onColorSelected(Vector<ColorInfo> colorSelectList) {
        String text = "";
        for(int i = 0; i < colorSelectList.size(); i++) {
            if(i > 0)
                text = text.concat("/");
            text = text.concat(colorSelectList.get(i).getName());
        }
        if(colorSelectList.size() == 0)
            text = getResources().getString(R.string.none_select);
        colorText.setText(text);
        this.colorSelectList = colorSelectList;
    }

    @Override
    public void onSizeSelected(Vector<SizeInfo> sizeList) {
        String size = "";
        for(int i = 0; i < sizeList.size(); i++)
            size = size.concat("/" + sizeList.get(i).getName());
        sizeText.setText(size);
    }
}

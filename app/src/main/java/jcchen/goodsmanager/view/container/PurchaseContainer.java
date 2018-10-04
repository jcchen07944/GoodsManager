package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.MainActivity;
import jcchen.goodsmanager.view.adapter.SizePurchaseViewPagerAdapter;
import jcchen.goodsmanager.view.fragment.ColorSelectDialogFragment;
import jcchen.goodsmanager.view.fragment.SizeSelectDialogFragment;
import jcchen.goodsmanager.view.listener.OnColorSelectedListener;
import jcchen.goodsmanager.view.listener.OnSizeSelectedListener;

public class PurchaseContainer extends ScrollView implements Container, OnColorSelectedListener, OnSizeSelectedListener {

    private Context context;

    private TypeInfo currentType;

    private ConstraintLayout purchaseBaseLayout;
    private Button colorSelect, sizeSelect, submit;
    private TextView sizeText, colorText;
    private ViewPager mViewPager;

    private ColorSelectDialogFragment mColorSelectDialogFragment;
    private SizeSelectDialogFragment mSizeSelectDialogFragment;
    private PurchasePresenterImpl presenter;
    private SizePurchaseViewPagerAdapter mSizePurchaseViewPagerAdapter;

    private Vector<SizePurchaseViewPagerContainer> pageList;
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

        purchaseBaseLayout = (ConstraintLayout) findViewById(R.id.purchase_base_layout);
        purchaseBaseLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) ((MainActivity) context).getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(((MainActivity) context).getCurrentFocus().getWindowToken(), 0);
            }
        });

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
                mSizeSelectDialogFragment.loadSavedData(sizeSelectList);
                mSizeSelectDialogFragment.show(((Activity) context).getFragmentManager(), "SizeSelectDialogFragment");
            }
        });

        colorText = (TextView) findViewById(R.id.purchase_color_text);

        sizeText = (TextView) findViewById(R.id.purchase_size_text);

        mViewPager = (ViewPager) findViewById(R.id.purchase_pager);
        mViewPager.setAdapter(mSizePurchaseViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                InputMethodManager inputMethodManager = (InputMethodManager) ((MainActivity) context).getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(((MainActivity) context).getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        LinePageIndicator pageIndicator = (LinePageIndicator) findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(mViewPager);

        submit = (Button) findViewById(R.id.purchase_submit);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

        pageList = new Vector<>();
        pageList.add(new SizePurchaseViewPagerContainer(context));
        pageList.add(new SizePurchaseViewPagerContainer(context));
        pageList.add(new SizePurchaseViewPagerContainer(context));
        mSizePurchaseViewPagerAdapter = new SizePurchaseViewPagerAdapter(context, pageList);
    }

    @Override
    public void showItem(Object object) {
        currentType = (TypeInfo) object;
        for (int i = 0; i < pageList.size(); i++)
            pageList.get(i).showItem(object);
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) context).onBackPressed();
        return false;
    }

    @Override
    public void postResult() {

    }

    @Override
    public void onColorSelected(Vector<ColorInfo> colorSelectList) {
        String color = "";
        for (int i = 0; i < colorSelectList.size(); i++) {
            if (i > 0)
                color = color.concat("/");
            color = color.concat(colorSelectList.get(i).getName());
        }
        if (colorSelectList.size() == 0)
            color = getResources().getString(R.string.none_select);
        colorText.setText(color);
        this.colorSelectList = colorSelectList;
    }

    @Override
    public void onSizeSelected(Vector<SizeInfo> sizeSelectList) {
        String size = "";
        for (int i = 0; i < sizeSelectList.size(); i++) {
            if (i > 0)
                size = size.concat("/");
            size = size.concat(sizeSelectList.get(i).getName());
        }
        if (sizeSelectList.size() == 0)
            size = "F";
        sizeText.setText(size);
        this.sizeSelectList = sizeSelectList;
    }
}

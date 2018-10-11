package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
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
    private LinearLayout numbersLayout, nameLayout, priceLayout, incomeTKLayout, flexibleLayout, colorLayout, sizeLayout;
    private Button colorSelect, sizeSelect, submit;
    private TextView sizeText, colorText;
    private ViewPager mViewPager;
    private EditText material, numbers, mall, position, name, listPrice, actualPrice, incomeK, incomeT;
    private LinePageIndicator pageIndicator;
    private Switch flexible;

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

        numbersLayout = (LinearLayout) findViewById(R.id.purchase_numbers_layout);
        numbers = (EditText) findViewById(R.id.purchase_numbers);
        mall = (EditText) findViewById(R.id.purchase_mall);
        position = (EditText) findViewById(R.id.purchase_position);

        nameLayout = (LinearLayout) findViewById(R.id.purchase_name_layout);
        name = (EditText) findViewById(R.id.purchase_name);

        priceLayout = (LinearLayout) findViewById(R.id.purchase_price_layout);
        listPrice = (EditText) findViewById(R.id.purchase_list_price);
        actualPrice = (EditText) findViewById(R.id.purchase_actual_price);

        incomeTKLayout = (LinearLayout) findViewById(R.id.purchase_income_k_t_layout);
        incomeK = (EditText) findViewById(R.id.purchase_income_k);
        incomeT = (EditText) findViewById(R.id.purchase_income_t);

        flexibleLayout = (LinearLayout) findViewById(R.id.flexible_layout);
        flexible = (Switch) findViewById(R.id.flexible_switch);

        colorSelect = (Button) findViewById(R.id.purchase_color);
        colorSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mColorSelectDialogFragment.loadSavedData(colorSelectList);
                mColorSelectDialogFragment.show(((Activity) context).getFragmentManager(), "ColorSelectDialogFragment");
            }
        });

        material = (EditText) findViewById(R.id.purchase_material);

        colorLayout = (LinearLayout) findViewById(R.id.purchase_color_layout);
        colorText = (TextView) findViewById(R.id.purchase_color_text);

        sizeSelect = (Button) findViewById(R.id.purchase_size_select);
        sizeSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mSizeSelectDialogFragment.loadSavedData(sizeSelectList);
                mSizeSelectDialogFragment.show(((Activity) context).getFragmentManager(), "SizeSelectDialogFragment");
            }
        });

        sizeLayout = (LinearLayout) findViewById(R.id.purchase_size_layout);
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
        pageIndicator = (LinePageIndicator) findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(mViewPager);

        submit = (Button) findViewById(R.id.purchase_submit);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage(R.string.confirm_message)
                        .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (checkNecessaryField()) {
                                    presenter.savePurchaseInfo(collectPurchaseInfo());
                                    onBackPressed();
                                }
                            }
                        })
                        .setNegativeButton(R.string.confirm_no, null)
                        .show();

            }
        });
    }

    @Override
    public void init() {
        presenter = new PurchasePresenterImpl(context);

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

    private boolean checkNecessaryField() {
        return true;
    }

    private PurchaseInfo collectPurchaseInfo() {
        PurchaseInfo mPurchaseInfo = new PurchaseInfo();
        mPurchaseInfo.setType(currentType.getType());
        mPurchaseInfo.setNumbers(numbers.getText().toString());
        mPurchaseInfo.setMall(mall.getText().toString());
        mPurchaseInfo.setPosition(position.getText().toString());
        mPurchaseInfo.setName(name.getText().toString());
        mPurchaseInfo.setListPrice(MainActivity.safeParseInt(listPrice.getText().toString()));
        mPurchaseInfo.setActualPrice(MainActivity.safeParseInt(actualPrice.getText().toString()));
        mPurchaseInfo.setIncomeK(MainActivity.safeParseInt(incomeK.getText().toString()));
        mPurchaseInfo.setIncomeT(MainActivity.safeParseInt(incomeT.getText().toString()));
        mPurchaseInfo.setFlexible(flexible.isChecked());
        mPurchaseInfo.setColorList(colorSelectList);
        mPurchaseInfo.setMaterial(material.getText().toString());
        mPurchaseInfo.setSizeList(sizeSelectList);
        mPurchaseInfo.setSizeStructList(mSizePurchaseViewPagerAdapter.collectSizeStruct());
        return mPurchaseInfo;
    }
}

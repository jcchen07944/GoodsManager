package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.fragment.ColorSelectDialogFragment;
import jcchen.goodsmanager.view.fragment.SizeSelectDialogFragment;
import jcchen.goodsmanager.view.listener.OnColorSelectedListener;
import jcchen.goodsmanager.view.listener.OnSizeSelectedListener;

public class PurchaseContainer extends ScrollView implements Container, OnColorSelectedListener, OnSizeSelectedListener {

    private Context context;

    private Button selectColor, selectSize;
    private TextView sizeText;

    private ColorSelectDialogFragment mColorSelectDialogFragment;
    private SizeSelectDialogFragment mSizeSelectDialogFragment;
    private PurchasePresenterImpl presenter;

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

        selectColor = (Button) findViewById(R.id.purchase_color);
        selectColor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mColorSelectDialogFragment.show(((Activity) context).getFragmentManager(), "ColorSelectDialogFragment");
            }
        });

        selectSize = (Button) findViewById(R.id.purchase_size_select);
        selectSize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mSizeSelectDialogFragment.show(((Activity) context).getFragmentManager(), "SizeSelectDialogFragment");
            }
        });
        sizeText = (TextView) findViewById(R.id.purchase_size_text);
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
    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onColorSelected(ColorInfo colorInfo) {
        selectColor.setText(colorInfo.getName());
    }

    @Override
    public void onSizeSelected(Vector<SizeInfo> sizeList) {
        String size = "";
        for(int i = 0; i < sizeList.size(); i++)
            size = size.concat("/" + sizeList.get(i).getName());
        sizeText.setText(size);
    }
}

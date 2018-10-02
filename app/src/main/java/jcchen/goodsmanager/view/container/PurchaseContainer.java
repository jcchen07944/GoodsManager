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

    private Button colorSelect, sizeSelect;
    private TextView sizeText, colorText;

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

        colorSelect = (Button) findViewById(R.id.purchase_color);
        colorSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

    @Override
    public void init() {
        presenter = new PurchasePresenterImpl();

        // Color select.
        mColorSelectDialogFragment = new ColorSelectDialogFragment();
        mColorSelectDialogFragment.setPresenter(presenter);
        mColorSelectDialogFragment.setListener(this);
        mColorSelectDialogFragment.init(context);

        // Size select.
        mSizeSelectDialogFragment = new SizeSelectDialogFragment();
        mSizeSelectDialogFragment.setPresenter(presenter);
        mSizeSelectDialogFragment.setListener(this);
        mSizeSelectDialogFragment.init();
    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onColorSelected(Vector<ColorInfo> selectedColor) {
        String text = "";
        for(int i = 0; i < selectedColor.size(); i++) {
            if(i > 0)
                text = text.concat("/");
            text = text.concat(selectedColor.get(i).getName());
        }
        if(selectedColor.size() == 0)
            text = getResources().getString(R.string.none_select);
        colorText.setText(text);
    }

    @Override
    public void onSizeSelected(Vector<SizeInfo> sizeList) {
        String size = "";
        for(int i = 0; i < sizeList.size(); i++)
            size = size.concat("/" + sizeList.get(i).getName());
        sizeText.setText(size);
    }
}

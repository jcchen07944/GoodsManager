package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import jcchen.goodsmanager.R;

public class PurchaseSizeViewPagerContainer extends FrameLayout implements Container{

    private Context context;

    public PurchaseSizeViewPagerContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PurchaseSizeViewPagerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.purchase_size_viewpager_content, null);
        addView(view);
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
}

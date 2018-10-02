package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class PurchaseSizeViewPagerContainer extends ConstraintLayout implements Container{

    public PurchaseSizeViewPagerContainer(Context context) {
        super(context);
    }

    public PurchaseSizeViewPagerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void init() {

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

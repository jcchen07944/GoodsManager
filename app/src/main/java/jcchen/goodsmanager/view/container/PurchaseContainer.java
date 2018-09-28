package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class PurchaseContainer extends ScrollView implements Container {

    public PurchaseContainer(Context context) {
        super(context);
        init();
    }

    public PurchaseContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
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
}

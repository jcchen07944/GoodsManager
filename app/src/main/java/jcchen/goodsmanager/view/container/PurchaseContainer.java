package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * Created by JCChen on 2018/9/20.
 */

public class PurchaseContainer extends ConstraintLayout implements Container {

    public PurchaseContainer(Context context) {
        super(context);
    }

    public PurchaseContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void init() {

    }

    @Override
    public void showItem(Object type) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}

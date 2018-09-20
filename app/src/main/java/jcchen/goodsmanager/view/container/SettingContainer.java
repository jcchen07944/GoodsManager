package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * Created by JCChen on 2018/9/20.
 */

public class SettingContainer extends ConstraintLayout implements Container {

    public SettingContainer(Context context) {
        super(context);
    }

    public SettingContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
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

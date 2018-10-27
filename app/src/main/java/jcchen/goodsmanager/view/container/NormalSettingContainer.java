package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import jcchen.goodsmanager.R;


public class NormalSettingContainer extends FrameLayout implements Container {

    private Context context;

    public NormalSettingContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NormalSettingContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.normal_setting_layout, null);
        addView(view);
    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void postResult() {

    }
}

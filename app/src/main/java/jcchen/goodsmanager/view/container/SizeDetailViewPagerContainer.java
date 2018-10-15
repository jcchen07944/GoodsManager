package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;

public class SizeDetailViewPagerContainer extends FrameLayout implements Container {

    private Context context;

    public SizeDetailViewPagerContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SizeDetailViewPagerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }


    @Override
    public void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.size_detail_viewpager_content, null);

        addView(view);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public void postResult() {

    }
}

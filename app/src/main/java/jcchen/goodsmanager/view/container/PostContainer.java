package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import jcchen.goodsmanager.view.widget.BottomSheet.BottomSheetRL;

public class PostContainer extends BottomSheetRL implements Container {

    private Context context;

    public PostContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PostContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setPeekHeight((int) (300 * context.getResources().getDisplayMetrics().density));
                show();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void postResult() {

    }
}

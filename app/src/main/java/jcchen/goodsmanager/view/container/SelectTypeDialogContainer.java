package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListViewAdapter;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListView;

/**
 * Created by JCChen on 2018/9/26.
 */

public class SelectTypeDialogContainer extends ConstraintLayout implements Container {

    private FocusListView mFocusListView;
    private Context context;

    private PurchasePresenterImpl presenter;

    public SelectTypeDialogContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SelectTypeDialogContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFocusListView = (FocusListView) this.findViewById(R.id.purchase_type_list);
        final FocusListViewAdapter adapter = new FocusListViewAdapter(context, mFocusListView, presenter.getTypeList());
        mFocusListView.setAdapter(adapter);
        mFocusListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFocusListView.setSelection(adapter.getCount() / 2 - 1);
                mFocusListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mFocusListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for(int i = 0; i < mFocusListView.getChildCount(); i++)
                    mFocusListView.getChildAt(i).invalidate();
            }
        });
        mFocusListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mFocusListView.smoothScroll(mFocusListView.SCROLL_TO_CENTER);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void init() {
        /* New presenter */
        presenter = new PurchasePresenterImpl();
    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}

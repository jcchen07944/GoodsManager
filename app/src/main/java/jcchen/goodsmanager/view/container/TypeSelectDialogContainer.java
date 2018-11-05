package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.MainActivity;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListViewAdapter;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListView;
import jcchen.goodsmanager.view.widget.RoundedImageView;

/**
 * Created by JCChen on 2018/9/26.
 */

public class TypeSelectDialogContainer extends ConstraintLayout implements Container {

    private FocusListView mFocusListView;
    private RoundedImageView mRoundedImageView;
    private Context context;

    FocusListViewAdapter adapter;

    private SettingPresenterImpl mSettingPresenter;

    private TypeInfo defaultType;

    public TypeSelectDialogContainer(Context context) {
        super(context);
        this.context = getActivityFromView(this);
        init();
    }

    public TypeSelectDialogContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = getActivityFromView(this);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // FocusListView
        mFocusListView = (FocusListView) this.findViewById(R.id.purchase_type_list);
        adapter = new FocusListViewAdapter(context, mFocusListView, mSettingPresenter.getTypeList());
        mFocusListView.setAdapter(adapter);
        mFocusListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFocusListView.setSelection(defaultType == null ? adapter.getCount() / 2 : adapter.getItemPosition(defaultType));
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

        // RoundedImageView
        mRoundedImageView = (RoundedImageView) this.findViewById(R.id.confirm_button);
        mRoundedImageView.setClickable(true);
        mRoundedImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int SelectedPosition = (mFocusListView.getLastVisiblePosition() + mFocusListView.getFirstVisiblePosition()) / 2;
                ((MainActivity) context).onPurchaseStart((TypeInfo) adapter.getItem(SelectedPosition));
            }
        });
    }

    @Override
    public void init() {
        /* New presenter */
        mSettingPresenter = new SettingPresenterImpl(context);
        defaultType = null;
    }

    @Override
    public void showItem(Object object) {
        defaultType = (TypeInfo) object;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void postResult() {

    }

    /**
     * try get host activity from view.
     * views hosted on floating window like dialog and toast will sure return null.
     * @return host activity; or null if not available
     */
    public static Context getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


}

package jcchen.goodsmanager.view.widget.BottomSheet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class BottomSheetRL extends RelativeLayout {
    private Context context;

    private BottomSheet mBottomSheet;
    private RelativeLayout mRelativeLayout;
    private RecyclerViewAdapter adapter;

    private int peekHeight;

    public BottomSheetRL(Context context) {
        super(context);
        this.context = context;
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mBottomSheet = new BottomSheet(context);
        addView(mBottomSheet, params);

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.OVER_SCROLL_NEVER);
        mRelativeLayout = new RelativeLayout(context);
        mRelativeLayout.setClipChildren(false);
        addView(mRelativeLayout, params);

        setClipChildren(false);

        peekHeight = -1;
    }

    public BottomSheetRL(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        this.context = context;
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mBottomSheet = new BottomSheet(context);
        addView(mBottomSheet, params);

        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.OVER_SCROLL_NEVER);
        mRelativeLayout = new RelativeLayout(context);
        mRelativeLayout.setClipChildren(false);
        addView(mRelativeLayout, params);

        setClipChildren(false);

        peekHeight = -1;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRelativeLayout.setVisibility(GONE);
    }

    public void show() {
        if(peekHeight > 0) {
            // show bottomSheet
            mBottomSheet.addAnimationListener(new BottomSheet.onAnimationListener() {
                @Override
                public void onShowAnimationStart() {
                    setClipChildren(false);
                }
                @Override
                public void onShowAnimationEnd() {
                    // show content.
                    contentShow();
                }
                @Override
                public void onOverShootAnimationEnd() {
                    setClipChildren(true);
                }
            });
            getLayoutParams().height = peekHeight;
            invalidate();
            mBottomSheet.show();
        }
        else {
            Log.e("BottomSheetRL.show()", "Set PeekHeight before show.");
        }
    }

    public void dismiss() {

    }

    public void setAdapter(RecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public void setPeekHeight(int peekHeight) {
        this.peekHeight = peekHeight;
        mBottomSheet.setPeekHeight(peekHeight);
    }

    private void contentShow() {
        mRelativeLayout.setVisibility(VISIBLE);
    }
}

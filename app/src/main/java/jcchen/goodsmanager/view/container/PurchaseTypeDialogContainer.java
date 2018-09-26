package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ListView;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.adapter.FocusListViewAdapter;
import jcchen.goodsmanager.view.widget.FocusListView.FocusListView;

/**
 * Created by JCChen on 2018/9/26.
 */

public class PurchaseTypeDialogContainer extends ConstraintLayout implements Container {

    private FocusListView mFocusListView;
    private Context context;

    private PurchasePresenterImpl presenter;

    public PurchaseTypeDialogContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PurchaseTypeDialogContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFocusListView = (FocusListView) this.findViewById(R.id.purchase_type_list);
        FocusListViewAdapter adapter = new FocusListViewAdapter(context, mFocusListView, presenter.getTypeList());
        mFocusListView.setAdapter(adapter);
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

package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.adapter.PurchaseRecyclerViewAdapter;

/**
 * Created by JCChen on 2018/9/20.
 */

public class PurchaseContainer extends ConstraintLayout implements Container {

    private Context context;

    private RecyclerView recyclerView;

    private PurchasePresenterImpl purchasePresenter;
    private PurchaseRecyclerViewAdapter adapter;
    private Vector<TypeInfo> typeList;

    public PurchaseContainer(Context context) {
        super(context);
        this.context = context;
    }

    public PurchaseContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    public void init() {
        purchasePresenter = new PurchasePresenterImpl();
        typeList = purchasePresenter.getTypeList();
        adapter = new PurchaseRecyclerViewAdapter(context, typeList);
        recyclerView = (RecyclerView) findViewById(R.id.purchase_page_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

    }

    @Override
    public void showItem(Object type) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


}

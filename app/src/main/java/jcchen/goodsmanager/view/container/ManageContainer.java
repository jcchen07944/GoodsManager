package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.adapter.ManageRecyclerViewAdapter;

public class ManageContainer extends ConstraintLayout implements Container {

    private Context context;

    private RecyclerView mRecyclerView;
    private ManageRecyclerViewAdapter adapter;
    private Vector<PurchaseInfo> purchaseList;

    private PurchasePresenterImpl presenter;

    public ManageContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ManageContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = (RecyclerView) findViewById(R.id.manage_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void init() {
        presenter = new PurchasePresenterImpl(context);
        purchaseList = presenter.getPurchaseList();
        adapter = new ManageRecyclerViewAdapter(context, purchaseList);
    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public void onBackPressed() {
        adapter.resumeCard();
    }

    @Override
    public void postResult() {

    }

    public void removeSelectedCard() {
        int selectedPosition = adapter.getSelectedPosition();
        if (selectedPosition != -1) {
            presenter.removePurchaseInfo(purchaseList.get(selectedPosition));
            adapter.remove(selectedPosition);
        }
    }

    public void refresh() {
        purchaseList = presenter.getPurchaseList();
        adapter = new ManageRecyclerViewAdapter(context, purchaseList);
        mRecyclerView.setAdapter(adapter);
    }
}

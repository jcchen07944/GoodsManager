package jcchen.goodsmanager.view.container;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.adapter.ManageRecyclerViewAdapter;

public class ManageContainer extends ConstraintLayout implements Container {

    private Context context;

    private RecyclerView mRecyclerView;
    private ManageRecyclerViewAdapter adapter;

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
        adapter = new ManageRecyclerViewAdapter(context, presenter.getPurchaseList());
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
            presenter.removePurchaseInfo(adapter.getItem(selectedPosition));
            adapter.remove(selectedPosition);
        }
    }

    public void refresh() {
        adapter = new ManageRecyclerViewAdapter(context, presenter.getPurchaseList());
        mRecyclerView.setAdapter(adapter);
    }

    public ManageRecyclerViewAdapter getAdapter() {
        return adapter;
    }
}

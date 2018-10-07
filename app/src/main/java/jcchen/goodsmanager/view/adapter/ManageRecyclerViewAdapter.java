package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;

public class ManageRecyclerViewAdapter extends RecyclerView.Adapter<ManageRecyclerViewAdapter.ViewHolder> {

    private Context context;

    private Vector<PurchaseInfo> purchaseList;

    public ManageRecyclerViewAdapter(Context context, Vector<PurchaseInfo> purchaseList) {
        this.context = context;
        if(purchaseList == null)
            purchaseList = new Vector<>();
        this.purchaseList = (Vector<PurchaseInfo>) purchaseList.clone();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }
}

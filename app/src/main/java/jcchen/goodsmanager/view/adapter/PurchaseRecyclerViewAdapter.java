package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.MainActivity;

/**
 * Created by JCChen on 2018/9/21.
 */

public class PurchaseRecyclerViewAdapter extends RecyclerView.Adapter<PurchaseRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Vector<TypeInfo> typeList;
    private RecyclerView.LayoutParams params;
    private View LastClickCard;

    public PurchaseRecyclerViewAdapter(Context context, Vector<TypeInfo> typeList) {
        this.context = context;
        this.typeList = typeList;
        int margin_px = (int)(5 * context.getResources().getDisplayMetrics().density);
        int width = ((MainActivity) context).getContentWidthPixel() / 3 - margin_px * 2;
        this.params = new RecyclerView.LayoutParams(width, width / 3 * 2);
        this.params.setMargins(margin_px, margin_px, margin_px, margin_px);
        this.LastClickCard = null;
    }

    @Override
    public PurchaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new PurchaseRecyclerViewAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final PurchaseRecyclerViewAdapter.ViewHolder viewHolder, final int index) {
        viewHolder.purchase_type.setText(typeList.get(index).getType());
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView purchase_type;

        public ViewHolder(View view) {
            super(view);
            view.setLayoutParams(params);
            view.setBackground(context.getDrawable(R.drawable.purchase_cardview_normal));
            ((CardView)view).setCardElevation(1 * context.getResources().getDisplayMetrics().density);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(LastClickCard != null) {
                        ((CardView)LastClickCard).setBackground(context.getDrawable(R.drawable.purchase_cardview_normal));
                        ((CardView)LastClickCard).setCardElevation(1 * context.getResources().getDisplayMetrics().density);
                    }
                    LastClickCard = v;
                    ((CardView)LastClickCard).setBackground(context.getDrawable(R.drawable.purchase_cardview_check));
                    ((CardView)v).setCardElevation(8 * context.getResources().getDisplayMetrics().density);
                }
            });
            purchase_type = (TextView) view.findViewById(R.id.purchase_type);
        }
    }
}

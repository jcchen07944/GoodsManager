package jcchen.goodsmanager.view.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.view.MainActivity;

public class ManageRecyclerViewAdapter extends RecyclerView.Adapter<ManageRecyclerViewAdapter.ViewHolder> {

    private Context context;

    private Vector<PurchaseInfo> purchaseList;

    private ViewHolder selectedCard;

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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.position = position;
        viewHolder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeCard();
            }
        });
        viewHolder.Card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectCard(viewHolder);
                return true;
            }
        });
        viewHolder.Name.setText(purchaseList.get(position).getName());
        viewHolder.Type.setText(purchaseList.get(position).getType());
        viewHolder.Numbers.setText(purchaseList.get(position).getNumbers());
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public PurchaseInfo getItem(int position) {
        return purchaseList.get(position);
    }

    public void remove(int position) {
        purchaseList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public ConstraintLayout Card;
        public TextView Name, Type, Numbers;
        public ViewHolder(View view) {
            super(view);
            Card = view.findViewById(R.id.manage_card);
            Name = view.findViewById(R.id.manage_goods_name);
            Type = view.findViewById(R.id.manage_type);
            Numbers = view.findViewById(R.id.manage_numbers);
        }
    }

    public int getSelectedPosition() {
        if (selectedCard != null)
            return selectedCard.position;
        return -1;
    }

    private void selectCard(ViewHolder viewHolder) {
        resumeCard();
        viewHolder.Card.setElevation(8 * context.getResources().getDisplayMetrics().density);
        //viewHolder.Name.setTextColor(ContextCompat.getColor(context, R.color.colorTextOnBackground));
        viewHolder.Numbers.setBackground(ContextCompat.getDrawable(context, R.color.colorPrimaryLight));
        selectedCard = viewHolder;

        ((MainActivity) context).onCardSelectStart();
    }

    public void resumeCard() {
        if(selectedCard != null) {
            selectedCard.Card.setElevation(1 * context.getResources().getDisplayMetrics().density);
            //selectedCard.Name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            selectedCard.Numbers.setBackground(ContextCompat.getDrawable(context, R.drawable.manage_numbers_border));
            selectedCard = null;

            ((MainActivity) context).onCardSelectEnd();
        }
    }
}

package jcchen.goodsmanager.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.view.MainActivity;
import jcchen.goodsmanager.view.fragment.SizeDetailDialogFragment;

public class ManageRecyclerViewAdapter extends RecyclerView.Adapter<ManageRecyclerViewAdapter.ViewHolder> {

    private Context context;

    private ArrayList<PurchaseInfo> purchaseList;

    private ViewHolder selectedCard, expandedCard;

    private SizeDetailDialogFragment mSizeDetailDialogFragment;

    public ManageRecyclerViewAdapter(Context context, ArrayList<PurchaseInfo> purchaseList) {
        this.context = context;
        if(purchaseList == null)
            purchaseList = new ArrayList<>();
        this.purchaseList = (ArrayList<PurchaseInfo>) purchaseList.clone();
        mSizeDetailDialogFragment = new SizeDetailDialogFragment();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.position = position;
        viewHolder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(viewHolder);
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
        viewHolder.Type.setText(purchaseList.get(position).getTypeInfo().getType());
        viewHolder.Numbers.setText(purchaseList.get(position).getNumbers());
        viewHolder.Mall.setText(purchaseList.get(position).getMall());
        viewHolder.Position.setText(purchaseList.get(position).getPosition());
        viewHolder.Flexible.setText(purchaseList.get(position).getFlexible());
        viewHolder.ActualPrice.setText(purchaseList.get(position).getActualPrice() + "");
        viewHolder.ListPrice.setText(purchaseList.get(position).getListPrice() + "");
        viewHolder.Material.setText(purchaseList.get(position).getMaterial());
        viewHolder.IncomeK.setText(purchaseList.get(position).getIncomeK() + "");
        viewHolder.IncomeT.setText(purchaseList.get(position).getIncomeT() + "");

        ArrayList<ColorInfo> colorList = purchaseList.get(position).getColorList();
        String color = "";
        for (int i = 0; i < colorList.size(); i++) {
            if (i > 0)
                color = color.concat("/");
            color = color.concat(colorList.get(i).getName());
        }
        if (colorList.size() == 0)
            color = context.getResources().getString(R.string.none_select);
        viewHolder.Color.setText(color);

        ArrayList<SizeInfo> sizeList = purchaseList.get(position).getSizeList();
        String size = "";
        for (int i = 0; i < sizeList.size(); i++) {
            if (i > 0)
                size = size.concat("/");
            size = size.concat(sizeList.get(i).getName());
        }
        if (sizeList.size() == 0)
            size = "F";
        viewHolder.Size.setText(size);

        viewHolder.SizeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSizeDetailDialogFragment = new SizeDetailDialogFragment();
                mSizeDetailDialogFragment.setSizeDetail(purchaseList.get(position).getSizeStructList());
                mSizeDetailDialogFragment.setType(purchaseList.get(position).getTypeInfo());
                mSizeDetailDialogFragment.show(((Activity) context).getFragmentManager(), SizeDetailDialogFragment.TAG);
            }
        });
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
        public TextView Name, Type, Numbers, ActualPrice, ListPrice, IncomeK, IncomeT, Material, Flexible, Color, Size, Mall, Position;
        public ImageView Expand;
        public Button SizeDetail;
        public LinearLayout PriceLayout, IncomeLayout, FlexibleLayout, MaterialLayout, ColorLayout, SizeLayout;
        public ViewHolder(View view) {
            super(view);
            Card = view.findViewById(R.id.manage_card);
            Name = view.findViewById(R.id.manage_goods_name);
            Type = view.findViewById(R.id.manage_type);
            Numbers = view.findViewById(R.id.manage_numbers);
            ActualPrice = view.findViewById(R.id.manage_actual_price);
            ListPrice = view.findViewById(R.id.manage_list_price);
            IncomeK = view.findViewById(R.id.manage_income_k);
            IncomeT = view.findViewById(R.id.manage_income_t);
            Material = view.findViewById(R.id.manage_material);
            Flexible = view.findViewById(R.id.manage_flexible);
            Color = view.findViewById(R.id.manage_color);
            Size = view.findViewById(R.id.manage_size);
            Mall = view.findViewById(R.id.manage_mall);
            Position = view.findViewById(R.id.manage_position);
            Expand = view.findViewById(R.id.manage_expand);
            SizeDetail = view.findViewById(R.id.manage_size_detail);
            PriceLayout = view.findViewById(R.id.manage_price_layout);
            IncomeLayout = view.findViewById(R.id.manage_income_layout);
            FlexibleLayout = view.findViewById(R.id.manage_flexible_layout);
            MaterialLayout = view.findViewById(R.id.manage_material_layout);
            ColorLayout = view.findViewById(R.id.manage_color_layout);
            SizeLayout = view.findViewById(R.id.manage_size_layout);
        }
    }

    public int getSelectedPosition() {
        if (selectedCard != null)
            return selectedCard.position;
        return -1;
    }

    private void selectCard(ViewHolder viewHolder) {
        resumeCard();
        contract();
        viewHolder.Card.setElevation(8 * context.getResources().getDisplayMetrics().density);
        //viewHolder.Name.setTextColor(ContextCompat.getColor(context, R.color.colorTextOnBackground));
        viewHolder.Numbers.setBackground(ContextCompat.getDrawable(context, R.color.colorPrimaryLight));
        selectedCard = viewHolder;

        ((MainActivity) context).onCardSelectStart(purchaseList.get(viewHolder.position));
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

    /**
     * Expand to 280dp.
     **/
    private void expand(ViewHolder viewHolder) {
        if (expandedCard == viewHolder) {
            contract();
            return;
        }
        if (selectedCard != null) {
            resumeCard();
            return;
        }
        contract();
        expandedCard = viewHolder;

        expandedCard.Card.getLayoutParams().height = (int) (280 * context.getResources().getDisplayMetrics().density);
        expandedCard.PriceLayout.setVisibility(View.VISIBLE);
        expandedCard.IncomeLayout.setVisibility(View.VISIBLE);
        expandedCard.FlexibleLayout.setVisibility(View.VISIBLE);
        expandedCard.MaterialLayout.setVisibility(View.VISIBLE);
        expandedCard.ColorLayout.setVisibility(View.VISIBLE);
        expandedCard.SizeLayout.setVisibility(View.VISIBLE);
        expandedCard.SizeDetail.setVisibility(View.VISIBLE);
        expandedCard.Card.invalidate();
    }

    /**
     * Contract to 100dp.
     **/
    private void contract() {
        if (expandedCard != null) {
            expandedCard.Card.getLayoutParams().height = (int) (100 * context.getResources().getDisplayMetrics().density);
            expandedCard.PriceLayout.setVisibility(View.GONE);
            expandedCard.IncomeLayout.setVisibility(View.GONE);
            expandedCard.FlexibleLayout.setVisibility(View.GONE);
            expandedCard.MaterialLayout.setVisibility(View.GONE);
            expandedCard.ColorLayout.setVisibility(View.GONE);
            expandedCard.SizeLayout.setVisibility(View.GONE);
            expandedCard.SizeDetail.setVisibility(View.GONE);
            expandedCard.Card.invalidate();
            expandedCard = null;
        }
    }
}

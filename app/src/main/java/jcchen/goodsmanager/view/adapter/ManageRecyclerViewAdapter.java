package jcchen.goodsmanager.view.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.view.activity.MainActivity;
import jcchen.goodsmanager.view.fragment.SizeDetailDialogFragment;

public class ManageRecyclerViewAdapter extends RecyclerView.Adapter<ManageRecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context context;

    private ArrayList<PurchaseInfo> purchaseList, filterList;

    private ViewHolder expandedCard;

    private ArrayList<ViewHolder> selectedCard;

    private ManageFilter mManageFilter;

    private SizeDetailDialogFragment mSizeDetailDialogFragment;

    private boolean filtered;

    public ManageRecyclerViewAdapter(Context context, ArrayList<PurchaseInfo> purchaseList) {
        this.context = context;
        if(purchaseList == null)
            purchaseList = new ArrayList<>();
        this.purchaseList = (ArrayList<PurchaseInfo>) purchaseList.clone();
        this.filterList = new ArrayList<>(purchaseList);
        mSizeDetailDialogFragment = new SizeDetailDialogFragment();
        filtered = false;
        selectedCard = new ArrayList<>();
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
                if (selectedCard.isEmpty())
                    expand(viewHolder);
                else if (selectedCard.contains(viewHolder))
                    resumeCard(viewHolder.position);
                else
                    selectCard(viewHolder);
            }
        });
        viewHolder.Card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (selectedCard.isEmpty()) {
                    selectCard(viewHolder);
                    return true;
                }
                return false;
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
        viewHolder.Upload.setImageResource(purchaseList.get(position).isUpload()?
                R.drawable.ic_upload_ok : R.drawable.ic_upload_no);

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

        float density = context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(100 * density));
        if (position == getItemCount() - 1) {
            params.setMargins((int)(5 * density), (int)(10 * density), (int)(5 * density), (int)(80 * density));
            viewHolder.Card.setLayoutParams(params);
        }
        else {
            params.setMargins((int)(5 * density), (int)(10 * density), (int)(5 * density), 0);
            viewHolder.Card.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if(mManageFilter == null)
            mManageFilter = new ManageFilter();
        return mManageFilter;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public PurchaseInfo getItem(int position) {
        return purchaseList.get(position);
    }

    public void removeSelectedCard() {
        ArrayList<Integer> selectedPosition = getSelectedPosition();
        for (int i = selectedPosition.size() - 1; i >= 0; i--) {
            purchaseList.remove((int) selectedPosition.get(i));
            notifyItemRemoved(selectedPosition.get(i));
        }
        selectedCard.clear();
        notifyItemRangeChanged(0, getItemCount());
    }

    public ArrayList<Integer> getSelectedPosition() {
        ArrayList<Integer> selectedPosition = new ArrayList<>();
        for (int i = 0; i < selectedCard.size(); i++)
            selectedPosition.add(selectedCard.get(i).position);
        Collections.sort(selectedPosition);
        return selectedPosition;
    }

    public void selectCard(ViewHolder viewHolder) {
        if (selectedCard.contains(viewHolder))
            return;
        contract();
        viewHolder.Card.setElevation(8 * context.getResources().getDisplayMetrics().density);
        viewHolder.Numbers.setBackground(ContextCompat.getDrawable(context, R.color.colorPrimaryLight));
        selectedCard.add(viewHolder);

        ArrayList<PurchaseInfo> tmpList = new ArrayList<>();
        for (int i = 0; i < selectedCard.size(); i++)
            tmpList.add(purchaseList.get(selectedCard.get(i).position));
        ((MainActivity) context).onCardSelectStart(tmpList);
    }

    public void resumeCard(int position) {
        ViewHolder viewHolder = null;
        for (int i = 0; i < selectedCard.size(); i++)
            if (selectedCard.get(i).position == position)
                viewHolder = selectedCard.get(i);
        if (viewHolder == null)
            return;
        viewHolder.Card.setElevation(1 * context.getResources().getDisplayMetrics().density);
        viewHolder.Numbers.setBackground(ContextCompat.getDrawable(context, R.drawable.manage_numbers_border));
        selectedCard.remove(viewHolder);

        if (!selectedCard.isEmpty()) {
            ArrayList<PurchaseInfo> tmpList = new ArrayList<>();
            for (int i = 0; i < selectedCard.size(); i++)
                tmpList.add(purchaseList.get(selectedCard.get(i).position));
            ((MainActivity) context).onCardSelectStart(tmpList);
        }
        else
            ((MainActivity) context).onCardSelectEnd();
    }

    /**
     * Expand to 280dp.
     **/
    private void expand(ViewHolder viewHolder) {
        if (expandedCard == viewHolder) {
            contract();
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
        expandedCard.Expand.setRotation(180);
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
            expandedCard.Expand.setRotation(0);
            expandedCard.Card.invalidate();
            expandedCard = null;
        }
    }

    private class ManageFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults mFilterResults = new FilterResults();

            if(constraint != null && constraint.length() > 0) {
                ArrayList<PurchaseInfo> newPurchaseList = new ArrayList<>();
                for(int i = 0; i < filterList.size(); i++) {
                    if(filterList.get(i).getName().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                       filterList.get(i).getNumbers().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        newPurchaseList.add(filterList.get(i));
                    }
                }
                mFilterResults.count = newPurchaseList.size();
                mFilterResults.values = newPurchaseList;
                filtered = true;
            }
            else {
                mFilterResults.count = filterList.size();
                mFilterResults.values = filterList;
                filtered = false;
            }
            return mFilterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            purchaseList = (ArrayList<PurchaseInfo>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public ConstraintLayout Card;
        public TextView Name, Type, Numbers, ActualPrice, ListPrice, IncomeK, IncomeT, Material, Flexible, Color, Size, Mall, Position;
        public ImageView Expand, Upload;
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
            Upload = view.findViewById(R.id.manage_upload);
            SizeDetail = view.findViewById(R.id.manage_size_detail);
            PriceLayout = view.findViewById(R.id.manage_price_layout);
            IncomeLayout = view.findViewById(R.id.manage_income_layout);
            FlexibleLayout = view.findViewById(R.id.manage_flexible_layout);
            MaterialLayout = view.findViewById(R.id.manage_material_layout);
            ColorLayout = view.findViewById(R.id.manage_color_layout);
            SizeLayout = view.findViewById(R.id.manage_size_layout);
        }
    }
}

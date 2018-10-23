package jcchen.goodsmanager.view.container;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.widget.RecyclerHelper.RecyclerHelper;

public class TypeSettingContainer extends FrameLayout implements Container {

    private final int FIRST_CARD = 0;
    private final int DEFAULT_CARD = 1;

    private Context context;

    private RecyclerHelper mRecyclerHelper;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private ArrayList<TypeInfo> typeList;

    private SettingPresenterImpl mSettingPresenter;


    public TypeSettingContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TypeSettingContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    public void init() {
        mSettingPresenter = new SettingPresenterImpl(context);
        typeList = mSettingPresenter.getTypeList();
        typeList.add(0, null); // First Card.

        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerHelper = new RecyclerHelper<TypeInfo>(typeList, (RecyclerView.Adapter) mRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(mRecyclerHelper);
        mRecyclerView = new RecyclerView(context);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation()));
        mRecyclerView.setBackground(ContextCompat.getDrawable(context, R.color.colorAppBackground));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        addView(mRecyclerView);

        mRecyclerHelper.setRecyclerItemDragEnabled(true);
        mRecyclerHelper.addDisablePos(0);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void showItem(Object object) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void postResult() {

    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case FIRST_CARD:
                    return new RecyclerViewAdapter.ViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.setting_add_cardview, parent, false));
                case DEFAULT_CARD:
                    return new RecyclerViewAdapter.ViewHolder(LayoutInflater.from(context)
                            .inflate(R.layout.type_setting_cardview, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder viewHolder, final int position) {
            switch (getItemViewType(position)) {
                case FIRST_CARD:
                    viewHolder.type = FIRST_CARD;
                    break;
                case DEFAULT_CARD:
                    viewHolder.type = DEFAULT_CARD;
                    viewHolder.Name.setText(typeList.get(position).getType());
                    viewHolder.Column1.setText(safeGetText(typeList.get(position).getColumn().get(0)));
                    viewHolder.Column2.setText(safeGetText(typeList.get(position).getColumn().get(1)));
                    viewHolder.Column3.setText(safeGetText(typeList.get(position).getColumn().get(2)));
                    viewHolder.Column4.setText(safeGetText(typeList.get(position).getColumn().get(3)));
                    viewHolder.Column5.setText(safeGetText(typeList.get(position).getColumn().get(4)));
                    viewHolder.Column6.setText(safeGetText(typeList.get(position).getColumn().get(5)));
                    viewHolder.Column7.setText(safeGetText(typeList.get(position).getColumn().get(6)));
                    viewHolder.Column8.setText(safeGetText(typeList.get(position).getColumn().get(7)));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return typeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == 0) ? FIRST_CARD : DEFAULT_CARD;
        }

        private String safeGetText(String text) {
            return text.equals("") ? "(無)" : text;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public int type;
            public TextView Name, Column1, Column2, Column3, Column4, Column5, Column6, Column7, Column8;
            public ViewHolder(View view) {
                super(view);
                Name = (TextView) view.findViewById(R.id.type_setting_name);
                Column1 = (TextView) view.findViewById(R.id.type_setting_column_1);
                Column2 = (TextView) view.findViewById(R.id.type_setting_column_2);
                Column3 = (TextView) view.findViewById(R.id.type_setting_column_3);
                Column4 = (TextView) view.findViewById(R.id.type_setting_column_4);
                Column5 = (TextView) view.findViewById(R.id.type_setting_column_5);
                Column6 = (TextView) view.findViewById(R.id.type_setting_column_6);
                Column7 = (TextView) view.findViewById(R.id.type_setting_column_7);
                Column8 = (TextView) view.findViewById(R.id.type_setting_column_8);
            }
        }
    }
}

package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.fragment.EditSettingDialogFragment;
import jcchen.goodsmanager.view.listener.OnSettingEditListener;
import jcchen.goodsmanager.view.widget.RecyclerHelper.OnSwipeListener;
import jcchen.goodsmanager.view.widget.RecyclerHelper.RecyclerHelper;

public class TypeSettingContainer extends FrameLayout implements Container {

    public static final int FIRST_CARD = 0;
    public static final int DEFAULT_CARD = 1;

    private Context context;

    private RecyclerHelper mRecyclerHelper;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private ArrayList<TypeInfo> typeList;

    private SettingPresenterImpl mSettingPresenter;
    private EditSettingDialogFragment mEditSettingDialogFragment;


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

        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerHelper = new RecyclerHelper<TypeInfo>(typeList, (RecyclerView.Adapter) mRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(mRecyclerHelper);
        mRecyclerView = new RecyclerView(context);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation()));
        mRecyclerView.setBackground(ContextCompat.getDrawable(context, R.color.colorAppBackground));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        addView(mRecyclerView);

        mRecyclerHelper.setRecyclerItemDragEnabled(true).setOnDragListener(new jcchen.goodsmanager.view.widget.RecyclerHelper.OnDragListener() {
            @Override
            public void onDragItemEnd(ArrayList list) {
                typeList = (ArrayList<TypeInfo>) list.clone();
                mSettingPresenter.saveType(typeList);
            }
        });
        mRecyclerHelper.setRecyclerItemSwipeEnabled(true).setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipeItemEnd(final ArrayList list, final RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter, final int position) {
                new android.support.v7.app.AlertDialog.Builder(context)
                        .setMessage(context.getResources().getString(
                                R.string.delete_confirm_message) +
                                " [" + ((TypeInfo) list.get(position)).getType() + "]")
                        .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(position);
                                mAdapter.notifyItemRemoved(position);
                                typeList = (ArrayList<TypeInfo>) list.clone();
                                mSettingPresenter.saveType(typeList);
                            }
                        })
                        .setNegativeButton(R.string.confirm_no, null)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                mAdapter.notifyItemChanged(position);
                            }
                        })
                        .show();
            }
        });
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

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements OnSettingEditListener {

        private int selectedPos;

        RecyclerViewAdapter() {
            typeList.add(0, null); // First Card.
        }

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
                    viewHolder.Add.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedPos = position;
                            mEditSettingDialogFragment = new EditSettingDialogFragment();
                            mEditSettingDialogFragment.setResID(R.layout.type_edit_setting_layout);
                            mEditSettingDialogFragment.setListener(mRecyclerViewAdapter);
                            mEditSettingDialogFragment.show(((Activity) context).getFragmentManager(), EditSettingDialogFragment.TAG);
                        }
                    });
                    break;
                case DEFAULT_CARD:
                    viewHolder.Name.setText(typeList.get(position).getType());
                    viewHolder.Column1.setText(safeGetText(typeList.get(position).getColumn().get(0)));
                    viewHolder.Column2.setText(safeGetText(typeList.get(position).getColumn().get(1)));
                    viewHolder.Column3.setText(safeGetText(typeList.get(position).getColumn().get(2)));
                    viewHolder.Column4.setText(safeGetText(typeList.get(position).getColumn().get(3)));
                    viewHolder.Column5.setText(safeGetText(typeList.get(position).getColumn().get(4)));
                    viewHolder.Column6.setText(safeGetText(typeList.get(position).getColumn().get(5)));
                    viewHolder.Column7.setText(safeGetText(typeList.get(position).getColumn().get(6)));
                    viewHolder.Column8.setText(safeGetText(typeList.get(position).getColumn().get(7)));
                    viewHolder.Edit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedPos = position;
                            mEditSettingDialogFragment = new EditSettingDialogFragment();
                            mEditSettingDialogFragment.setResID(R.layout.type_edit_setting_layout);
                            mEditSettingDialogFragment.setListener(mRecyclerViewAdapter);
                            mEditSettingDialogFragment.show(((Activity) context).getFragmentManager(), EditSettingDialogFragment.TAG);
                        }
                    });
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

        @Override
        public void onEditStart(final View view) {
            switch (getItemViewType(selectedPos)) {
                case FIRST_CARD:
                    ((TextView) view.findViewById(R.id.type_edit_setting_title)).setText(R.string.add);
                    break;
                case DEFAULT_CARD:
                    ((TextView) view.findViewById(R.id.type_edit_setting_title)).setText(R.string.edit);
                    ((EditText) view.findViewById(R.id.type_edit_setting_type)).setText(typeList.get(selectedPos).getType());
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_1)).setText(typeList.get(selectedPos).getColumn().get(0));
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_2)).setText(typeList.get(selectedPos).getColumn().get(1));
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_3)).setText(typeList.get(selectedPos).getColumn().get(2));
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_4)).setText(typeList.get(selectedPos).getColumn().get(3));
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_5)).setText(typeList.get(selectedPos).getColumn().get(4));
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_6)).setText(typeList.get(selectedPos).getColumn().get(5));
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_7)).setText(typeList.get(selectedPos).getColumn().get(6));
                    ((EditText) view.findViewById(R.id.type_edit_setting_column_8)).setText(typeList.get(selectedPos).getColumn().get(7));
                    break;
            }

            view.findViewById(R.id.type_edit_setting_confirm).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditEnd(view);
                    mEditSettingDialogFragment.dismiss();
                }
            });
            view.findViewById(R.id.type_edit_setting_cancel).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditSettingDialogFragment.dismiss();
                }
            });
        }

        @Override
        public void onEditEnd(View view) {

            ArrayList<String> columnList = new ArrayList<>();
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_1)).getText().toString());
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_2)).getText().toString());
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_3)).getText().toString());
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_4)).getText().toString());
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_5)).getText().toString());
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_6)).getText().toString());
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_7)).getText().toString());
            columnList.add(((EditText) view.findViewById(R.id.type_edit_setting_column_8)).getText().toString());
            TypeInfo typeInfo = new TypeInfo(((EditText) view.findViewById(R.id.type_edit_setting_type)).getText().toString(), columnList);

            switch (getItemViewType(selectedPos)) {
                case FIRST_CARD:
                    typeList.add(typeInfo);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                    break;
                case DEFAULT_CARD:
                    typeList.set(selectedPos, typeInfo);
                    mRecyclerViewAdapter.notifyItemChanged(selectedPos);
                    break;
            }
            mSettingPresenter.saveType(typeList);
        }

        private String safeGetText(String text) {
            return text.equals("") ? "(無)" : text;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Name, Column1, Column2, Column3, Column4, Column5, Column6, Column7, Column8;
            public ImageView Add, Edit;
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
                Add = (ImageView) view.findViewById(R.id.setting_card_add);
                Edit = (ImageView) view.findViewById(R.id.type_setting_img);
            }
        }
    }
}

package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
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
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.fragment.EditSettingDialogFragment;
import jcchen.goodsmanager.view.listener.OnSettingEditListener;
import jcchen.goodsmanager.view.widget.RecyclerHelper.OnSwipeListener;
import jcchen.goodsmanager.view.widget.RecyclerHelper.RecyclerHelper;

public class SizeSettingContainer extends FrameLayout implements Container {

    private final int FIRST_CARD = 0;
    private final int DEFAULT_CARD = 1;

    private Context context;

    private RecyclerHelper mRecyclerHelper;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private ArrayList<SizeInfo> sizeList;

    private SettingPresenterImpl mSettingPresenter;
    private EditSettingDialogFragment mEditSettingDialogFragment;

    public SizeSettingContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SizeSettingContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mSettingPresenter.saveSize(sizeList);
    }

    @Override
    public void init() {
        mSettingPresenter = new SettingPresenterImpl(context);
        sizeList = mSettingPresenter.getSizeList();

        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerHelper = new RecyclerHelper<SizeInfo>(sizeList, (RecyclerView.Adapter) mRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(mRecyclerHelper);
        mRecyclerView = new RecyclerView(context);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation()));
        mRecyclerView.setBackground(ContextCompat.getDrawable(context, R.color.colorAppBackground));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        addView(mRecyclerView);

        mRecyclerHelper.setRecyclerItemDragEnabled(true).setOnDragListener(new jcchen.goodsmanager.view.widget.RecyclerHelper.OnDragListener() {
            @Override
            public void onDragItemListener(ArrayList list) {
                sizeList = (ArrayList<SizeInfo>) list.clone();
            }
        });
        mRecyclerHelper.setRecyclerItemSwipeEnabled(true).setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipeItemListener() {

            }

            @Override
            public void onSwipeConfirm(final ArrayList list, final RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter, final int position) {
                new android.support.v7.app.AlertDialog.Builder(context)
                        .setMessage(R.string.delete_confirm_message)
                        .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(position);
                                mAdapter.notifyItemRemoved(position);
                                sizeList = (ArrayList<SizeInfo>) list.clone();
                            }
                        })
                        .setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
            sizeList.add(0, null); // First Card.
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case FIRST_CARD:
                    return new RecyclerViewAdapter.ViewHolder(LayoutInflater.from(context)
                            .inflate(R.layout.setting_add_cardview, parent, false));
                case DEFAULT_CARD:
                    return new RecyclerViewAdapter.ViewHolder(LayoutInflater.from(context)
                            .inflate(R.layout.size_setting_cardview, parent, false));
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
                            mEditSettingDialogFragment.setResID(R.layout.size_edit_setting_layout);
                            mEditSettingDialogFragment.setListener(mRecyclerViewAdapter);
                            mEditSettingDialogFragment.show(((Activity) context).getFragmentManager(), EditSettingDialogFragment.TAG);
                        }
                    });
                    break;
                case DEFAULT_CARD:
                    viewHolder.Name.setText(sizeList.get(position).getName());
                    viewHolder.Code.setText(sizeList.get(position).getCode());
                    viewHolder.Edit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedPos = position;
                            mEditSettingDialogFragment = new EditSettingDialogFragment();
                            mEditSettingDialogFragment.setResID(R.layout.size_edit_setting_layout);
                            mEditSettingDialogFragment.setListener(mRecyclerViewAdapter);
                            mEditSettingDialogFragment.show(((Activity) context).getFragmentManager(), EditSettingDialogFragment.TAG);
                        }
                    });
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return sizeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == 0) ? FIRST_CARD : DEFAULT_CARD;
        }

        @Override
        public void onEditStart(final View view) {
            switch (getItemViewType(selectedPos)) {
                case FIRST_CARD:
                    ((TextView) view.findViewById(R.id.size_edit_setting_title)).setText(R.string.add);
                    break;
                case DEFAULT_CARD:
                    ((TextView) view.findViewById(R.id.size_edit_setting_title)).setText(R.string.edit);
                    ((EditText) view.findViewById(R.id.size_edit_setting_name)).setText(sizeList.get(selectedPos).getName());
                    ((EditText) view.findViewById(R.id.size_edit_setting_code)).setText(sizeList.get(selectedPos).getCode());
                    break;
            }

            view.findViewById(R.id.size_edit_setting_confirm).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditEnd(view);
                    mEditSettingDialogFragment.dismiss();
                }
            });
            view.findViewById(R.id.size_edit_setting_cancel).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditSettingDialogFragment.dismiss();
                }
            });
        }

        @Override
        public void onEditEnd(View view) {

            SizeInfo sizeInfo = new SizeInfo(((EditText) view.findViewById(R.id.size_edit_setting_name)).getText().toString(),
                    ((EditText) view.findViewById(R.id.size_edit_setting_code)).getText().toString());

            switch (getItemViewType(selectedPos)) {
                case FIRST_CARD:
                    sizeList.add(sizeInfo);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                    break;
                case DEFAULT_CARD:
                    sizeList.set(selectedPos, sizeInfo);
                    mRecyclerViewAdapter.notifyItemChanged(selectedPos);
                    break;
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Name, Code;
            public ImageView Add, Edit;
            public ViewHolder(View view) {
                super(view);
                Name = (TextView) view.findViewById(R.id.size_setting_name);
                Code = (TextView) view.findViewById(R.id.size_setting_code);
                Add = (ImageView) view.findViewById(R.id.setting_card_add);
                Edit = (ImageView) view.findViewById(R.id.size_setting_img);
            }
        }
    }
}

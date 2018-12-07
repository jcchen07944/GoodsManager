package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PostBlock;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.fragment.EditSettingDialogFragment;
import jcchen.goodsmanager.view.listener.OnSettingEditListener;
import jcchen.goodsmanager.view.widget.RecyclerHelper.OnSwipeListener;
import jcchen.goodsmanager.view.widget.RecyclerHelper.RecyclerHelper;

public class PostSettingContainer extends FrameLayout implements Container {

    private final int DEFAULT_CARD = 0;
    private final int CUSTOMIZED_CARD = 1;

    private Context context;

    private SettingPresenterImpl mSettingPresenter;

    private EditSettingDialogFragment mEditSettingDialogFragment;

    private RecyclerHelper mRecyclerHelper;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private FloatingActionButton mFloatingActionButton;

    private ArrayList<PostBlock> postList;

    private int selectedPos;

    public PostSettingContainer(Context context) {
        super(context);
        this.context = context;
        mSettingPresenter = new SettingPresenterImpl(context);

        init();
    }

    public PostSettingContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        mSettingPresenter = new SettingPresenterImpl(context);

        init();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void init() {
        final View view = LayoutInflater.from(context).inflate(R.layout.post_setting_layout, null);
        postList = mSettingPresenter.getPostList();

        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerHelper = new RecyclerHelper<PostBlock>(postList, (RecyclerView.Adapter) mRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(mRecyclerHelper);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.post_setting_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation()));
        mRecyclerView.setBackground(ContextCompat.getDrawable(context, R.color.colorAppBackground));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerHelper.setRecyclerItemDragEnabled(true).setOnDragListener(new jcchen.goodsmanager.view.widget.RecyclerHelper.OnDragListener() {
            @Override
            public void onDragItemEnd() {
                mSettingPresenter.savePostList(postList);
            }
        });
        mRecyclerHelper.setRecyclerItemSwipeEnabled(true).setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipeItemEnd(final int position) {
                new androidx.appcompat.app.AlertDialog.Builder(context)
                        .setMessage(context.getResources().getString(R.string.delete_confirm_message))
                        .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                postList.remove(position);
                                mRecyclerViewAdapter.notifyItemRemoved(position);
                                mSettingPresenter.savePostList(postList);
                            }
                        })
                        .setNegativeButton(R.string.confirm_no, null)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                mRecyclerViewAdapter.notifyItemChanged(position);
                            }
                        })
                        .show();
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = -1;
                mEditSettingDialogFragment = new EditSettingDialogFragment();
                mEditSettingDialogFragment.setResID(R.layout.post_edit_setting_layout);
                mEditSettingDialogFragment.setListener(mRecyclerViewAdapter);
                mEditSettingDialogFragment.show(((Activity) context).getFragmentManager(), EditSettingDialogFragment.TAG);
            }
        });

        addView(view);
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

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewAdapter.ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.post_setting_cardview, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder viewHolder, final int position) {
            switch (getItemViewType(position)) {
                case CUSTOMIZED_CARD:
                    viewHolder.Text.setText(postList.get(position).getContent());
                    viewHolder.Edit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectedPos = viewHolder.getAdapterPosition();
                            mEditSettingDialogFragment = new EditSettingDialogFragment();
                            mEditSettingDialogFragment.setResID(R.layout.post_edit_setting_layout);
                            mEditSettingDialogFragment.setListener(mRecyclerViewAdapter);
                            mEditSettingDialogFragment.show(((Activity) context).getFragmentManager(), EditSettingDialogFragment.TAG);
                        }
                    });
                    break;
                case DEFAULT_CARD:
                    viewHolder.Text.setText("商品資訊");
                    viewHolder.Edit.setVisibility(GONE);
                    break;
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (position == getItemCount() - 1) {
                params.setMargins(0, 0, 0, (int)(80 * context.getResources().getDisplayMetrics().density));
                viewHolder.BaseLayout.setLayoutParams(params);
            }
            else {
                params.setMargins(0, 0, 0, 0);
                viewHolder.BaseLayout.setLayoutParams(params);
            }
        }

        @Override
        public int getItemCount() {
            return postList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return postList.get(position).isDefault() ? DEFAULT_CARD : CUSTOMIZED_CARD;
        }

        @Override
        public void onEditStart(View view) {
            if (selectedPos == -1) {
                ((TextView) view.findViewById(R.id.post_edit_setting_title)).setText(R.string.add);
            }
            else {
                ((TextView) view.findViewById(R.id.post_edit_setting_title)).setText(R.string.edit);
                ((EditText) view.findViewById(R.id.post_edit_setting_text)).setText(postList.get(selectedPos).getContent());
            }

            view.findViewById(R.id.post_edit_setting_confirm).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditEnd(view);
                    mEditSettingDialogFragment.dismiss();
                }
            });
            view.findViewById(R.id.post_edit_setting_cancel).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditSettingDialogFragment.dismiss();
                }
            });
        }

        @Override
        public void onEditEnd(View view) {
            PostBlock postBlock = new PostBlock(false, ((EditText) view.findViewById(R.id.post_edit_setting_text)).getText().toString());

            if (selectedPos == -1) {
                postList.add(postBlock);
            }
            else {
                postList.set(selectedPos, postBlock);
            }

            mRecyclerViewAdapter.notifyDataSetChanged();
            mSettingPresenter.savePostList(postList);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public CardView BaseLayout;
            public TextView Text;
            public ImageView Edit;
            public ViewHolder(View view) {
                super(view);
                BaseLayout = (CardView) view.findViewById(R.id.post_setting_cardview_base);
                Text = (TextView) view.findViewById(R.id.post_setting_text);
                Edit = (ImageView) view.findViewById(R.id.post_setting_edit);
            }
        }
    }
}

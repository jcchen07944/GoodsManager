package jcchen.goodsmanager.view.widget.RecyclerHelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RecyclerHelper<T> extends Callback {

    private OnDragListener onDragListener;
    private OnSwipeListener onSwipeListener;
    private boolean isItemDragEnabled;
    private boolean isItemSwipeEnabled;
    private ArrayList<T> list;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    private ArrayList<Integer> disablePosList;

    public OnDragListener getOnDragListener() {
        return this.onDragListener;
    }

    public OnSwipeListener getOnSwipeListener() {
        return this.onSwipeListener;
    }

    public void onMoved(int fromPosition, int toPosition) {
        if (disablePosList != null) {
            for (int i = 0; i < disablePosList.size(); i++) {
                if (fromPosition == disablePosList.get(i) || toPosition == disablePosList.get(i)) {
                    this.mAdapter.notifyItemChanged(toPosition);
                    return;
                }
            }
        }
        if (this.onSwipeListener != null) {
            onSwipeListener.onSwipeConfirm(list, mAdapter, toPosition);
            onSwipeListener.onSwipeItemListener();
        }
    }

    public void onItemMoved(int fromPosition, int toPosition) {
        if (disablePosList != null) {
            for (int i = 0; i < disablePosList.size(); i++) {
                if (fromPosition == disablePosList.get(i) || toPosition == disablePosList.get(i))
                    return;
            }
        }
        Collections.swap((List)this.list, fromPosition, toPosition);
        this.mAdapter.notifyItemMoved(fromPosition, toPosition);
        if (onDragListener != null) {
            onDragListener.onDragItemListener(list);
        }
    }

    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        if (this.isItemDragEnabled) {
            dragFlags = 3;
        }

        if (this.isItemSwipeEnabled) {
            swipeFlags = 12;
        }

        return Callback.makeMovementFlags(dragFlags, swipeFlags);
    }

    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        if (viewHolder == null) {
            return false;
        }

        if (target == null) {
            return false;
        }

        this.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    public void onSwiped(ViewHolder viewHolder, int direction) {
        if (viewHolder == null) {
            return;
        }

        this.onMoved(viewHolder.getOldPosition(), viewHolder.getAdapterPosition());
    }

    public boolean isLongPressDragEnabled() {
        return true;
    }

    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    public void addDisablePos(int position) {
        if (disablePosList == null)
            disablePosList = new ArrayList<>();
        disablePosList.add(position);
    }

    public RecyclerHelper setRecyclerItemDragEnabled(boolean isDragEnabled) {
        this.isItemDragEnabled = isDragEnabled;
        return this;
    }

    public RecyclerHelper setRecyclerItemSwipeEnabled(boolean isSwipeEnabled) {
        this.isItemSwipeEnabled = isSwipeEnabled;
        return this;
    }

    public RecyclerHelper setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
        return this;
    }

    public RecyclerHelper setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
        return this;
    }

    public ArrayList<T> getList() {
        return this.list;
    }

    public void setList(ArrayList<T> var1) {
        this.list = var1;
    }

    public Adapter getMAdapter() {
        return this.mAdapter;
    }

    public void setMAdapter(Adapter var1) {
        this.mAdapter = var1;
    }

    public RecyclerHelper( ArrayList<T> list, RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter) {
        this.list = list;
        this.mAdapter = mAdapter;
    }
}

package jcchen.goodsmanager.view.widget.RecyclerHelper;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public interface OnSwipeListener {
    void onSwipeItemListener();
    void onSwipeConfirm(ArrayList list, RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter, int position);
}

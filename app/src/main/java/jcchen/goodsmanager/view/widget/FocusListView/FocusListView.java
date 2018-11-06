package jcchen.goodsmanager.view.widget.FocusListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;

/**
 * Created by JCChen on 2018/9/26.
 */

public class FocusListView extends ListView {

    public final int SCROLL_TO_CENTER = -1;

    public FocusListView(Context context) {
        super(context);
    }

    public FocusListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        try {
            // Disable fling of listView
            Field f_vScale = AbsListView.class.getDeclaredField("mVelocityScale");
            f_vScale.setAccessible(true);
            f_vScale.set(this, 0f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set the listView to the certain position.
     *
     * @param position => Absolute position in listView. Not index of visible children.
     */
    @Override
    public void setSelection(int position) {
        setSelectionFromTop(position, getHeight() / 2 - getChildAt(0).getHeight() / 2);
    }

    /**
     * Smooth scroll to certain item.
     * If itemId equals SCROLL_TO_CENTER, just scroll to nearest item at the mid.
     *
     * @param itemId
     */
    public void smoothScroll(int itemId) {
        if(itemId == SCROLL_TO_CENTER) {
            int center_index = -1;  //  Child index (0 ~ ChildCount)
            float last_pos = Float.MAX_VALUE;
            for (int i = 0; i < getChildCount(); i++) {
                float pos = 1f - ((FocusListViewContent) getChildAt(i)).getPosRate();
                if (pos < last_pos)
                    last_pos = pos;
                else {
                    center_index = i - 1;
                    break;
                }
            }
            float scroll_offset = (1f - ((FocusListViewContent) getChildAt(center_index)).getPosRate()) * (float) getHeight() / 2f;
            if (((FocusListViewContent)(getChildAt(center_index))).getMid() < getHeight() / 2)
                smoothScrollBy(-(int) scroll_offset, 200);
            else
                smoothScrollBy((int) scroll_offset, 200);
        }
        else {
            int contentHeight = getChildAt(0).getHeight();
            int id_select = (int) getAdapter().getItemId(getFirstVisiblePosition() + getChildCount() / 2);
            int count = absMin(absMin(itemId - id_select - ((FocusListViewAdapter)getAdapter()).getListSize(),
                    itemId - id_select + ((FocusListViewAdapter)getAdapter()).getListSize()), itemId - id_select);
            smoothScrollBy(count * contentHeight - (getHeight() / 2 - ((FocusListViewContent)getChildAt(getChildCount() / 2)).getMid()), 200);
        }
    }

    /**
     * Compare two numbers a and b. If absolute value of a is bigger than absolute value of b,
     * then return original b, otherwise, return a.
     *
     * @param a
     * @param b
     * @return Return original a(or b).
     */
    private int absMin(int a, int b) {
        if(Math.abs(a) < Math.abs(b))
            return a;
        else
            return b;
    }

    public void setTextSize(float size) {
        ((FocusListViewAdapter) getAdapter()).textSize = size;
    }

    public static class FocusListViewAdapter<T> extends BaseAdapter {

        private final int count = 10000;  // Unlimited content.

        private Context context;
        private ListView mListView;
        private ArrayList<T> list;

        public float textSize = -1;

        public FocusListViewAdapter(Context context, ListView listView, ArrayList<T> list) {
            this.context = context;
            this.mListView = listView;
            this.list = list;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return list.get((int) getItemId(position));
        }

        @Override
        public long getItemId(int position) {
            if(position >= count / 2)
                return (position - count / 2) % list.size();
            else
                return Math.abs((count / 2 - position - 1) % list.size() - list.size() + 1);
        }

        public int getListSize() {
            return list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.focus_listview_content, null);
            }
            ((FocusListViewContent) convertView).setParentHeight(mListView.getHeight());
            TextView mTextView = (TextView) convertView.findViewById(R.id.focus_listview_text);
            if (textSize != -1)
                mTextView.setTextSize(textSize);
            mTextView.setText(getItem(position).toString());
            return convertView;
        }
    }

}

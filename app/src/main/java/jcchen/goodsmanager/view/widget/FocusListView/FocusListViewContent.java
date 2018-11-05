package jcchen.goodsmanager.view.widget.FocusListView;

import android.content.Context;
import android.graphics.Canvas;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;

/**
 * Created by JCChen on 2018/9/26.
 */

public class FocusListViewContent extends ConstraintLayout {

    private final float SCALE_MAX = 1.5f;

    private float parentHeight = -1;

    public FocusListViewContent(Context context) {
        super(context);
    }

    public FocusListViewContent(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        float scale = getScale();
        canvas.save();
        {
            canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
            super.dispatchDraw(canvas);
        }
        canvas.restore();
    }

    public void setParentHeight(float parentHeight) {
        this.parentHeight = parentHeight;
    }

    public int getMid() {
        return (getTop() + getBottom()) / 2;
    }

    private float getScale() {
        return Math.max(getPosRate(), 0.5f) * SCALE_MAX;
    }


    public float getPosRate() {
        if(getMid() < parentHeight / 2f)
            return (float) getMid() / (parentHeight / 2f);
        else
            return (parentHeight - (float) getMid()) / (parentHeight / 2f);
    }
}

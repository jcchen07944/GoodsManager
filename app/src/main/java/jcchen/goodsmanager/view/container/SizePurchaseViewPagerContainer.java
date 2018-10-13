package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.TypeInfo;
import jcchen.goodsmanager.view.MainActivity;

public class SizePurchaseViewPagerContainer extends FrameLayout implements Container{

    private Context context;

    private TypeInfo currentType;

    private ConstraintLayout purchaseViewPagerLayout;
    private EditText[] sizeEditText;

    public SizePurchaseViewPagerContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SizePurchaseViewPagerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    public void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.purchase_size_viewpager_content, null);

        sizeEditText = new EditText[8];
        sizeEditText[0] = (EditText) view.findViewById(R.id.purchase_size_1);
        sizeEditText[1] = (EditText) view.findViewById(R.id.purchase_size_2);
        sizeEditText[2] = (EditText) view.findViewById(R.id.purchase_size_3);
        sizeEditText[3] = (EditText) view.findViewById(R.id.purchase_size_4);
        sizeEditText[4] = (EditText) view.findViewById(R.id.purchase_size_5);
        sizeEditText[5] = (EditText) view.findViewById(R.id.purchase_size_6);
        sizeEditText[6] = (EditText) view.findViewById(R.id.purchase_size_7);
        sizeEditText[7] = (EditText) view.findViewById(R.id.purchase_size_8);

        purchaseViewPagerLayout = (ConstraintLayout) view.findViewById(R.id.purchase_viewpager_layout);
        purchaseViewPagerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) ((MainActivity) context).getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(((MainActivity) context).getCurrentFocus().getWindowToken(), 0);
            }
        });

        addView(view);
    }

    @Override
    public void showItem(Object object) {
        currentType = (TypeInfo) object;
        Vector<String> column = currentType.getColumn();
        for(int i = 0; i < column.size(); i++)
            sizeEditText[i].setHint(column.get(i));
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void postResult() {

    }
}

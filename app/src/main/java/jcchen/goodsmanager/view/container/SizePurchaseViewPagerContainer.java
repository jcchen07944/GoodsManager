package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PurchaseInfo;
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

        for(int i = 0; i < 8; i++) {
            final int index = i;
            sizeEditText[index].setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    sizeEditText[index].setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            });
            sizeEditText[index].setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    sizeEditText[index].setInputType(InputType.TYPE_CLASS_TEXT);
                    return true;
                }
            });
            sizeEditText[index].setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                        if (index == 7)
                            findViewById(R.id.purchase_note).requestFocus();
                        else
                            sizeEditText[index+1].requestFocus();
                        return true;
                    }
                    return false;
                }
            });
        }

        purchaseViewPagerLayout = (ConstraintLayout) view.findViewById(R.id.purchase_viewpager_layout);
        purchaseViewPagerLayout.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) ((MainActivity) context).getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(((MainActivity) context).getCurrentFocus().getWindowToken(), 0);
                }
            }
        });

        addView(view);
    }

    @Override
    public void showItem(Object object) {
        if (object instanceof TypeInfo) {
            currentType = (TypeInfo) object;
            ArrayList<String> column = currentType.getColumn();
            for (int i = 0; i < column.size(); i++)
                sizeEditText[i].setHint(column.get(i));
        }
        if (object instanceof PurchaseInfo.SizeStruct) {
            PurchaseInfo.SizeStruct sizeStruct = (PurchaseInfo.SizeStruct) object;
            ((EditText) findViewById(R.id.content_size_text)).setText(sizeStruct.getSizeName());
            sizeEditText[0].setText(sizeStruct.getColumn0());
            sizeEditText[1].setText(sizeStruct.getColumn1());
            sizeEditText[2].setText(sizeStruct.getColumn2());
            sizeEditText[3].setText(sizeStruct.getColumn3());
            sizeEditText[4].setText(sizeStruct.getColumn4());
            sizeEditText[5].setText(sizeStruct.getColumn5());
            sizeEditText[6].setText(sizeStruct.getColumn6());
            sizeEditText[7].setText(sizeStruct.getColumn7());
            ((EditText) findViewById(R.id.purchase_append)).setText(sizeStruct.getAppend());
            ((EditText) findViewById(R.id.purchase_note)).setText(sizeStruct.getNote());
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void postResult() {

    }
}

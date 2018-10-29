package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;


public class NormalSettingContainer extends FrameLayout implements Container {

    private Context context;

    private LinearLayout reset;

    private SettingPresenterImpl mSettingPresenter;

    public NormalSettingContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NormalSettingContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void init() {
        mSettingPresenter = new SettingPresenterImpl(context);

        final View view = LayoutInflater.from(context).inflate(R.layout.normal_setting_layout, null);

        reset = (LinearLayout) view.findViewById(R.id.normal_setting_reset);
        reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage(R.string.setting_reset_message)
                        .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSettingPresenter.resetSetting();
                                Intent intent = ((Activity) context).getIntent();
                                ((Activity) context).finish();
                                ((Activity) context).startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.confirm_no, null)
                        .show();
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
}

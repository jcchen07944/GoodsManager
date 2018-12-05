package jcchen.goodsmanager.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.SettingProfile;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;


public class NormalSettingContainer extends FrameLayout implements Container {

    private Context context;

    private LinearLayout reset, time;
    private TextView timeHint;
    private EditText ipAddress, port;

    private SettingPresenterImpl mSettingPresenter;

    private SettingProfile mSettingProfile;

    public NormalSettingContainer(Context context) {
        super(context);
        this.context = context;
        mSettingPresenter = new SettingPresenterImpl(context);

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
        final View view = LayoutInflater.from(context).inflate(R.layout.normal_setting_layout, null);

        mSettingProfile = mSettingPresenter.getProfile();

        ipAddress = (EditText) view.findViewById(R.id.normal_setting_ip);
        ipAddress.clearFocus();
        port = (EditText) view.findViewById(R.id.normal_setting_port);
        port.clearFocus();


        timeHint = (TextView) view.findViewById(R.id.normal_setting_time_text);
        switch (mSettingProfile.getTimeDialogShowFreq()) {
            case SettingProfile.TIME_DIALOG_MODE_EVERYDAY:
                timeHint.setText(context.getResources().getString(R.string.setting_every_time));
                break;
            case SettingProfile.TIME_DIALOG_MODE_ONCE:
                timeHint.setText(context.getResources().getString(R.string.setting_once_a_day));
                break;
        }

        time = (LinearLayout) view.findViewById(R.id.normal_setting_time_layout) ;
        time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mSettingProfile.getTimeDialogShowFreq()) {
                    case SettingProfile.TIME_DIALOG_MODE_EVERYDAY:
                        mSettingProfile.setTimeDialogShowFreq(SettingProfile.TIME_DIALOG_MODE_ONCE);
                        timeHint.setText(context.getResources().getString(R.string.setting_once_a_day));
                        break;
                    case SettingProfile.TIME_DIALOG_MODE_ONCE:
                        mSettingProfile.setTimeDialogShowFreq(SettingProfile.TIME_DIALOG_MODE_EVERYDAY);
                        timeHint.setText(context.getResources().getString(R.string.setting_every_time));
                        break;
                }
                mSettingPresenter.saveProfile(mSettingProfile);
            }
        });

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

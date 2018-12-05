package jcchen.goodsmanager.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.DialogFragment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.core.content.ContextCompat;
import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.DateInfo;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.activity.MainActivity;

public class DateSelectDialogFragment extends DialogFragment {

    public static final String TAG = "DateSelectDialogFragment";

    private final int MODE_YEAR = 0;
    private final int MODE_MONTH = 1;
    private final int MODE_DAY = 2;

    private Context context;

    private SettingPresenterImpl mSettingPresenter;

    private DateInfo mDateInfo;

    private ImageView increase, decrease, confirm;
    private TextView day, date;
    private LinearLayout dateLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.date_select_dialog_layout, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSettingPresenter = new SettingPresenterImpl(context);
        mDateInfo = mSettingPresenter.getDate();

        increase = (ImageView) view.findViewById(R.id.day_increase);
        decrease = (ImageView) view.findViewById(R.id.day_decrease);
        confirm = (ImageView) view.findViewById(R.id.confirm_button);
        day = (TextView) view.findViewById(R.id.day_text);
        date = (TextView) view.findViewById(R.id.date_text);
        dateLayout = (LinearLayout) view.findViewById(R.id.date_layout);

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        if (mDateInfo == null) {
            day.setText("1");
            date.setText(mSimpleDateFormat.format(Calendar.getInstance().getTime()));
        }
        else {
            day.setText(mDateInfo.getDay() + "");
            date.setText(isCancelable()? mDateInfo.getDate() : mSimpleDateFormat.format(Calendar.getInstance().getTime()));
        }

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog mDatePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(year + "年" + String.format("%02d", monthOfYear + 1) + "月" + String.format("%02d", dayOfMonth) + "日");
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                        //mDateInfo == null ? now.get(Calendar.YEAR) : parseDate(MODE_YEAR, mDateInfo.getDate()),
                        //mDateInfo == null ? now.get(Calendar.MONTH) : parseDate(MODE_MONTH, mDateInfo.getDate()) - 1,
                        //mDateInfo == null ? now.get(Calendar.DAY_OF_MONTH) : parseDate(MODE_DAY, mDateInfo.getDate())
                );
                mDatePickerDialog.setAccentColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                mDatePickerDialog.show(((MainActivity) context).getSupportFragmentManager(), "DatePickerDialog");
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(day.getText().toString()) < 9)
                    day.setText((Integer.parseInt(day.getText().toString()) + 1) + "");

            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(day.getText().toString()) > 1)
                    day.setText((Integer.parseInt(day.getText().toString()) - 1) + "");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSettingPresenter.saveDate(new DateInfo(date.getText().toString(), Integer.parseInt(day.getText().toString())));
                ((MainActivity) context).onDateSet();
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        context = activity;
        super.onAttach(activity);
    }

    private int parseDate(int Mode, String Date) {
        switch (Mode) {
            case MODE_YEAR:
                return Integer.parseInt(Date.split("年")[0]);
            case MODE_MONTH:
                return Integer.parseInt(Date.split("年")[1].split("月")[0]);
            case MODE_DAY:
                return Integer.parseInt(Date.split("年")[1].split("月")[1].split("日")[0]);
        }
        return 0;
    }
}

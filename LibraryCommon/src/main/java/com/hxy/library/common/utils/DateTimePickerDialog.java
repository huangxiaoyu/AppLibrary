package com.hxy.library.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.hxy.app.librarycore.R;

/**
 * 2018/12/3 17:01
 * <p>
 * huangxiaoyu
 * <p>
 * desc
 */
public class DateTimePickerDialog extends AlertDialog {
    DatePicker mDatePicker;
    TimePicker mTimePicker;
    OnTimeSelect mOnTimeSelect;
    boolean showTime = true;

    public DateTimePickerDialog(Context context, OnTimeSelect onTimeSelect) {
        super(context, AlertDialog.THEME_HOLO_LIGHT);
        mOnTimeSelect = onTimeSelect;
    }

    public DateTimePickerDialog(Context context, boolean showTime, OnTimeSelect onTimeSelect) {
        super(context, AlertDialog.THEME_HOLO_LIGHT);
        mOnTimeSelect = onTimeSelect;
        this.showTime = showTime;
    }

    protected DateTimePickerDialog(Context context, boolean cancelable, OnCancelListener
            cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected DateTimePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_date_time_picker_dialog);
        mDatePicker = findViewById(R.id.dpDate);
        mTimePicker = findViewById(R.id.tpTime);
        if (!showTime) {
            mTimePicker.setVisibility(View.GONE);
        }
        mTimePicker.setIs24HourView(true);
        findViewById(R.id.btnCancel).setOnClickListener(v -> {
            dismiss();
        });
        findViewById(R.id.btnCommit).setOnClickListener(v -> {
            String dateStr = "";
            String timeStr = "";

            if (mOnTimeSelect != null) {
                dateStr = mDatePicker.getYear() + "-" + (mDatePicker.getMonth() + 1 < 10 ? "0" +
                        (mDatePicker.getMonth() + 1) : (mDatePicker.getMonth() + 1)) + "-" +
                        (mDatePicker.getDayOfMonth() < 10 ? "0" + mDatePicker.getDayOfMonth() :
                                mDatePicker.getDayOfMonth());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timeStr = (mTimePicker.getHour() < 10 ? "0" + mTimePicker.getHour() :
                            mTimePicker.getHour()) + ":" + (mTimePicker.getMinute() < 10 ? "0" +
                            mTimePicker.getMinute() : mTimePicker.getMinute());
                } else {
                    timeStr = (mTimePicker.getCurrentHour() < 10 ? "0" + mTimePicker
                            .getCurrentHour() : mTimePicker.getCurrentHour()) + ":" +
                            (mTimePicker.getCurrentMinute() < 10 ? "0" + mTimePicker
                                    .getCurrentMinute() : mTimePicker.getCurrentMinute());
                }
                mOnTimeSelect.onTimeSelect(showTime ? dateStr + " " + timeStr : dateStr);
            }
            dismiss();
        });
    }

    public interface OnTimeSelect {
        void onTimeSelect(String datetime);
    }
}

package com.hxy.library.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.hxy.app.librarycore.R;

/**
 * huangxiaoyu
 * 2020/5/27 0027 08:49
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        this(context, R.style.dialog_loadingStyle);
    }
    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }
}

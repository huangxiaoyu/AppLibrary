package com.hxy.library.common.utils;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by huangxiaoyu on 2016/11/19.
 */

public class SweetAlertDialogFactory {
    public static SweetAlertDialog build(Context context, int alertType, boolean cancelable) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, alertType).setTitleText("")
                .setConfirmText("确定");
        dialog.setCancelable(cancelable);
        dialog.setCancelClickListener(alertDialog->alertDialog.dismiss());
        return dialog;
    }

    public static SweetAlertDialog build(Context context, int alertType) {
        return build(context, alertType, true);
    }

    public static SweetAlertDialog build(Context context) {
        return build(context, SweetAlertDialog.NORMAL_TYPE);
    }

    public abstract class OnSweetClickListener implements SweetAlertDialog.OnSweetClickListener {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismiss();
        }
    }
}

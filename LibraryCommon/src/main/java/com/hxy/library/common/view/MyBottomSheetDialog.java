package com.hxy.library.common.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hxy.app.librarycore.R;
import com.hxy.library.common.utils.recycleviewdivider.HorizontalDividerItemDecoration;


/**
 * Created by huangxiaoyu on 2016/11/14.
 * 选择部门
 */

public class MyBottomSheetDialog extends BottomSheetDialog {
    RecyclerView rvList;
    Context mContext;

    public MyBottomSheetDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        int screenHeight = getScreenHeight(getContext());
        int statusBarHeight = getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ?
                ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);


    }

    private static int getScreenHeight(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private void init() {
        //        mBinding = DataBindingUtil.setContentView(getOwnerActivity(), R.layout
        //                .view_sheet_dialog_select_company);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_my_bottom_sheet_dialog, null);
        rvList = view.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
        view.findViewById(R.id.ivClose).setOnClickListener(v -> dismiss());
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        rvList.setAdapter(adapter);
    }

    public RecyclerView.Adapter getAdapter() {
        return rvList.getAdapter();
    }
}

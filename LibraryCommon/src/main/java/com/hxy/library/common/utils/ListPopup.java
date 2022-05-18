package com.hxy.library.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hxy.app.librarycore.R;

/**
 * @author cginechen
 * @date 2016-11-16
 */

public class ListPopup extends PopupWindow {

    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_NONE = 2;
    protected ImageView mArrowUp;
    protected ImageView mArrowDown;
    protected int mAnimStyle;
    protected int mDirection;

    private BaseQuickAdapter mAdapter;
    private Context context;

    public ListPopup(Context context, int width, int height) {
        super(width, height);
        this.context = context;
    }

    /**
     * 构造方法。
     *
     * @param context 传入一个 Context。
     * @param adapter 列表的 Adapter
     */
    public ListPopup(Context context, BaseQuickAdapter adapter) {
        this.context = context;
    }

    /**
     * type 是控制recyclerView  0网格  或者垂直1 类型
     */
    public void create(BaseQuickAdapter mAdapter) {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.popup_layout, null, true);
        RecyclerView recyclerView = layout.findViewById(R.id.rvList);
        mArrowDown = (ImageView) layout.findViewById(R.id.arrow_down);
        mArrowUp = (ImageView) layout.findViewById(R.id.arrow_up);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setPadding(15, 15, 15, 15);
        recyclerView.setAdapter(mAdapter);
        setTouchInterceptor((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                dismiss();
                return false;
            }
            return false;
        });
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        setContentView(layout);
    }

    public void create(BaseQuickAdapter mAdapter, int count) {
//        setWidth(width);
//        setHeight(maxHeight);
        FrameLayout layout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.popup_layout, null, true);
        RecyclerView recyclerView = layout.findViewById(R.id.rvList);
        mArrowDown = (ImageView) layout.findViewById(R.id.arrow_down);
        mArrowUp = (ImageView) layout.findViewById(R.id.arrow_up);
        recyclerView.setLayoutManager(new GridLayoutManager(context, count));
        recyclerView.setPadding(15, 15, 15, 15);
        recyclerView.setAdapter(mAdapter);
        setTouchInterceptor((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                dismiss();
                return false;
            }
            return false;
        });
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        setContentView(layout);
    }
}

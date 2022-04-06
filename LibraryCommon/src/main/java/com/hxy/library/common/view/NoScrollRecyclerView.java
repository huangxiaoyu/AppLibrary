package com.hxy.library.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 项目：MeiYueHongJiu  包名：
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/6/12
 * <p>
 * desc
 */
public class NoScrollRecyclerView extends RecyclerView {
    public NoScrollRecyclerView(Context context) {
        super(context);
    }

    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, mExpandSpec);
    }
}

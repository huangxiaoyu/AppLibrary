package com.hxy.library.common.photoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 2018/10/22 14:45
 * <p>
 * huangxiaoyu
 * <p>
 * desc 为了解决photoview嵌套在部分父控件时闪退的bug，github上提供的解决方案
 */
public class PhotoViewPager extends ViewPager {
    public PhotoViewPager(@NonNull Context context) {
        super(context, null);
    }

    public PhotoViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //uncomment if you really want to see these errors
            //e.printStackTrace();
            return false;
        }
    }
}

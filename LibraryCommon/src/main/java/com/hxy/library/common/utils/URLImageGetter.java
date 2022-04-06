package com.hxy.library.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * huangxiaoyu
 * 2020/7/27 0027 12:00
 */
public class URLImageGetter implements Html.ImageGetter {
    Context mContext;
    TextView mTextView;
    boolean isfullScreen;
    private List<Target> mTargets = new ArrayList<>();

    public URLImageGetter(Context context, TextView textView) {
        mContext = context;
        mTextView = textView;
        mTextView.setTag(mTargets);
    }

    public URLImageGetter(Context context, TextView textView, boolean isfullScreen) {
        mContext = context;
        mTextView = textView;
        mTextView.setTag(mTargets);
        this.isfullScreen = isfullScreen;
    }

    @Override
    public Drawable getDrawable(String s) {
        final LevelListDrawable drawable = new LevelListDrawable();
        int width = mTextView.getWidth();
        Glide.with(mContext).asBitmap().load(s).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<?
                    super Bitmap> transition) {
                if (resource != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                    drawable.addLevel(1, 1, bitmapDrawable);
                    Point point = new Point();
                    mTextView.getDisplay().getSize(point);
//                    int with = point.x;
                    int with = mTextView.getWidth();
                    LogUtils.e("" + with);
                    LogUtils.e("" + resource.getWidth());
                    if (isfullScreen) {
                        drawable.setBounds(0, 0, with,
                                (int) (Float.parseFloat(String.valueOf(with)) / resource.getWidth() * resource.getHeight()));
                    } else {
                        drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                    }
                    drawable.setLevel(1);
                    mTextView.invalidate();
                    mTextView.setText(mTextView.getText());
                }
            }

        });
        return drawable;
    }

    private class URLDrawable extends BitmapDrawable {
        private Drawable mDrawable;

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }
        }


        public void setDrawable(Drawable drawable) {
            mDrawable = drawable;
        }
    }

}

package com.hxy.library.common.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * huangxiaoyu
 * 2020/3/31 0031 15:19
 */
public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.imageView = (ImageView) view;
    }
}
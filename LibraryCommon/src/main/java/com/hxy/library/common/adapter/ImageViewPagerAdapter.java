package com.hxy.library.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.PagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hxy.app.librarycore.R;


public class ImageViewPagerAdapter extends PagerAdapter {
    Context context;
    JSONArray mDatas;
    String key;
    float ratio = 1f;
    OnItemChildViewClickListener onItemChildViewClickListener;
    int defaultRes;

    public ImageViewPagerAdapter(Context context, JSONArray datas, String key, float ratio) {
        this.context = context;
        mDatas = datas;
        this.key = key;
        this.ratio = ratio;
    }

    public ImageViewPagerAdapter(Context context, JSONArray datas, String key, float ratio,
                                 OnItemChildViewClickListener onItemChildViewClickListener) {
        this.context = context;
        mDatas = datas;
        this.key = key;
        this.ratio = ratio;
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    public ImageViewPagerAdapter(Context context, JSONArray datas, String key, float ratio,
                                 OnItemChildViewClickListener onItemChildViewClickListener,
                                 @DrawableRes int defaultRes) {
        this(context, datas, key, ratio, onItemChildViewClickListener);
        this.defaultRes = defaultRes;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    public JSONObject getItem(int position) {
        return mDatas.getJSONObject(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        JSONObject object = mDatas.getJSONObject(position);
        String imgPath = object.getString(key);
        int resId;
        try {
            resId = Integer.parseInt(imgPath);
            Glide.with(context).applyDefaultRequestOptions(RequestOptions.fitCenterTransform()
                    .error(R.mipmap.img_error).placeholder(R.mipmap.img_error)).load(resId).into
                    (imageView);
        } catch (Exception ex) {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions.fitCenterTransform()
                    .error(R.mipmap.img_error).placeholder(R.mipmap.img_error)).load(imgPath)
                    .into(imageView);
        }
        imageView.setOnClickListener(v -> {
            if (onItemChildViewClickListener != null) {
                onItemChildViewClickListener.onItemChildViewClickListener(v, -1, position);
            }
        });

        container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return imageView;
    }

    public void refresh(JSONArray adPagerDatas) {
        this.mDatas = adPagerDatas;
        notifyDataSetChanged();
    }

    public interface OnItemChildViewClickListener {
        void onItemChildViewClickListener(View v, int id, int position);
    }

}

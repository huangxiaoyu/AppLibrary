package com.hxy.library.common.photoview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hxy.app.librarycore.R;

import java.util.List;

/**
 * 2018/10/22 14:40
 * <p>
 * huangxiaoyu
 * <p>
 * desc
 */
public abstract class PhotoViewDialog extends Dialog {
    List<PhotoPic> mList;
    TextView mTtileView;
    PhotoViewPager mViewPager;
    TextView tvDesc;
    String desc;
    Context mContext;
    View mView;
    OnPhotoTapListener listener = new OnPhotoTapListener() {
        @Override
        public void onPhotoTap(ImageView view, float x, float y) {
            dismiss();
        }
    };

    public PhotoViewDialog(@NonNull Context context, List<PhotoPic> list, String desc) {
        super(context, R.style.transparentBgDialog);
        mContext = context;
        mList = list;
        this.desc = desc;
        initView();
        initData();
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_photoview, null);
        mTtileView = mView.findViewById(R.id.tvTitle);
        tvDesc = mView.findViewById(R.id.tvDesc);
        mViewPager = mView.findViewById(R.id.vpPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        wl.height = metrics.heightPixels;
        wl.width = metrics.widthPixels;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);

    }

    private void initData() {
        if (!TextUtils.isEmpty(desc)) {
            tvDesc.setText(desc);
        }
        mTtileView.setText(1 + "/" + mList.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTtileView.setText((position + 1) + "/" + mList.size());
                if (TextUtils.isEmpty(desc)) {
                    tvDesc.setText(mList.get(position).getDesc());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new PhotoViewPagerAdater());
    }

    class PhotoViewPagerAdater extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
            //            return super.getItemPosition(object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object
                object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return initItem(container, position);
        }
    }

    /**
     * 初始化item
     *
     * @param container
     * @param position
     * @return
     */
    public abstract Object initItem(@NonNull ViewGroup container, int position);

    public static class PhotoPic {
        String url;
        String desc;

        public PhotoPic(String url, String desc) {
            this.url = url;
            this.desc = desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

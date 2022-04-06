package com.hxy.library.common.utils;

import android.app.Activity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.hxy.app.librarycore.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.Locale;

/**
 * huangxiaoyu
 * 2020/7/27 0027 14:31
 */
public class URLTagHandler implements Html.TagHandler {
    Activity mActivity;

    public URLTagHandler(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable editable, XMLReader xmlReader) {
        // 处理标签<img>
        if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
            // 获取长度
            int len = editable.length();
            // 获取图片地址
            ImageSpan[] images = editable.getSpans(len - 1, len, ImageSpan.class);
            for (int i = 0; i < images.length; i++) {
                String imgURL = images[i].getSource();
                // 使图片可点击并监听点击事件
                editable.setSpan(new ClickableImage(mActivity, imgURL), len - 1, len,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
    }

    private class ClickableImage extends ClickableSpan {
        private String url;
        Activity mActivity;

        public ClickableImage(Activity activity, String url) {
            this.mActivity = activity;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            // 进行图片点击之后的处理
            ArrayList<LocalMedia> datas = new ArrayList<>();
            LocalMedia media = new LocalMedia();
            media.setPath(url);
            datas.add(media);
            PictureSelector.create(mActivity)
                    .themeStyle(R.style.picture_default_style)
                    .isNotPreviewDownload(true)
                    .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                    .openExternalPreview(0, datas);
        }
    }
}

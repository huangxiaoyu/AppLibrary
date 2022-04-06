package com.hxy.library.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

/**
 * 项目：MeiYueHongJiu  包名：
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/6/12
 * <p>
 * desc
 */
public class NoScrollWebView extends WebView {
    public NoScrollWebView(Context context) {
        this(context, null);
    }

    public NoScrollWebView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NoScrollWebView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        WebSettings webSettings = getSettings();
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        String dir = getContext().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getContext().getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibilityTraversal");
        removeJavascriptInterface("accessibility");
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, mExpandSpec);
    }
}

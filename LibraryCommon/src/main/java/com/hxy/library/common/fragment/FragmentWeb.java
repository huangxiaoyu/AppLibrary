package com.hxy.library.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import com.hxy.app.librarycore.databinding.FragmentWebBinding;
import com.hxy.library.common.utils.BaseContract;
import com.hxy.library.common.utils.Constants;

/**
 * Administrator
 * 2021/11/15 0015
 */
public class FragmentWeb extends FragmentBase<FragmentWebBinding, BaseContract.Presenter> {
    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }


    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {
        mBinding.titleLayout.toolBar.setVisibility(getArguments().getBoolean(Constants.KEY_BOOL, true) ?
                View.VISIBLE : View.GONE);
        WebSettings webSettings = mBinding.web.getSettings();
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        String dir = getActivity().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
        /*Mozilla/5.0 (Linux; Android 5.1.1; SM-G9350 Build/LMY48Z) AppleWebKit/537.36 (KHTML,
        like Gecko)
        Version/4.0 Chrome/39.0.0.0 Safari/537.36*/
        //        LogUtils.e(webSettings.getUserAgentString());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mBinding.web.removeJavascriptInterface("searchBoxJavaBridge_");
        mBinding.web.removeJavascriptInterface("accessibilityTraversal");
        mBinding.web.removeJavascriptInterface("accessibility");
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        String data = getArguments().getString(Constants.KEY_DATA);
        if (data.startsWith("http")) {
            mBinding.web.loadUrl(data);
        } else {
            mBinding.web.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.web.destroy();
    }
}

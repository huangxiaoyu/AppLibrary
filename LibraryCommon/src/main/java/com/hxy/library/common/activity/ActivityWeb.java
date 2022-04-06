package com.hxy.library.common.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.hxy.app.librarycore.R;
import com.hxy.app.librarycore.databinding.ActivityWebBinding;
import com.hxy.library.common.utils.BaseContract;
import com.hxy.library.common.utils.Constants;
import com.hxy.library.common.utils.LogUtils;
import com.hxy.library.common.utils.SweetAlertDialogFactory;
import com.hxy.library.common.utils.Utils;
import com.permissionx.guolindev.PermissionX;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.net.URISyntaxException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by huangxiaoyu on 2018-3-23.
 * -- wap页面
 * desc
 */

public class ActivityWeb extends ActivityBase<ActivityWebBinding, BaseContract.Presenter> {
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
        mBinding.tvText.setText(getString(R.string.app_name));
        mBinding.ivBack.setOnClickListener(v -> {
            if (mBinding.x5web.canGoBack()) {
                mBinding.x5web.goBack();
            } else {
                onBackPressed();
            }
        });
        mBinding.ivShare.setOnClickListener(v -> {
            //没有图片地址,可能会分享失败,需要传入imgUrl
//            Utils.showShare(null, getString(R.string.app_name), mBinding.x5web.getUrl(),
//                    mBinding.x5web.getTitle(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),
//                    mBinding.x5web.getUrl());
        });
//        mBinding.srLayout.setEnabled(!getIntent().hasExtra(Constants.KEY_BOOL) || getIntent().getBooleanExtra
//        (Constants.KEY_BOOL, true));
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String url = intent.hasExtra(Constants.KEY_DATA) ? intent.getStringExtra(Constants.KEY_DATA) : null;
        loadUrl(url);
        //        if (!TextUtils.isEmpty(url)) {
        //            mBinding.wvWeb.loadUrl(url);
        //        } else {
        //            Utils.showToast(this, "连接错误.无法打开");
        //            finish();
        //        }
    }


    @SuppressLint("JavascriptInterface")
    protected void initViews(Bundle savedInstanceState) {
        mBinding.x5web.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request) {
                LogUtils.e(request.getUrl().toString());
                if (Constants.ACCEPTED_URI_SCHEME.matcher(request.getUrl().toString().toLowerCase()).matches()) {
                    mBinding.pbWeb.setVisibility(View.VISIBLE);
                    if (request.getUrl().toString().contains("useshare=true")) {
                        mBinding.ivShare.setVisibility(View.VISIBLE);
                    } else if (request.getUrl().toString().contains("useshare=false")) {
                        mBinding.ivShare.setVisibility(View.GONE);
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                } else {
                    return urlAction(request.getUrl().toString()) ? true : super.shouldOverrideUrlLoading(view,
                            request);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e(url);
                if (Constants.ACCEPTED_URI_SCHEME.matcher(url.toLowerCase()).matches()) {
                    mBinding.pbWeb.setVisibility(View.VISIBLE);
                    if (url.contains("useshare=true")) {
                        mBinding.ivShare.setVisibility(View.VISIBLE);
                    } else if (url.contains("useshare=false")) {
                        mBinding.ivShare.setVisibility(View.GONE);
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    return urlAction(url) ? true : super.shouldOverrideUrlLoading(view, url);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    mBinding.tvText.setText(title);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtils.e(String.format("ErrorCode:%s;Description:%s", errorCode, description));
                view.loadUrl("file:///android_asset/error_page.html");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                LogUtils.e(String.format("ErrorCode:%s;Description:%s", error.getErrorCode(), error.getDescription()));
                view.loadUrl("file:///android_asset/error_page.html");
            }

        });
        mBinding.x5web.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        mBinding.x5web.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mBinding.pbWeb.setProgress(newProgress);
                if (mBinding.srLayout.isRefreshing() && newProgress > 60) {
                    mBinding.srLayout.setRefreshing(false);
                } else if (newProgress == 100) {
                    mBinding.pbWeb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mBinding.tvText.setText(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                SweetAlertDialogFactory.build(ActivityWeb.this, SweetAlertDialog.NORMAL_TYPE).setContentText(message).setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    result.confirm();
                }).show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                SweetAlertDialogFactory.build(ActivityWeb.this, SweetAlertDialog.NORMAL_TYPE).setContentText(message).setConfirmText("确定").setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    result.confirm();
                }).setCancelText("取消").setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    result.cancel();
                }).show();
                return true;
                //                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue,
                                      JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }


            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                LogUtils.i("test", "openFileChooser 1");
                ActivityWeb.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
                ActivityWeb.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.i("test", "openFileChooser 3");
                ActivityWeb.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                Log.i("test", "openFileChooser 4:" + filePathCallback.toString());
                ActivityWeb.this.uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }


        });
        mBinding.srLayout.setOnRefreshListener(() -> mBinding.x5web.reload());
        //处理web滑动监听
        mBinding.srLayout.setOnChildScrollUpCallback((parent, child) -> mBinding.x5web.canScrollVertically(-1));
        //        mBinding.wvWeb.addJavascriptInterface(new JavascriptInterface(this),
        //        00000000000000000000000000000000000000000
        // "meiyueInterface");
        WebSettings webSettings = mBinding.x5web.getSettings();
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath());
        /*Mozilla/5.0 (Linux; Android 5.1.1; SM-G9350 Build/LMY48Z) AppleWebKit/537.36 (KHTML,
        like Gecko)
        Version/4.0 Chrome/39.0.0.0 Safari/537.36*/
        //        LogUtils.e(webSettings.getUserAgentString());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mBinding.x5web.removeJavascriptInterface("searchBoxJavaBridge_");
        mBinding.x5web.removeJavascriptInterface("accessibilityTraversal");
        mBinding.x5web.removeJavascriptInterface("accessibility");

        String url = getIntent().hasExtra(Constants.KEY_DATA) ? getIntent().getStringExtra(Constants.KEY_DATA) : null;
        loadUrl(url);
    }

    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "请选择"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
    }

    private boolean urlAction(String url) {
        if (url.startsWith("tel:")) {
            PermissionX.init(this)
                    .permissions(Manifest.permission.CALL_PHONE)
                    .onExplainRequestReason((scope, deniedList, beforeRequest) -> scope.showRequestReasonDialog(deniedList, "需要申请拨打电话权限.", "确定", "取消"))
                    .onForwardToSettings((scope, deniedList) -> scope.showForwardToSettingsDialog(deniedList,
                            "您需要去应用程序设置当中手动开启权限", "开启", "取消"))
                    .request((allGranted, grantedList, deniedList) -> {
                        if (!allGranted) {
                            SweetAlertDialogFactory.build(ActivityWeb.this, SweetAlertDialog.ERROR_TYPE,
                                    false).setContentText(
                                    "您拒绝了使用的权限").setConfirmClickListener(dialog -> {
                                dialog.dismiss();
                            }).show();
                        } else {
                            Utils.telTo(ActivityWeb.this, url.split(":")[1]);
                        }
                    });

            return true;
        } else if (url.startsWith("sms:")) {
            String[] objs = url.split(":");
            Utils.sendSms(ActivityWeb.this, objs[1], objs.length > 2 ? objs[2] : null);
            return true;
        } else if (url.startsWith("share:")) {
            Utils.share(ActivityWeb.this, url.split(":")[1]);
            return true;
        } else if (url.startsWith(Constants.DEAL_URL_APP_SHARE)) {
            if (url.split(Constants.DEAL_URL_APP_SHARE).length > 1) {
//                try {
//                    Utils.appShare(ActivityWeb.this, URLDecoder.decode(url.split(Constants.DEAL_URL_APP_SHARE)[1],
//                            "utf-8"));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                    Toast.makeText(ActivityWeb.this, "参数错误", Toast.LENGTH_SHORT).show();
//                }
            } else {
                Toast.makeText(ActivityWeb.this, "参数错误", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                intent.setComponent(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    intent.setSelector(null);
                }
                if (getPackageManager().resolveActivity(intent, 0) == null) {//未找到对应的应用程序打开scheme
                    Utils.showToast(ActivityWeb.this, "未找到对应的应用程序打开" + intent.getPackage());
                    //                            return Utils.tryHandleByMarket(MainActivity
                    // .this, intent) ?
                    // true : super.shouldOverrideUrlLoading(view, url);
                    return true;
                }
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return true;
            }
            return true;
        }
    }

    public void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("useshare=true")) {
                mBinding.ivShare.setVisibility(View.VISIBLE);
            } else if (url.contains("useshare=false")) {
                mBinding.ivShare.setVisibility(View.GONE);
            }
            mBinding.x5web.loadUrl(url);
        } else {
            Utils.showToast(this, "连接错误.无法打开");
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBinding.x5web != null) {
            mBinding.x5web.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBinding.x5web != null) {
            mBinding.x5web.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mBinding.x5web != null) {
            mBinding.x5web.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mBinding.x5web.canGoBack()) {
                mBinding.x5web.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

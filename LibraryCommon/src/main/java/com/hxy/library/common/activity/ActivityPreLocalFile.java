package com.hxy.library.common.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.hxy.app.librarycore.databinding.ActivityPreLocalFileBinding;
import com.hxy.library.common.utils.BaseContract;
import com.hxy.library.common.utils.Constants;
import com.hxy.library.common.utils.LogUtils;
import com.hxy.library.common.utils.SweetAlertDialogFactory;
import com.jaeger.library.StatusBarUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Administrator
 * 2022/3/30 0030
 */
public class ActivityPreLocalFile extends ActivityBase<ActivityPreLocalFileBinding, BaseContract.Presenter> {
    TbsReaderView mTbsReaderView;

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setLightMode(this);
        mBinding.titleLayout.toolBar.setNavigationOnClickListener(v -> finish());
        if (getIntent() != null && getIntent().hasExtra(Constants.KEY_DATA)) {
            addTbsReaderView();
        } else {
            ToastUtils.showShort("文件路径错误");
            finish();
        }


    }

    private void addTbsReaderView() {
        //1、设置回调
        TbsReaderView.ReaderCallback readerCallback = new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                //回调结果参考 TbsReaderView.ReaderCallback
                LogUtils.e("ReaderCallback", "readerCallback: " + integer);
            }
        };
        //2、创建TbsReaderView
        mTbsReaderView = new TbsReaderView(this, readerCallback);

        //3、将TbsReaderView 添加到RootLayout中（可添加到自定义标题栏的下方）
        mBinding.flContainer.addView(
                mTbsReaderView,
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        String title = getIntent().getStringExtra(Constants.KEY_TITLE);
        String path = getIntent().getStringExtra(Constants.KEY_DATA);
        mBinding.titleLayout.tvTitle.setText(!TextUtils.isEmpty(title) ? title :
                path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf(".")));
        //4、传入指定参数
        Bundle bundle = new Bundle();
        bundle.putString(TbsReaderView.KEY_FILE_PATH, path);
        bundle.putString(TbsReaderView.KEY_TEMP_PATH, getExternalFilesDir("temp").getAbsolutePath());
        String extensionName = getFileType(path);

        //5、调用preOpen判断是否支持当前文件类型 （若tbs支持的文档类型返回false，则说明内核未加载成功）
        boolean result = mTbsReaderView.preOpen(extensionName, false);
        if (result) {
            //6、调用openFile打开文件
            mTbsReaderView.openFile(bundle);
        } else {
            SweetAlertDialogFactory.build(this, SweetAlertDialog.ERROR_TYPE, false).setContentText("不支持的格式.").setConfirmText("关闭").setConfirmClickListener(dialog -> {
                dialog.dismiss();
                finish();
            }).show();
        }
    }

    /***
     * 获取文件类型
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = paramString.substring(i + 1);
        return str;
    }

    @Override
    public void onDestroy() {
        //7、结束时一定调用onStop方法
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
        super.onDestroy();
    }
}

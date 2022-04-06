package com.hxy.library.common.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.hxy.library.common.activity.ActivityApp;
import com.hxy.library.common.activity.ActivityBase;
import com.hxy.library.common.http.Page;
import com.hxy.library.common.http.lifecycle.AndroidLifecycle;
import com.hxy.library.common.http.lifecycle.LifecycleProvider;
import com.hxy.library.common.utils.BaseContract;
import com.hxy.library.common.utils.SweetAlertDialogFactory;
import com.hxy.library.common.utils.Utils;
import com.hxy.library.common.view.LoadingDialog;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.trello.rxlifecycle4.android.RxLifecycleAndroid;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * 项目：MeiYueHongJiu  包名：com.hxy.app.locationdemo.fragment
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/5/7
 * <p>
 * desc
 */
public abstract class FragmentBase<V extends ViewBinding, P extends BaseContract.Presenter> extends Fragment implements BaseContract.View<FragmentEvent> {
    public LoadingDialog mLoadingDialog;
    protected ActivityBase mActivity;//避免系统回收activity.声明一个全局的mContext
    public String TAG;
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    public V mBinding;
    public Page mPage;
    public P mPresenter;

    /**
     * mPresenter
     * 不可调用本方法 可直接使用mPresenter属性 否则会重复创建presenter
     */
    public abstract P getPresenter();

    @Override
    public LifecycleProvider getLifecycleProvider() {
        return AndroidLifecycle.createLifecycleProvider(this);
    }

    @Override
    public Context getCurrentContext() {
        return mActivity;
    }

    public void showLoading() {
        if (!getActivity().isFinishing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!TextUtils.isEmpty(getTitle())) {
                ((ActivityApp) getActivity()).setCenterTitle(getTitle());
            }
        }
    }

    /**
     * 布局id
     *
     * @return
     */
//    protected abstract int getLayoutId();

    /**
     * activity标题
     *
     * @return
     */
    protected abstract String getTitle();


    /**
     * 初始化页面
     *
     * @param saveInstanceState
     */
    protected abstract void initView(Bundle saveInstanceState);

    protected abstract void initData(Bundle saveInstanceState);


    protected ActivityBase getBaseActivity() {
        return mActivity;
    }


    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return this.lifecycleSubject.hide();
    }

    @NonNull
    @CheckResult
    public final <L> LifecycleTransformer<L> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, event);
    }

    @NonNull
    @CheckResult
    public final <L> LifecycleTransformer<L> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(this.lifecycleSubject);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.lifecycleSubject.onNext(FragmentEvent.ATTACH);
        TAG = this.getClass().getSimpleName();
        mPresenter = getPresenter();
        if (!TextUtils.isEmpty(getTitle())) {
            ((ActivityApp) getActivity()).setCenterTitle(getTitle());
        }
        mActivity = (ActivityBase) getActivity();
        mLoadingDialog = new LoadingDialog(mActivity);
        mLoadingDialog.setOnCancelListener(dialog -> {
            mPresenter.cancleTag(TAG);
        });
    }

    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class cls = (Class) type.getActualTypeArguments()[0];
        try {
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            mBinding = (V) inflate.invoke(null, inflater, container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        initView(savedInstanceState);
        return mBinding.getRoot();
    }

    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        initData(savedInstanceState);
    }

    @CallSuper
    public void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext(FragmentEvent.START);
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        String canonicalName = this.getClass().getCanonicalName();
        MobclickAgent.onPageStart(canonicalName); //auto模式自动埋点 统计页面
        this.lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @CallSuper
    public void onPause() {
        this.lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        String canonicalName = this.getClass().getCanonicalName();
        MobclickAgent.onPageEnd(canonicalName);//auto模式自动埋点 统计页面

    }

    @CallSuper
    public void onStop() {
        this.lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @CallSuper
    public void onDestroyView() {
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @CallSuper
    public void onDestroy() {
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @CallSuper
    public void onDetach() {
        this.lifecycleSubject.onNext(FragmentEvent.DETACH);
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDetach();
    }


    /**
     * 弹出toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (isAdded()) {
            Utils.showToast(getActivity(), msg);
        }
    }

    public void showTap(String msg) {
        if (isAdded()) {
            SweetAlertDialogFactory.build(mActivity).setContentText(msg).show();
        }
    }

    public void showTap(int type, String msg) {
        if (isAdded()) {
            SweetAlertDialogFactory.build(mActivity, type).setContentText(msg).show();
        }
    }

    public void startActivity(Class c) {
        startActivity(new Intent(getActivity(), c));
    }

    public void startActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(getActivity(), c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void refreshUI(Bundle bundle) {
//        showToast("refreshUI");
    }


//    protected void setImmerseLayout(View view) {// view为标题栏
//        Window window = getActivity().getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//            View
//            .SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        int statusBarHeight = getStatusBarHeight(this.getActivity().getBaseContext());
//        view.setPadding(0, statusBarHeight, 0, 0);
//    }
//
//    public int getStatusBarHeight(Context context) {
//        int result = 0;
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
//        "android");
//        if (resourceId > 0) {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }

    protected void setImmerseLayout(View view) {// view为标题栏
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager
                    .LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        int statusBarHeight = getStatusBarHeight(this.getActivity().getBaseContext());
        view.setPadding(0, statusBarHeight + statusBarHeight / 3, 0, 0);
    }

    protected void setImmerseLayout(View view, int color) {// view为标题栏
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager
                    .LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        int statusBarHeight = getStatusBarHeight(this.getActivity().getBaseContext());
        view.setPadding(0, statusBarHeight + statusBarHeight / 3, 0, 0);
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

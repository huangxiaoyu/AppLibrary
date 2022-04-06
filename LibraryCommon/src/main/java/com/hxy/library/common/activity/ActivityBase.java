package com.hxy.library.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.hxy.library.common.http.Page;
import com.hxy.library.common.http.lifecycle.AndroidLifecycle;
import com.hxy.library.common.http.lifecycle.LifecycleProvider;
import com.hxy.library.common.utils.AppTaskManager;
import com.hxy.app.librarycore.utils.BaseContract;
import com.hxy.library.common.utils.BaseContract;
import com.hxy.library.common.utils.SweetAlertDialogFactory;
import com.hxy.library.common.utils.Utils;
import com.hxy.library.common.view.LoadingDialog;
import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.RxLifecycleAndroid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * 基本封装activity基类
 */
public abstract class ActivityBase<V extends ViewBinding, P extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View<ActivityEvent> {
    public static final String KEY_SAVE_DATA = "key_save_data";
    public static final String KEY_SAVE_OBJ = "key_save_obj";
    public LoadingDialog mLoadingDialog;
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    public Fragment mFragment;
    public V mBinding;
    public AppCompatActivity mActivity;
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
    public void onFail(Throwable throwable) {

    }

    @Override
    public Context getCurrentContext() {
        return mActivity;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


//    @LayoutRes
//    protected abstract int getContentViewId();

    public String TAG = null;

    public void beforOnCreate() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager
//                .LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //使用DecorView的硬件加速api对控件进行灰化处理,比如4月4号的个悼念逝者的特殊日子
//        Calendar calendar = Calendar.getInstance();
//        if (calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DAY_OF_MONTH) == 4) {
//            Paint paint = new Paint();
//            ColorMatrix cm = new ColorMatrix();
//            cm.setSaturation(0);
//            paint.setColorFilter(new ColorMatrixColorFilter(cm));
//            View decorView = getWindow().getDecorView();
//            decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
//            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        beforOnCreate();
        //设置状态栏黑色
        super.onCreate(savedInstanceState);
        mActivity = this;
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class cls = (Class) type.getActualTypeArguments()[0];
        try {
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class);
            mBinding = (V) inflate.invoke(null, getLayoutInflater());
            setContentView(mBinding.getRoot());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        setStatusBar();
        this.lifecycleSubject.onNext(ActivityEvent.CREATE);
        //沉浸式状态栏，此方法需要布局向下调整一个状态栏的高度
        //        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            Window window = getWindow();
        //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        //                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //            window.getDecorView().setSystemUiVisibility(View
        // .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        //                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //            window.setStatusBarColor(Color.TRANSPARENT);
        //            window.setNavigationBarColor(Color.TRANSPARENT);
        //        }
        //        setContentView(R.layout.activity_mine_base);
        AppTaskManager.getAppManager().addActivity(this);
        TAG = getClass().getSimpleName();
        mLoadingDialog = new LoadingDialog(mActivity);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        //点击取消的时候取消订阅
        mLoadingDialog.setOnCancelListener(dialog -> {
            if (TextUtils.isEmpty(TAG) && mPresenter != null) {
                mPresenter.cancleTag(TAG);
            }
        });
        mPresenter = getPresenter();
    }

    protected void setStatusBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setTransparent(this);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager
//                .LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public void showLoading() {
        if (!isFinishing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissLoading() {
        mLoadingDialog.dismiss();
    }


    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return this.lifecycleSubject.hide();
    }

    @NonNull
    @CheckResult
    public final <L> LifecycleTransformer<L> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, event);
    }

    @NonNull
    @CheckResult
    public final <L> LifecycleTransformer<L> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(this.lifecycleSubject);
    }


    @CallSuper
    protected void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext(ActivityEvent.START);
    }

    @CallSuper
    protected void onResume() {
        super.onResume();
        this.lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @CallSuper
    protected void onPause() {
        this.lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    protected void onStop() {
        this.lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @CallSuper
    protected void onDestroy() {
        this.lifecycleSubject.onNext(ActivityEvent.DESTROY);
        if (mPresenter != null) {
            mPresenter.detach();
        }
        /*清除内存缓存*/
        Glide.get(mActivity).clearMemory();
        /*清除硬盘缓存*/
        Observable.create((ObservableOnSubscribe) emitter -> {
            Glide.get(mActivity).clearDiskCache();
            emitter.onNext("");
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
        AppTaskManager.getAppManager().removeActivity(mActivity);
        super.onDestroy();
    }


    /**
     * 弹出toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        Utils.showToast(this, msg);
    }

    public void showToast(@StringRes int resId) {
        Utils.showToast(this, getString(resId));
    }

    public void showTip(String msg) {
        SweetAlertDialogFactory.build(mActivity).setContentText(msg).show();
    }

    public void showTip(int type, String msg) {
        SweetAlertDialogFactory.build(mActivity, type).setContentText(msg).show();
    }

    public void startActivity(Class<?> c) {
        startActivity(new Intent(mActivity, c));
    }

    public void startActivity(Class<?> c, Bundle bundle) {
        Intent intent = new Intent(mActivity, c);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}

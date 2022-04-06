package com.hxy.library.common.utils;

import android.content.Context;

import com.trello.rxlifecycle4.LifecycleProvider;

/**
 * huangxiaoyu
 * 2020/4/16 0016 14:39
 */
public interface BaseContract {
    /**
     * 数据处理
     */
    interface Model {

        void detach();

        void cancleTag(String tag);
    }

    /**
     * 数据回调
     */
    interface View<T> extends LifecycleProvider<T> {
        /**
         * 显示加载
         */
        void showLoading();

        /**
         * 隐藏加载
         */
        void dismissLoading();

        Context getCurrentContext();

        /**
         * 用于发生错误后ui上的更新处理,一般不会调用,所有的fial在presenter层处理完毕.只有类似加载更多的时候接口请求出错了,更新取消加载的状态展示会调用,在view层从写该方法
         */
        void onFail(Throwable throwable);

        com.hxy.library.common.http.lifecycle.LifecycleProvider getLifecycleProvider();
    }

    /**
     * 业务处理
     */
    interface Presenter {
        void detach();

        void cancleTag(String tag);
    }

    interface CallBack<T> {
        void onSuccess(T t);

        void onFail(Throwable throwable);
    }

//    /**
//     * model类封装
//     */
//    class BaseModel<ApiService> implements BaseContract.Model {
//
//        protected CompositeDisposable mCompositeDisposable;//取消retorfit obs集合
//        protected HashMap<String, Call> mCallMap;//取消call集合
//        protected RestClient<ApiService> mRestClient;//接口请求类
//
//        public BaseModel() {
//            mCompositeDisposable = new CompositeDisposable();
//            mRestClient = BaseApplication.getApplication().getRestClient();
//            mCallMap = new HashMap<>();
//        }
//
//        /**
//         * 结合rxliftcycle自动管理声明周期 不用再调用{@detach}
//         */
//        @Deprecated
//        @Override
//        public void detach() {
//            if (mCallMap != null && mCallMap.size() > 0) {
//                Iterator<String> iterator = mCallMap.keySet().iterator();
//                while (iterator.hasNext()) {
//                    if (mCallMap.get(iterator.next()).isCanceled()) {
//                        mCallMap.get(iterator.next()).cancel();
//                    }
//                }
//            }
//            if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
////            mCompositeDisposable.dispose();//不能使用dispose,用了之后add就失效了,不能复用
//                mCompositeDisposable.clear();
//            }
//        }
//
//        /**
//         * 结合rxliftcycle自动管理声明周期 不在使用{@cacancleTag}
//         *
//         * @param tag
//         */
//        @Deprecated
//        @Override
//        public void cancleTag(String tag) {
//            if (mCallMap.containsKey(tag)) {
//                if (!mCallMap.get(tag).isCanceled()) {
//                    mCallMap.get(tag).cancel();
//                }
//            }
//        }
//
//    }
//
//    /**
//     * persenter类封装
//     */
//    class BasePresenter<V extends BaseContract.View, M extends BaseContract.Model> implements BaseContract.Presenter {
//        protected M mModel;
//        protected V mView;
//        WeakReference<V> mReference;
//
//        /**
//         * @param view  不是某个view控件,而是mvp中的view展示层
//         * @param model
//         */
//        public BasePresenter(V view, M model) {
//            mModel = model;
//            mView = view;
//            mReference = new WeakReference<>(view);
//        }
//
//
//        @Override
//        public void detach() {
//            if (mModel != null) {
//                mModel.detach();
//            }
//            if (mReference != null) {
//                mReference.clear();
//                mReference = null;
//            }
//        }
//
//        @Override
//        public void cancleTag(String tag) {
//            if (mModel != null) {
//                mModel.cancleTag(tag);
//            }
//        }
//    }
//
//    /**
//     * 业务的callback
//     */
//    abstract class SimpleCallBack<T> implements CallBack<T> {
//        Context mContext;
//        boolean isShowError = true;
//
//        public SimpleCallBack(Context context) {
//            mContext = context;
//        }
//
//        /**
//         * 不显示错误弹窗,可用于不需要给用户反馈的操作
//         */
//        public SimpleCallBack(Context context, boolean showError) {
//            mContext = context;
//            isShowError = showError;
//        }
//
//
//        public void onFail(Throwable throwable) {
//            throwable.printStackTrace();
//            /*接口的异常问题在ApiCallBack中发出,其他业务的异常自己捕获后发出*/
////            MobclickAgent.reportError(mContext.getApplicationContext(), throwable);
//            if (isShowError) {
//                SweetAlertDialogFactory.build(mContext, SweetAlertDialog.ERROR_TYPE).setContentText(throwable
//                .getMessage()).show();
//            }
//        }
//    }
}

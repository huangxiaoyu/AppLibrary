package com.hxy.library.common.http.lifecycle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxy.library.common.rx.RxBus;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public abstract class DefaultCallback<T> implements Callback<T> {
    @NonNull
    @Override
    public HttpError parseThrowable(Call<T> call, Throwable t) {
        if (t instanceof HttpError) {
            return (HttpError) t;
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            final String msg;
            switch (httpException.code()) {
                case 400:
                    msg = "参数错误";
                    break;
                case 401:
                    msg = "无登录信息或登录已失效";
                    RxBus.getDefault().post(RxBus.RX_TOKEN_TIMEOUT, "无登录信息或登录已失效");
                    break;
                case 403:
                    msg = "禁止访问";
                    break;
                case 404:
                    msg = "地址未找到";
                    break;
                default:
                    msg = "请求异常:" + httpException.code();
            }
            return new HttpError(msg, httpException);
        } else if (t instanceof UnknownHostException) {
            return new HttpError("网络异常", t);
        } else if (t instanceof ConnectException) {
            return new HttpError("网络连接异常", t);
        } else if (t instanceof SocketException) {
            return new HttpError("网络连接异常", t);
        } else if (t instanceof SocketTimeoutException) {
            return new HttpError("响应超时", t);
        } else {
            return new HttpError("请求失败", t);
        }
    }

    @NonNull
    @Override
    public T transform(Call<T> call, T t) {
        return t;
    }

    @Override
    public void onCompleted(Call<T> call, @Nullable Throwable t) {
        if (t != null) {
            Log.w(RetrofitFactory.TAG, "onCompleted-->\n" + Utils.getStackTraceString(t));
        }
    }
}
package com.hxy.library.common.http.lifecycle;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 创建时间：2019-09-04
 * 编写人： chengxin
 * 功能描述：代理{@link Call.Factory} 拦截{@link #newCall(Request)}方法
 */
public abstract class CallFactoryProxy implements Call.Factory {

    protected final Call.Factory delegate;

    public CallFactoryProxy(Call.Factory delegate) {
        Utils.checkNotNull(delegate, "delegate==null");
        this.delegate = delegate;
    }
}

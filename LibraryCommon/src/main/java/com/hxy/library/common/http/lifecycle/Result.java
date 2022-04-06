package com.hxy.library.common.http.lifecycle;

import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;

/**
 * An HTTP Result. like Retrofit,retrofit2.adapter.rxjava2.Result
 */
public final class Result<T> {

    /**
     * @param body 请求成功返回的body
     * @throws NullPointerException if body==null
     */
    @CheckResult
    public static <T> Result<T> success(T body) {
        Utils.checkNotNull(body, "body==null");
        return new Result<>(body, null);
    }

    /**
     * @param error 请求失败返回的错误信息
     * @throws NullPointerException if error==null
     */
    @CheckResult
    public static <T> Result<T> error(Throwable error) {
        Utils.checkNotNull(error, "error==null");
        return new Result<>(null, error);
    }

    @Nullable
    private final Throwable error;
    @Nullable
    private final T body;

    private Result(@Nullable T body, @Nullable Throwable error) {
        this.error = error;
        this.body = body;
    }

    @Nullable
    public Throwable error() {
        return error;
    }

    @Nullable
    public T body() {
        return body;
    }

    /**
     * 判断http请求是否成功返回了body
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return body != null;
    }

}

package com.hxy.library.common.http;

/**
 * 项目：
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/5/14
 * <p>
 * desc
 */
public class ApiException extends Exception {
    public int getCode() {
        return code;
    }

    int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }
}

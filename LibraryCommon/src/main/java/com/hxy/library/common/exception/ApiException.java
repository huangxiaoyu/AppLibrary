package com.hxy.library.common.exception;

/**
 * 项目：MeiYueHongJiu  包名：com.hxy.app.locationdemo.exception
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

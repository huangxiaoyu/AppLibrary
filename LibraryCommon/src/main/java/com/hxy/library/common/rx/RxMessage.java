package com.hxy.library.common.rx;

/**
 * Created by jingbin on 16/5/17.
 */
public class RxMessage {
    private int code;
    private Object object;

    public RxMessage(int code, Object object) {
        this.code = code;
        this.object = object;
    }

    public RxMessage() {
    }

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}

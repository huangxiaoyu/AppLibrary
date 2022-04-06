package com.hxy.library.common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hxy.library.common.utils.Constants;

/**
 * 2018/12/7 17:14
 * <p>
 * huangxiaoyu
 * <p>
 * desc
 */
public class ExpandSub<T> implements MultiItemEntity {
    T data;
    int itemType = Constants.TYPE_LEVEL_2;
    boolean isSelect = false;

    public ExpandSub(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}

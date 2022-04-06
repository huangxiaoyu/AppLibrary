package com.hxy.library.common.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class MySectionEntity<T> extends SectionEntity<T> {
    public MySectionEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySectionEntity(T t) {
        super(t);
    }
}

package com.hxy.library.common.bean;

//import com.chad.library.adapter.base.entity.AbstractExpandableItem;
//import com.chad.library.adapter.base.entity.MultiItemEntity;
//import com.hxy.app.librarycore.utils.Constants;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hxy.library.common.utils.Constants;

/**
 * 2018/12/7 17:14
 * <p>
 * huangxiaoyu
 * <p>
 * desc
 */
public class ExpandParent<K, T> extends AbstractExpandableItem<T> implements MultiItemEntity {
    String id;
    String title;
    String startDate;
    String endDate;
    K parent;
    int level = Constants.TYPE_LEVEL_1;
    int itemType = Constants.TYPE_LEVEL_1;

    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id == null ? "" : id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getStartDate() {
        return startDate == null ? "" : startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? "" : startDate;
    }

    public String getEndDate() {
        return endDate == null ? "" : endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? "" : endDate;
    }

    public K getParent() {
        return parent;
    }

    public void setParent(K parent) {
        this.parent = parent;
    }
}

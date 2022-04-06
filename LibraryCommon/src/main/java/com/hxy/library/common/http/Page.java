package com.hxy.library.common.http;

/**
 * Created by huangxiaoyu on 2016/9/22.
 */
public class Page {
    public static final int DEFAULT_PAGE_SIZE = 10;

    public Page() {
    }

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * pageNo : 0
     * pageSize : 0
     * sortField : string
     * sortType : 0
     * pageCount : 0
     * recordCount : 0
     */

    private int pageNo = 1;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private String sortField;
    private int sortType;
    private int pageCount;
    private int recordCount;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}

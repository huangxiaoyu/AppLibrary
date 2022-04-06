package com.hxy.library.common.http;

/**
 * 2018/12/18 17:44
 * <p>
 * huangxiaoyu
 * <p>
 * desc
 */
public class Version {

    /**
     * versionnumberid : b1f84833-9bd7-4c21-a6e8-07d78aa81365
     * versionnumbertype : 1
     * name : 安卓
     * versionnumber : 129
     * url :
     * deletemark : 0
     * description : 2
     * createdate : 2018-06-27 20:20:32
     * createuserid : System
     * createusername : 超级管理员
     */

    private String versionnumberid;
    private int versionnumbertype;
    private String name;
    private String versionnumber;//版本
    private String url;
    private int deletemark;
    private String description;
    private String createdate;
    private String createuserid;
    private String createusername;

    public String getVersionnumberid() {
        return versionnumberid;
    }

    public void setVersionnumberid(String versionnumberid) {
        this.versionnumberid = versionnumberid;
    }

    public int getVersionnumbertype() {
        return versionnumbertype;
    }

    public void setVersionnumbertype(int versionnumbertype) {
        this.versionnumbertype = versionnumbertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionnumber() {
        return versionnumber;
    }

    public void setVersionnumber(String versionnumber) {
        this.versionnumber = versionnumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDeletemark() {
        return deletemark;
    }

    public void setDeletemark(int deletemark) {
        this.deletemark = deletemark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }
}

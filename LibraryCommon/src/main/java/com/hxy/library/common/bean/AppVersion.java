package com.hxy.library.common.bean;

/**
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/6/27
 * <p>
 * desc
 */
public class AppVersion {


    /**
     * version_number_id : e0c6a83e-0623-4018-9188-6c998da092e7
     * version_number_type :
     * version_number_name : 1.2
     * version_number : 2.0
     * version_url : 2.0
     * delete_mark :
     * description :
     * create_date : 2019-03-15T15:25:24.0000000+08:00
     * create_user_id : System
     * create_user_name : 超级管理员
     */

    private String version_number_id;
    private String version_number_type;
    private String version_number_name;
    private String version_number;
    private String version_url;
    private String delete_mark;
    private String description;
    private String create_date;
    private String create_user_id;
    private String create_user_name;
    private String appname;
    private String iosurl;
    private String iosversion;
    private String url = "";
    private String version = "0";

    public String getVersion_number_id() {
        return version_number_id == null ? "" : version_number_id;
    }

    public void setVersion_number_id(String version_number_id) {
        this.version_number_id = version_number_id == null ? "" : version_number_id;
    }

    public String getVersion_number_type() {
        return version_number_type == null ? "" : version_number_type;
    }

    public void setVersion_number_type(String version_number_type) {
        this.version_number_type = version_number_type == null ? "" : version_number_type;
    }

    public String getVersion_number_name() {
        return version_number_name == null ? "" : version_number_name;
    }

    public void setVersion_number_name(String version_number_name) {
        this.version_number_name = version_number_name == null ? "" : version_number_name;
    }

    public String getVersion_number() {
        return version_number == null ? "" : version_number;
    }

    public void setVersion_number(String version_number) {
        this.version_number = version_number == null ? "" : version_number;
    }

    public String getVersion_url() {
        return version_url == null ? "" : version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url == null ? "" : version_url;
    }

    public String getDelete_mark() {
        return delete_mark == null ? "" : delete_mark;
    }

    public void setDelete_mark(String delete_mark) {
        this.delete_mark = delete_mark == null ? "" : delete_mark;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public String getCreate_date() {
        return create_date == null ? "" : create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date == null ? "" : create_date;
    }

    public String getCreate_user_id() {
        return create_user_id == null ? "" : create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id == null ? "" : create_user_id;
    }

    public String getCreate_user_name() {
        return create_user_name == null ? "" : create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name == null ? "" : create_user_name;
    }

    public String getAppname() {
        return appname == null ? "" : appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? "" : appname;
    }

    public String getIosurl() {
        return iosurl == null ? "" : iosurl;
    }

    public void setIosurl(String iosurl) {
        this.iosurl = iosurl == null ? "" : iosurl;
    }

    public String getIosversion() {
        return iosversion == null ? "" : iosversion;
    }

    public void setIosversion(String iosversion) {
        this.iosversion = iosversion == null ? "" : iosversion;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url;
    }

    public String getVersion() {
        return version == null ? "" : version;
    }

    public void setVersion(String version) {
        this.version = version == null ? "" : version;
    }
}

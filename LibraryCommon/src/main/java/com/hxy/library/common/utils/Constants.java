package com.hxy.library.common.utils;


import java.util.regex.Pattern;

/**
 * 项目：LocationDemo  包名：com.hxy.app.locationdemo.utils
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/6/8
 * <p>
 * desc
 */
public class Constants {
    /**
     * 系统可以处理的url正则
     */
    public static final Pattern ACCEPTED_URI_SCHEME = Pattern.compile("(?i)" + // switch on case
            // insensitive matching
            '(' + // begin group for scheme
            "(?:http|https|ftp|file)://" + "|(?:inline|data|about|javascript):" + "|(?:.*:.*@)" + ')' + "(.*)");

    public static final String REG_PWD = "^(?![A-Za-z]+$)(?!\\d+$)[A-Za-z0-9]{6,16}$";//字母加数组组合
    // 一、假定密码字符数范围6-16，除英文数字和字母外的字符都视为特殊字符：
    public static final String REG_PWD_WEEK = "^[0-9A-Za-z]{6,18}$";//弱：
    public static final String REG_PWD_MEDIUM = "^(?=.{6,18})[0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z" +
            "]*$";// 中：
    public static final String REG_PWD_STRONG = "^(?=.{6,18})([0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z" +
            "]*){2,}$";//  强 ：
    // 二、假定密码字符数范围6-16，密码字符允许范围为ASCII码表字符：
    public static final String REG_PWD_ASCII_WEEK = "^[0-9A-Za-z]{6,18}$";//   弱：
    public static final String REG_PWD_ASCII_MEDIUM = "^(?=.{6,18})[0-9A-Za-z]*[\\x00-\\x2f\\x3A" +
            "-\\x40\\x5B-\\xFF][0" +
            "-9A-Za-z]*$";  //中：
    public static final String REG_PWD_ASCII_STRONG = "^(?=.{6,18})([0-9A-Za-z]*[\\x00-\\x2F\\x3A" +
            "-\\x40\\x5B-\\xFF][0" +
            "-9A-Za-z]*){2,}$*";  //强：

    public static final String DEAL_URL_APP_SHARE = "appshare:";//商品列表
    public static final String DEAL_URL_HASTITLE_TRUE = "hastitle=true";//使用wap页面的标题
    public static final String DEAL_URL_HASTITLE_FALSE = "hastitle=false";//使用app页面的标题
    //    public static final String DIR_ROOT = Environment.getExternalStorageDirectory().getPath();
    //    public static final String DIR_BASE = "zhongxinedu";
    //    public static final String DIR_IMGS = "imgs";
    //    public static final String DIR_LOGS = "logs";
    //    public static final String DIR_FULL_BASE = DIR_ROOT + File.separator + DIR_BASE;
    //    public static final String DIR_FULL_IMGS = DIR_FULL_BASE + File.separator + DIR_IMGS;
    //    public static final String DIR_FULL_LOGS = DIR_FULL_BASE + File.separator + DIR_LOGS;
    public static final int RESULT_OK = 1;
    public static final String SP_CONFIG = "sp_config";
    public static final String KEY_DATA = "key_data";
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_OBJ = "key_obj";
    public static final String KEY_OBJ2 = "key_obj2";
    public static final String KEY_BOOL = "key_bool";
    public static final String KEY_TYPE = "key_type";
    public static final String KEY_INDEX = "key_index";
    public static final String KEY_STATE = "key_state";
    public static final String SP_KEY_ISLOGIN = "sp_key_islogin";
    public static final String SP_KEY_LOGIN_DATA = "sp_key_login_data";
    // 退出后上传坐标以及退出接口
    public static final String SP_KEY_IN_FENCE_DATA = "sp_key_in_fence_date";//集客进出围栏的时间  30分钟内部不提示
    public static final String SP_KEY_OUT_FENCE_DATA = "sp_key_in_fence_date";//集客进出围栏的时间 30分钟内部提示
    public static final String MATCHES_MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";// 钱的正则
    public static final String MATCHES_IDCARD = "^(\\d{18}$)|(^\\d{17}(\\d|X|x))$";// 身份证的正则
    public static final String MATCHES_EMAIL = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";// 邮箱
    public static final String MATCHES_BANKCARD = "^[0-9]{16,19}$";// 银行卡
    public static final String MATCHES_PHONE = "^[1][0-9]{10}$";// 手机号码
    public static final String MATCHES_PASSWORD =
            "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6," + "22}$";// 银行卡
    public static final String MATCHES_IP =
            "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}" + "(25[0-5]|2[0-4" +
                    "]\\d|((1\\d{2})|([1-9]?\\d)))";
    public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";//
    public static final String DATE_FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";//
    public static final String DATE_FORMAT_YMD_T_HMS = "yyyy-MM-dd'T'HH:mm:ss";//
    public static final String DATE_FORMAT_YMD_HM = "yyyy-MM-dd HH:mm";//
    public static final String DATE_FORMAT_YMD_H = "yyyy-MM-dd HH";//
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";//
    public static final String DATE_FORMAT_YM = "yyyy-MM";//
    public static final String DATE_FORMAT_HMS = "HH:mm:ss";//
    public static final String DATE_FORMAT_HM = "HH:mm";//
    public static final String DATE_FORMAT_YMD_ = "yyyyMMdd";//


    public static final String SP_KEY_LOGIN_PHONE = "sp_key_login_phone";//登录人的手机号
    public static final int TYPE_LEVEL_1 = 10;
    public static final int TYPE_LEVEL_2 = 20;
    public static final int TYPE_LEVEL_3 = 30;
    public static final int TYPE_LEVEL_4 = 40;
    public static final int TYPE_LEVEL_5 = 50;
    public static final int TYPE_LEVEL_6 = 60;


    public static final String SP_KEY_ACCOUNT = "sp_key_account";
    public static final String SP_KEY_PASSWORD = "sp_key_password";
    public static final String SP_KEY_REMEMBER_PASSORD = "sp_key_remember_passord";
    public static final String SP_KEY_IMEI = "sp_key_imei";
    public static final String SP_KEY_SEND_MSG_TIMER = "sp_key_send_msg_timer";
    public static final String SP_KEY_SEND_MSG_TIMER_TIME = "sp_key_send_msg_timer_time";


    public static final int REQUEST_CODE_RESULT_BASE = 0x999;
    public static final int REQUEST_CODE_RESULT_REFRESH = 0x998;
    public static final String SP_KEY_TIMER_OUT_BUTTOM_CURRENT = "sp_key_timer_out_buttom_current";
    public static final String DEAL_URL_CLOSE_WINDOW = "closewindow:";//分享
    public static final int IMG_SELECT_MAX_NUM = 9;
    public static final String SP_KEY_EXAM_ID = "sp_key_exam_id";
    public static final String SP_KEY_EXAM_NAME = "sp_key_exam_name";
    public static final String SP_KEY_SUBJECT_ID = "sp_key_subject_id";
    public static final String SP_KEY_SUBJECT_NAME = "sp_key_subject_name";

    public static final String SP_KEY_CATEGORY_ID = "key_category_id";
    public static final String SP_KEY_CATEGORY_NAME = "key_category_name";
    public static final String SP_KEY_LAST_VERSION = "key_last_version";
    public static final String SP_VIDEO_STUDY_RECORD_ID = "sp_video_study_record_id";
    public static final String SP_KEY_STUDY_START_TIME = "SP_KEY_STUDY_START_TIME";
    /*是否同意服务协议和隐私政策*/
    public static final String SP_KEY_USE_GRANTED = "sp_key_use_granted";
    //乐播投屏初始化状态
    public static final String SP_KEY_LELINK_INIT = "sp_key_lelink_init";
    //是否有权限做题
    public static final String SP_KEY_USER_HAS_ORDER = "sp_key_user_has_order";
    //最后一次发验证码的时间
    public static final String SP_KEY_LAST_SEND_SMS_TIME = "SP_KEY_LAST_SEND_SMS_TIME";
    /*发送验证码时间间隔*/
    public static final int SEND_MSG_INTERVAL = 60;

    public static final String SP_WX_PAY_TYPE = "sp_wx_pay_type";
    //设备id
    public static final String SP_KEY_DEVICE_ID = "sp_key_device_id";
    /*题库相关路径*/
    public static final String GROUP_EXAM = "exam";
    public static final int GROUP_EXAM_INTERCEPTOR = 99;
    public static final String GROUP_EXAM_ACTIVITY_MAIN = "/" + GROUP_EXAM + "/activitymain";
    public static final String GROUP_EXAM_ACTIVITY_TEST = "/" + GROUP_EXAM + "/activitytest";
    public static final String GROUP_EXAM_SERVICE_MAIN = "/" + GROUP_EXAM + "/servicemain";
    public static final String GROUP_EXAM_PRETREATMENT_SERVICE_MAIN = "/" + GROUP_EXAM + "/PretreatmentServicemain";
    public static final String GROUP_EXAM_DEGRADE_SERVICE_MAIN = "/" + GROUP_EXAM + "/DegradeServicemain";
    public static final String GROUP_EXAM_REPLACESERVICE_MAIN = "/" + GROUP_EXAM + "/ReplaceServicemain";

    public static final String SP_KEY_ERROR_COUNT_TO_REMOVE_ERROR_BOOK = "sp_key_error_count_to_remove_error_book";
    public static final String SP_KEY_SHOW_ERROR_COUNT_TO_REMOVE_ERROR_BOOK_TIPS =
            "sp_key_show_error_count_to_remove_error_book_tips";
    //    做题模式
    public static final String SP_KEY_EXAM_MODEL = "sp_key_exam_model";
}

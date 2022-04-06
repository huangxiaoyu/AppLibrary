package com.hxy.library.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hxy.library.common.BaseApplication;
import com.hxy.app.librarycore.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {
    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打电话
     *
     * @param context
     * @param phone   电话号码
     */
    public static void telTo(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            showToast(context, "未获得权限,呼叫失败");
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 发短信
     *
     * @param context
     * @param phone      短信接收者
     * @param defaultMsg 短信内容
     */
    public static void sendSms(Context context, String phone, String defaultMsg) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        if (!TextUtils.isEmpty(defaultMsg)) {
            intent.putExtra("sms_body", defaultMsg);
        }
        context.startActivity(intent);
    }

    /**
     * 分享 系统分享
     *
     * @param context
     * @param shareStr 分享内容
     */
    public static void share(Context context, String shareStr) {
        Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
        intent.setType("text/plain"); // 分享发送的数据类型
        intent.putExtra(Intent.EXTRA_TEXT, shareStr); // 分享的内容
        context.startActivity(Intent.createChooser(intent, "分享到"));// 目标应用选择对话框的标题
    }

    public static List<String> getList() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("java");
        stringArrayList.add("java");
        stringArrayList.add("java");
        stringArrayList.add("java");
        stringArrayList.add("java");
        return stringArrayList;
    }

    public static boolean isLogin(Context context) {
        return context != null ? (boolean) SPUtils.get(context, Constants.SP_KEY_ISLOGIN, false)
                : false;
    }

    public static boolean isLogin() {
        return (boolean) SPUtils.get(BaseApplication.getApplication().getApplicationContext(),
                Constants.SP_KEY_ISLOGIN, false);
    }

//    public static void showShare(String platform, String title, String titleUrl, String content, String imgUrl,
//                                 String url) {
//        showShare(platform, title, titleUrl, content, imgUrl, null, url, null);
//    }
//
//    public static void showShare(String platform, String title, String titleUrl, String content, Bitmap bitmap,
//                                 String url) {
//        showShare(platform, title, titleUrl, content, null, bitmap, url, null);
//    }
//
//    public static void showShare(String platform, String title, String titleUrl, String content, String imgUrl,
//                                 Bitmap bitmap,
//                                 String url, PlatformActionListener callBack) {
//        final OnekeyShare oks = new OnekeyShare();
//        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
//        if (platform != null) {
//            oks.setPlatform(platform);
//        }
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(title);
//        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//        oks.setTitleUrl(titleUrl);
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(content);
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        if (!TextUtils.isEmpty(imgUrl)) {
//            oks.setImageUrl(imgUrl);
//        } else {
//            oks.setImageData(bitmap);
//        }
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(url);
//        oks.setCallback(callBack);
//        //启动分享
//        oks.show(MobSDK.getContext());
//    }
//
//
//    public static void submitPolicyGrantResult(boolean granted) {
//        MobSDK.submitPolicyGrantResult(granted, null);
//    }

    public static String getDate(String format, long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static String getDate(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(calendar.getTime());
    }

    /**
     * 去除cookie转成json,方便替换,避免重复赋值
     *
     * @param context
     * @param url
     * @param key     添加cookie的jsonkey
     * @param value   添加cookie的json值
     */
    public static void syncCookie(Context context, String url, String key, String value) {
        if (!TextUtils.isEmpty(url))
            if (!TextUtils.isEmpty(key)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.createInstance(context);
                }
                CookieManager cookieManager = CookieManager.getInstance();
                String cookieSrc = cookieManager.getCookie(url);
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();// 移除
                cookieManager.removeAllCookie();
                StringBuilder sbCookie = new StringBuilder();//创建一个拼接cookie的容器,
                // 为什么这么拼接，大家查阅一下http头Cookie的结构
                try {
                    if (!TextUtils.isEmpty(cookieSrc)) {
                        JSONObject jsonObject = JSON.parseObject(cookieSrc);
                        jsonObject.put(key, value);
                        sbCookie.append(JSON.toJSONString(jsonObject));//追加新的cookie
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(key, value);
                        sbCookie.append(JSON.toJSONString(jsonObject));//追加新的cookie
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                String cookieValue = sbCookie.toString();
                cookieManager.setCookie(url, cookieValue);//为url设置cookie
                CookieSyncManager.getInstance().sync();//同步cookie
                String newCookie = cookieManager.getCookie(url);
                //                LogUtils.e("同步后cookie", newCookie);
            }
    }

    public static File compressImage(String filePaht, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        File file = new File(filePaht);
        if (file != null && file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

//    public static void appShare(Context context, String shareStr) {
//        JSONObject object = JSON.parseObject(shareStr);
//        try {
//            showShare(null, object.getString("title"), object.getString("titleUrl"), object
//                            .getString("msg"), object.getString("imgUrl"), null,
//                    object.getString("url"), null);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            SweetAlertDialogFactory.build(context, SweetAlertDialog.ERROR_TYPE).setContentText(ex
//                    .getMessage()).show();
//        }
//    }

    public static String getHMS(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTdate(String str) {
        return !TextUtils.isEmpty(str) && str.contains("T") ? str.substring(0, 19).replace("T",
                "" + " ") : (!TextUtils.isEmpty(str) ? str : "");

    }

    /**
     * 保留2位小数
     *
     * @param distace
     * @return
     */
    public static String getDistance(double distace) {
        if (distace >= 1000) {
            return String.format("%.2f公里", distace / 1000);
        } else {
            return String.format("%.2f米", distace);
        }
    }


    public static Uri resToUri(Context context, int resourceId) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + resourceId);
    }

    public static String getTimeMarK(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar currentDate = Calendar.getInstance();
            if (currentDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR) > 0) {
                return (currentDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)) + "年前";
            } else if (currentDate.get(Calendar.MONTH) - calendar.get(Calendar.MONTH) > 0) {
                return (currentDate.get(Calendar.MONTH) - calendar.get(Calendar.MONTH)) + "月前";
            } else if (currentDate.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH) > 0) {
                return (currentDate.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH)) + "天前";
            } else if (currentDate.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY) > 0) {
                return (currentDate.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY)) + "小时前";
            } else if (currentDate.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE) > 0) {
                return (currentDate.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE)) + "分钟前";
            } else {
                return "刚刚";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "刚刚";
    }

    public static void reportError(Throwable error) {
        MobclickAgent.reportError(BaseApplication.getApplication(), error);
    }

    public static void reportError(Context applicationContext, String error) {
        MobclickAgent.reportError(applicationContext, error);
    }

    public static void reportError(Context applicationContext, Throwable ex) {
        MobclickAgent.reportError(applicationContext, ex);
    }

    /**
     * 2022-03-28之后改用用户id,之前是phone
     *
     * @param userName
     */
    public static void onProfileSignIn(String userName) {
        MobclickAgent.onProfileSignIn(userName);
    }

    public static void onProfileSignOff() {
        MobclickAgent.onProfileSignOff();
    }

    public static boolean isInstallApp(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                //通过遍历应用所有包名进行判断
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static File getRootDir(Context context) {
        String state = Environment.getExternalStorageState();
        File root;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            root = context.getApplicationContext().getExternalFilesDir("");
        } else {
            root = context.getApplicationContext().getFilesDir();
        }
        return root;
    }

    public static File getRootDir(Context context, String type) {
        String state = Environment.getExternalStorageState();
        File root;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            root = context.getApplicationContext().getExternalFilesDir(type);
        } else {
            String path = context.getApplicationContext().getFilesDir().getPath();
            root = new File(path + File.separator + type);
            if (!root.exists() || !root.isFile()) {
                root.mkdirs();
            }
        }
        return root;
    }

    public static void imgPreview(Fragment fragment, ArrayList<LocalMedia> datas) {
        PictureSelector.create(fragment)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo
                // GlideEngine.java
                .openExternalPreview(0, datas);
    }

    public static void imgPreview(Fragment fragment, ArrayList<LocalMedia> datas, int firstPreIndex) {
        PictureSelector.create(fragment)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo
                // GlideEngine.java
                .openExternalPreview(firstPreIndex, datas);
    }

    public static void imgPreview(Activity activity, ArrayList<LocalMedia> datas) {
        PictureSelector.create(activity)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo
                // GlideEngine.java
                .openExternalPreview(0, datas);
    }

    public static void imgPreview(Activity activity, ArrayList<LocalMedia> datas, int firstPreIndex) {
        PictureSelector.create(activity)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo
                // GlideEngine.java
                .openExternalPreview(firstPreIndex, datas);
    }
}

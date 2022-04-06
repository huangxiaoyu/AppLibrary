package com.hxy.library.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.alibaba.fastjson.JSON;
import com.hxy.library.common.utils.Constants;
import com.hxy.library.common.utils.LogUtils;
import com.hxy.library.common.utils.SPUtils;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 项目：LocationDemo  包名：com.hxy.app.locationdemo
 * <p> 百度回去城市列表:http://map.baidu.com/?qt=sub_area_list&areacode=1&level=1&from=mapapi,
 * areacode为1代表中国 获取省份信息 传入省份code获取城市信息.传入城市获取区域信息
 * huangxiaoyu
 * <p>
 * 2018/6/9
 * <p>
 * desc
 */
public class BaseApplication extends Application {
    public static String DEVICEID = null;
    public int width, height;
    static BaseApplication mApplication;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    public boolean isGatherStarted = false;
//    public LocationService locationService;

    public static BaseApplication getApplication() {
        return mApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DEVICEID = SPUtils.get(this, Constants.SP_KEY_DEVICE_ID, "").toString();
        if (TextUtils.isEmpty(DEVICEID)) {
            DEVICEID = getUniqueID(this);
            SPUtils.put(this, Constants.SP_KEY_DEVICE_ID, DEVICEID);
        }
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        // 初始化 JPush
        //        frescoInit();
        mApplication = this;
        String[] deviceInfo = new String[2];
        deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(getApplicationContext());
        deviceInfo[1] = DeviceConfig.getMac(getApplicationContext());
        LogUtils.i(JSON.toJSONString(deviceInfo));
    }

    /**
     * 获取请求标识
     *
     * @return
     */
    public int getTag() {
        return mSequenceGenerator.incrementAndGet();
    }

    private String getUniqueID(Context context) {
        String id = null;
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId) && "9774d56d682e549c" != androidId) {
            try {
                UUID uuid = UUID.nameUUIDFromBytes(androidId.getBytes());
                id = uuid.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(id)) {
            id = getUUID();
        }
        return TextUtils.isEmpty(id) ? UUID.randomUUID().toString() : id;
    }

    private String getUUID() {
        String serial = null;
        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + (
                !TextUtils.isEmpty(Build.CPU_ABI) ? Build.CPU_ABI.length() : 0) %
                10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;//13 位
        if (Build.VERSION.SDK_INT <= 29) {
            try {
                serial = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? Build.getSerial() : Build.SERIAL;
                //API>=9 使用serial号
                return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            } catch (Exception exception) {
                serial = "serial"; // 随便一个初始化
            }
        } else {
            serial = Build.UNKNOWN; // 随便一个初始化
        }

        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 各module获取自身Retrofit接口实例
     */
//    public RestClient getRestClient() {
//        return null;
//    }
    public String getMateData(String key) {
        try {
            Bundle metaData = this.getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA).metaData;
            Object o = metaData.get(key);
            LogUtils.i(String.format("%s的类型是:%s", key, o.getClass().getCanonicalName()));
            String dataString = String.valueOf(o);
//            String dataString = metaData.getString(key);
//            if (o instanceof Integer) {
//                dataString = metaData.getInt(key) + "";
//            }
            LogUtils.i(String.format("matedata:%s=%s", key, dataString));
            return dataString;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}

package com.hxy.library.common.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/7/23
 * <p>
 * desc
 */
public class RetryConnInterceptor implements Interceptor {
    Context mContext;
    public int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public RetryConnInterceptor(Context context, int maxRetry) {
        this.mContext = context;
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.i("RetryConnInterceptor", "num:" + retryNum);
        String strResult = "";
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            strResult = buffer.clone().readString(UTF8);
            buffer = null;
        }
        if (retryNum == 0 && (!response.isSuccessful() || (!TextUtils.isEmpty(strResult) &&
                !strResult.contains("\"type\":1")))) {
            Buffer buffer = new Buffer();
            RequestBody requestBody = request.body();
            requestBody.writeTo(buffer);
            JSONObject object = new JSONObject();
            object.put("url", request.url().toString());
            object.put("requestbody", buffer.readString(UTF8));
            buffer = null;
            object.put("responsebody", strResult);
            //自发现异常捕获
//            MobclickAgent.reportError(MineApplication.getApplication(), new ApiException(-1, JSON
//                    .toJSONString(object, SerializerFeature.WriteSlashAsSpecial)));
        }
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            Log.i("RetryConnInterceptor", "num:" + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}

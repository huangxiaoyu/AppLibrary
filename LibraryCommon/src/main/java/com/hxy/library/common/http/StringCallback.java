package com.hxy.library.common.http;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hxy.library.common.rx.RxBus;
import com.hxy.library.common.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Huang on 2016/7/28.
 */
public abstract class StringCallback implements Callback<ResponseBody> {
    Context mContext;

    public StringCallback(Context context) {
        mContext = context;
    }

    public abstract void onResponse(String result);

    public abstract void onFailure(String error);

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ResponseBody body = response.body();
        try {
            if (null != body && response.isSuccessful()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(body.byteStream
                        ()));
                StringBuilder out = new StringBuilder();
                String newLine = System.getProperty("line.separator");//换行符号
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                    out.append(newLine);
                }
                // Prints the correct String representation of body.
                Log.i("onResponse", URLEncoder.encode(out.toString(), "utf-8"));
                String result = out.toString();
                if (result.contains("\"errorCode\":-2")) {//token过期 提示登录
                    RxBus.getDefault().post(RxBus.RX_TOKEN_TIMEOUT, "无登录信息或登录已失效");
                } else {
                    onResponse(out.toString());
                }
            } else {
                onFailure(call, new Exception("请求失败"));
                //                JSONObject object = new JSONObject();
                //                object.put("id", 1);
                //                object.put(Constants.RESULT_MSG, "数据请求失败");
                //                onResponse(object.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(call, e);
        }
    }


    @Override
    public void onFailure(Call call, Throwable t) {
        String requestStr = call.request().toString();
        String paramStr = "";
        Buffer buffer = new Buffer();
        try {
            RequestBody requestBody = call.request().body();
            if (requestBody != null) {
                requestBody.writeTo(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        paramStr = buffer.clone().readUtf8();
        buffer = null;
        JSONObject object = new JSONObject();
        object.put("request", requestStr);
        object.put("param", paramStr);
        MobclickAgent.reportError(mContext.getApplicationContext(), JSON.toJSONString(object) + t);
        LogUtils.e("onFailure", t.getMessage());
        t.printStackTrace();
        if (t instanceof SocketTimeoutException) {
            onFailure("请求超时");
        } else if (t instanceof IOException) {
            onFailure("数据解析失败");
        } else {
            onFailure("请求失败");
        }
    }
}

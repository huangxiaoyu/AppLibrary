package com.hxy.library.common.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/8/5.
 * md5加密工具
 */
public class Md5Utils {
    public static String md5Encode(String source){
        try {
            MessageDigest md5Digester = MessageDigest.getInstance("MD5");
            //将原字符串转换为字节数组更新到MessageDigest中
            md5Digester.update(source.getBytes());
            //md5加密
            byte[] digested = md5Digester.digest();
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<digested.length; i++){
                //将字节转化为整数
                int b = digested[i] & 0xff;
                //将整数转化为16进制字符串
                String hexStr = Integer.toHexString(b);
                //判断16进制的字符串是否只有1位，如果ture则加上0；
                if (hexStr.length() == 1){
                    sb.append("0"+hexStr);
                }else {
                    sb.append(hexStr);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String md5Encode(String source,String salt){
        return md5Encode(source+salt);
    }
}

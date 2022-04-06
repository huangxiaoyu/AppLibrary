package com.hxy.library.common.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * huangxiaoyu 处理activity传值数据量过大的问题
 * 2020/8/3 0003 09:43
 */
public class SoftDataHolder {
    private static SoftDataHolder instance;

    public static SoftDataHolder getInstance() {
        if (instance == null) {
            synchronized (SoftDataHolder.class) {
                if (instance == null) {
                    instance = new SoftDataHolder();
                }
            }
        }
        return instance;
    }

    private Map<String, SoftReference<Object>> map = new HashMap<>();

    /**
     * 数据存储
     *
     * @param id
     * @param object
     */
    public void saveData(String id, Object object) {
        if (map.containsKey(id)) {
            map.remove(id);
        }
        map.put(id, new SoftReference<>(object));
    }

    /**
     * 获取数据
     *
     * @param id
     * @return
     */
    public Object getData(String id) {
        SoftReference<Object> softReference = map.get(id);
        return softReference.get();
    }

    /**
     * 移除引用 释放空间
     *
     * @param id
     * @return
     */
    public void remove(String id) {
        if (map.containsKey(id)) {
            SoftReference<Object> softReference = map.get(id);
            map.remove(id);
            Object o = softReference.get();
            o = null;
        }
    }
}

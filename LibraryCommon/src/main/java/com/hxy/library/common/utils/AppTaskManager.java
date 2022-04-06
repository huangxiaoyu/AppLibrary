package com.hxy.library.common.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by fei on 2016/7/25.
 */
public class AppTaskManager {
    // Activity栈
    private static Stack<Activity> activityStack;
    // 单例模式
    private static AppTaskManager instance;
    private static HashMap<String, Activity> activityHashMap;

    public AppTaskManager() {
        activityHashMap = new HashMap<>();
    }

    /**
     * 单一实例
     */
    public static AppTaskManager getAppManager() {
        if (instance == null) {
            instance = new AppTaskManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
        String name = getActivityName(activity);
        activityHashMap.put(name, activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity.getClass().getCanonicalName());
    }

    public Activity getActivity(String name) {
        if (activityHashMap.containsKey(name)) {
            return activityHashMap.get(name);
        }
        return null;
    }

    public Activity getActivity(Class cClass) {
        if (activityHashMap.containsKey(cClass.getCanonicalName())) {
            return activityHashMap.get(cClass.getCanonicalName());
        }
        return null;
    }

    /**
     * 获取栈的长度
     */
    public static int getTaskLength() {
        return activityStack.size();
    }


    public void finishActivity(String name) {
        try {
            Activity activity = activityHashMap.get(name);
            activityStack.remove(activity);
            activityHashMap.remove(name);
            activity.finish();
            activity = null;
        } catch (Exception ex) {
            return;
        }
    }


    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityHashMap.clear();
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        finishAllActivity();
    }

    private String getActivityName(Activity activity) {
        return activity.getClass().getCanonicalName();

    }


    public void removeActivity(Activity activity) {
        if (activity != null) {
            String activityName = getActivityName(activity);
            if (activityHashMap.containsKey(activityName)) {
                activityHashMap.remove(activityName);
            }
            activityStack.remove(activity);

        }

    }
}

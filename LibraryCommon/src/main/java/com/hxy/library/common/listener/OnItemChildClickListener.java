package com.hxy.library.common.listener;

import android.view.View;

/**
 * 项目：MeiYueHongJiu  包名：com.hxy.app.locationdemo.listener
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/5/16
 * <p>
 * desc
 */
public interface OnItemChildClickListener {
    /**
     * item中child控件的点击事件回调
     *
     * @param view      触发点击事件的控件id,
     * @param groupPostion     可以作父节点id传输
     * @param childPostion 触发事件itme索引
     */
    void onItemChildClickListener(View view, int groupPostion, int childPostion);
}

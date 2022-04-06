package com.hxy.library.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 2018/10/30 18:14
 * <p>
 * huangxiaoyu
 * <p>
 * desc
 */
public class MarqueeText extends AppCompatTextView {
    public MarqueeText(Context context) {
        super(context);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //返回textview是否处在选中的状态
    //而只有选中的textview才能够实现跑马灯效果
    @Override
    public boolean isFocused() {
        return true;
    }
}

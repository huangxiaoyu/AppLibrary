package com.hxy.library.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.hxy.app.librarycore.R;

/**
 * huangxiaoyu
 * 2020/7/15 0015 10:49
 */
public class OvalImageView extends androidx.appcompat.widget.AppCompatImageView {
    int roundingRadius = 5;
    int roundingType = 0;//01234 全部,上圆角下圆角左圆角右圆角;
    private float[] rids;

    public OvalImageView(Context context) {
        super(context);
        init(context, null);
    }

    public OvalImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public OvalImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    //此处可根据自己需要修改大小
//    private float[] rids = {SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8),
//    SizeUtils.dp2px(8), 0.0f,
//            0.0f, 0.0f, 0.0f,};
    void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.OvalImageView);
            if (typedArray != null) {
                if (typedArray.hasValue(R.styleable.OvalImageView_roundingRadius)) {
                    roundingRadius = typedArray.getInt(R.styleable.OvalImageView_roundingRadius,
                            roundingRadius);
                }
                if (typedArray.hasValue(R.styleable.OvalImageView_roundingRadius)) {
                    roundingType = typedArray.getInt(R.styleable.OvalImageView_roundingType,
                            roundingType);
                }
                typedArray.recycle();
            }
        }
        if (roundingType == 0) {
            rids = new float[]{SizeUtils.dp2px(roundingRadius), SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius), SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius), SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius)};
        } else if (roundingType == 1) {
            rids = new float[]{SizeUtils.dp2px(roundingRadius), SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius), SizeUtils.dp2px(roundingRadius),
                    0f, 0f, 0f, 0f};
        } else if (roundingType == 2) {
            rids = new float[]{0f, 0f, 0f, 0f, SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius)};
        } else if (roundingType == 3) {
            rids = new float[]{SizeUtils.dp2px(roundingRadius), SizeUtils.dp2px(roundingRadius),
                    0f, 0f, 0f, 0f,
                    SizeUtils.dp2px(roundingRadius), SizeUtils.dp2px(roundingRadius)};
        } else if (roundingType == 4) {
            rids = new float[]{0f, 0f, SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius),
                    SizeUtils.dp2px(roundingRadius), 0f, 0f};
        }

    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        setMeasuredDimension(width, width);
//    }

    /**
     * 画图
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);

    }
}

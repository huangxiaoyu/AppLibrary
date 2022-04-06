package com.hxy.library.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hxy.app.librarycore.R;

/**
 * huangxiaoyu
 * 2020/4/15 0015 14:32
 */
public class CircleProgress extends View {


    public int progress = 50;//进度实际值,当前进度
    /**
     * 自定义控件属性，可灵活的设置圆形进度条的大小、颜色、类型等
     */
    private int mR;//圆半径，决定圆大小
    private int bgColor;//圆或弧的背景颜色
    private int fgColor;//圆或弧的前景颜色，即绘制时的颜色
    private int drawStyle; //绘制类型 FILL画圆形进度条，STROKE绘制弧形进度条
    private int strokeWidth;//STROKE绘制弧形的弧线的宽度
    private int max = 100;//最大值，设置进度的最大值
    Paint mPaint;
    Context mContext;
    boolean opt;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > max) {
            progress = max;
        } else {
            this.progress = progress;
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    private void initProperty(AttributeSet attrs) {
        TypedArray tArray = mContext.obtainStyledAttributes(attrs, R.styleable
                .CircleHollowProgressBar);
        mR = tArray.getInteger(R.styleable.CircleHollowProgressBar_r, 0);
        bgColor = tArray.getColor(R.styleable.CircleHollowProgressBar_bgColor, Color.TRANSPARENT);
        fgColor = tArray.getColor(R.styleable.CircleHollowProgressBar_fgColor, Color.WHITE);
        drawStyle = tArray.getInt(R.styleable.CircleHollowProgressBar_drawStyle, 0);
        strokeWidth = tArray.getInteger(R.styleable.CircleHollowProgressBar_stroke, 5);
        progress = tArray.getInteger(R.styleable.CircleHollowProgressBar_progress, 0);
        max = tArray.getInteger(R.styleable.CircleHollowProgressBar_max, 100);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true); // 消除锯齿
        this.mPaint.setStyle(Paint.Style.STROKE); // 绘制空心圆或 空心矩形
        initProperty(attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2; // 圆心位置
        mPaint.setColor(bgColor);
        mPaint.setStrokeWidth(strokeWidth);
        mR = mR == 0 ? center : mR;
        canvas.drawCircle(center, center, mR - strokeWidth, mPaint);
        // 绘制圆环
        mPaint.setColor(fgColor);
        if (drawStyle == 0) {
            mPaint.setStyle(Paint.Style.STROKE);
            opt = false;
        } else {
            mPaint.setStyle(Paint.Style.FILL);
            opt = true;
        }
        int top = (center - mR) + strokeWidth;
        int bottom = (center + mR) - strokeWidth;
        RectF oval = new RectF(top, top, bottom, bottom);
        canvas.drawArc(oval, 270, 360 * progress / (max < 1 ? 1 : max), opt, mPaint);

    }
}
package com.hxy.library.common.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Administrator
 * 2022/3/15 0015
 */
public class RadiusBackgroundSpan extends ReplacementSpan {
    private int mSize;
    private int mBgColor;
    private int mBorderColor;
    private int mColor;
    private int mRadius;

    public RadiusBackgroundSpan(int color, int radius) {
        mColor = color;
        mRadius = radius;
    }

    public RadiusBackgroundSpan(int bgColor, int color, int radius) {
        mBgColor = bgColor;
        mColor = color;
        mRadius = radius;
    }

    public RadiusBackgroundSpan(int bgColor, int borderColor, int color, int radius) {
        mBgColor = bgColor;
        mBorderColor = borderColor;
        mColor = color;
        mRadius = radius;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end) + 2 * mRadius);
        //mSize就是span的宽度，span有多宽，开发者可以在这里随便定义规则
        //我的规则：这里text传入的是SpannableString，start，end对应setSpan方法相关参数
        //可以根据传入起始截至位置获得截取文字的宽度，最后加上左右两个圆角的半径得到span宽度
        return mSize;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, @NonNull Paint paint) {
        int color = mColor != 0 ? mColor : paint.getColor();//保存文字颜色
        if (mBgColor != 0) {
            paint.setColor(mBgColor);//设置背景颜色
            paint.setAntiAlias(true);// 设置画笔的锯齿效果
            RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
            //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
            canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
        }
        if (mBorderColor != 0) {
            paint.setColor(mBorderColor);//设置边框颜色
            paint.setStyle(Paint.Style.STROKE);// 设置画笔的空心
            paint.setStrokeWidth(2.0f);//设置边框粗细
            RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
            //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
            canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
        }
        paint.setStyle(Paint.Style.FILL);// 设置画笔的空心
        paint.setColor(color);//恢复画笔的文字颜色
        paint.setTextSize(paint.getTextSize() - 6);
//        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, start, end, x + mRadius+6, y, paint);//绘制文字
    }
}

package com.hxy.library.common.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * huangxiaoyu
 * 2020/4/22 0022 13:51
 */
public class WaveView extends View {
    int waterLevel = 4;//水位.波浪的平均高度
    int amplitude = 20;//波浪上下浮动限值
    int color = Color.parseColor("#99FFFFFF");//波浪的颜色
    int line = 5;//波浪的条数
    int speed = 6000;//波浪速度 毫秒单位
    private int waveWidth = 300;//波浪的宽度
    private ValueAnimator mAnimator;//结合ValueAnimator让它动起来
    private int mOffsetX;
    private Paint mPaint;
    private Path mBezierPath;
    private Path mPointPath;

    private Point mStartPoint;
    private Point mControlPoint;
    private Point mEndPoint;

    public WaveView(Context context) {
        super(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);//线宽

        mBezierPath = new Path();
//        mPointPath = new Path();
//
//        mStartPoint = new Point();
//        mStartPoint.set(100, 300);
//        mControlPoint = new Point();
//        mControlPoint.set(300, 100);
//        mEndPoint = new Point();
//        mEndPoint.set(500, 500);

        mAnimator = ValueAnimator.ofInt(0, waveWidth);
        mAnimator.addUpdateListener(animation -> {
            mOffsetX = (int) animation.getAnimatedValue();
            invalidate();
        });

        mAnimator.setInterpolator(new LinearInterpolator());

        mAnimator.setDuration(speed);//定义波浪的速度
        mAnimator.setRepeatCount(-1);
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        //贝塞尔
//        //传入起始点
//        mBezierPath.moveTo(mStartPoint.x, mStartPoint.y);
//        //传入控制点和终点
//        mBezierPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);
//        /*第二段曲线的起点是第一段曲线的终点，且可以发现，rQuadTo传的控制点(200,300)并非坐标，
//        而是相对于第一段曲线的终点(500,500)来计算，即(500+200, 500+300)
//        才是第二段曲线控制点的真正坐标，同理第二段曲线终点的坐标是(500+40, 500-200)*/
//        mBezierPath.rQuadTo(200, 300, 400, -200);//相对于endpoint的点追加一段曲线.
//        //连接线
//        mPointPath.moveTo(mStartPoint.x, mStartPoint.y);
//        mPointPath.lineTo(mControlPoint.x, mControlPoint.y);
//        mPointPath.lineTo(mEndPoint.x, mEndPoint.y);
//
//        //绘制起始点、控制点、终点的连线
//        canvas.drawPath(mPointPath, mPaint);
//
//        //绘制贝塞尔
//        mPaint.setColor(Color.RED);
//        canvas.drawPath(mBezierPath, mPaint);

//        mBezierPath.reset();
//        int halfItem = waveWidth / 2;
//        mBezierPath.moveTo(0, 300);
//        /*每段波浪的宽度定义为600，因此每半段波浪的高度为300，首先将起点移动到（0，300）处，即整个View最左侧的一个点，
//        接着开始遍历绘制后续多段波浪，mPath.rQuadTo(halfItem/2, -100, halfItem, 0);表示右移半个波浪， 并且上移100，
//        即一个浪的最高点，接着mPath.rQuadTo(halfItem/2, 100, halfItem, 0);再右移半个波浪，并且下移100，即一个浪的最低点
//        ，这就形成了一段完整的波浪，然后以此循环，直到超过View的最大宽度 */
//        for (int i = 0; i < waveWidth + getWidth(); i += waveWidth) {
//            mBezierPath.rQuadTo(halfItem / 2, -limit, halfItem, 0);
//            mBezierPath.rQuadTo(halfItem / 2, limit, halfItem, 0);
//        }
//        canvas.drawPath(mBezierPath, mPaint);


//        mBezierPath.reset();
//        int halfItem = waveWidth / 2;
//        /*起点改为了mPath.moveTo(-mItemWidth + mOffsetX, halfItem)，为何不是(mOffsetX, halfItem)呢，
//        因为mOffsetX的变化范围是从0到mItemWidth，如果一开始不减去mItemWidth，就会导致启动动画之后波浪左边总是会露出一段空白区域，
//        整个动画衔接不起来，无法形成无限循环的视觉效果*/
//        //必须先减去一个浪的宽度，以便第一遍动画能够刚好位移出一个波浪，形成无限波浪的效果
//        mBezierPath.moveTo(-waveWidth + mOffsetX, halfItem);
//        for (int i = -waveWidth; i < waveWidth + getWidth(); i += waveWidth) {
//            mBezierPath.rQuadTo(halfItem / 2, -100, halfItem, 0);
//            mBezierPath.rQuadTo(halfItem / 2, 100, halfItem, 0);
//        }
//        canvas.drawPath(mBezierPath, mPaint);


        mBezierPath.reset();
        int halfItem = waveWidth / 2;
        //必须先减去一个浪的宽度，以便第一遍动画能够刚好位移出一个波浪，形成无限波浪的效果
        mBezierPath.moveTo(-waveWidth + mOffsetX, ((getY() + getHeight()) / 10) * waterLevel);
        for (int i = -waveWidth; i < waveWidth + getWidth(); i += waveWidth) {
            mBezierPath.rQuadTo(halfItem / 2, -amplitude, halfItem, 0);
            mBezierPath.rQuadTo(halfItem / 2, amplitude, halfItem, 0);
        }

        //闭合路径波浪以下区域
        mBezierPath.lineTo(getWidth(), getHeight());
        mBezierPath.lineTo(0, getHeight());
        mBezierPath.close();
        canvas.drawPath(mBezierPath, mPaint);


    }
}

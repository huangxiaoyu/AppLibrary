package com.hxy.library.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager2.widget.ViewPager2;

/**
 * Administrator
 * 2021/12/31 0031
 */
public class ViewPager2Container extends RelativeLayout {
    private ViewPager2 mViewPager2;
    private boolean disallowParentInterceptDownEvent = true;
    private int startX = 0;
    private int startY = 0;

    public ViewPager2Container(Context context) {
        super(context);
    }

    public ViewPager2Container(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPager2Container(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewPager2Container(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView instanceof ViewPager2) {
                mViewPager2 = (ViewPager2) childView;
                break;
            }
        }
        if (mViewPager2 == null) {
            throw new IllegalStateException("The root child of ViewPager2Container must contains a ViewPager2");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean doNotNeedIntercept =
                mViewPager2 == null || !mViewPager2.isUserInputEnabled() || (mViewPager2.getAdapter() != null && mViewPager2.getAdapter().getItemCount() <= 1);
        if (doNotNeedIntercept) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(!disallowParentInterceptDownEvent);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = Math.abs(endX - startX);
                int disY = Math.abs(endY - startY);
                if (mViewPager2 != null && mViewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                    onVerticalActionMove(endY, disX, disY);
                } else if (mViewPager2 != null && mViewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    onHorizontalActionMove(endX, disX, disY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void onHorizontalActionMove(int endX, int disX, int disY) {
        if (mViewPager2 == null || mViewPager2.getAdapter() == null) {
            return;
        }
        if (disX > disY) {
            int currentItem = mViewPager2.getCurrentItem();
            int itemCount = mViewPager2.getAdapter().getItemCount();
            if (currentItem == 0 && endX - startX > 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(currentItem != itemCount - 1
                        || endX - startX >= 0);
            }
        } else if (disY > disX) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    private void onVerticalActionMove(int endY, int disX, int disY) {
        if (mViewPager2 == null || mViewPager2.getAdapter() == null) {
            return;
        }
        int currentItem = mViewPager2.getCurrentItem();
        int itemCount = mViewPager2.getAdapter().getItemCount();
        if (disY > disX) {
            if (currentItem == 0 && endY - startY > 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(currentItem != itemCount - 1
                        || endY - startY >= 0);
            }
        } else if (disX > disY) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    /**
     * ???????????????????????????View???{@link MotionEvent#ACTION_DOWN}??????????????????View??????????????????????????????
     * ????????????CoordinatorLayout+CollapsingToolbarLayout?????????ViewPager2Container?????????????????????????????????
     * <p>
     * ?????????????????????ViewPager2Container???{@link MotionEvent#ACTION_DOWN}??????????????????View??????????????????????????????
     * ????????????CoordinatorLayout+CollapsingToolbarLayout?????????ViewPager2Container?????????????????????????????????
     *
     * @param disallowParentInterceptDownEvent ????????????ViewPager2Container???{@link MotionEvent#ACTION_DOWN}??????????????????View
     *                                         ???????????????????????????false
     *                                         true ?????????ViewPager2Container???{@link MotionEvent#ACTION_DOWN}??????????????????View
     *                                         ??????????????????
     *                                         ??????disallowIntercept???true????????????CoordinatorLayout+CollapsingToolbarLayout
     *                                         ???????????????
     *                                         false ??????ViewPager2Container???{@link MotionEvent#ACTION_DOWN}??????????????????View
     *                                         ??????????????????
     */
    void disallowParentInterceptDownEvent(Boolean disallowParentInterceptDownEvent) {
        this.disallowParentInterceptDownEvent = disallowParentInterceptDownEvent;
    }

}

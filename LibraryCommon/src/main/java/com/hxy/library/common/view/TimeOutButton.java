package com.hxy.library.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.hxy.app.librarycore.R;
import com.hxy.library.common.utils.Constants;
import com.hxy.library.common.utils.SPUtils;

/**
 * huangxiaoyu  2019-06-15 14:36
 * <p>
 * desc
 */
public class TimeOutButton extends AppCompatButton {
    int unit = 1000;
    int resendTime = 10;//该值只能在init方法前调用 即获取xml默认参数可修改
    String phone;
    private CountDownTimer timer;
    String defaultText = "获取验证码", tipText = "秒后可重发";
    boolean started = false;
    OnstartListener mOnstartListener;

    public TimeOutButton(Context context) {
        super(context);
        init();
    }


    public TimeOutButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeOutButton);
        unit = array.getInt(R.styleable.TimeOutButton_unit, unit);
        resendTime = array.getInt(R.styleable.TimeOutButton_resendTime, resendTime);
        tipText = array.getString(R.styleable.TimeOutButton_tipText);
        if (TextUtils.isEmpty(tipText)) {
            tipText = "秒后可重发";
        }
        init();
    }

    public TimeOutButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeOutButton);
        unit = array.getInt(R.styleable.TimeOutButton_unit, unit);
        resendTime = array.getInt(R.styleable.TimeOutButton_resendTime, resendTime);
        tipText = array.getString(R.styleable.TimeOutButton_tipText);
        if (TextUtils.isEmpty(tipText)) {
            tipText = "秒后可重发";
        }
        init();
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" : phone;
    }


    /**
     * 响应点击事件  为避免onTouchEvent阻止自身的OnClickListener,需要重新设置一个回调
     *
     * @param onstartListener
     */
    public void setOnstartListener(OnstartListener onstartListener) {
        mOnstartListener = onstartListener;
    }

    private void init() {
        defaultText = getText().toString();
        int currentResendTime = (int) SPUtils.get(getContext(),
                Constants.SP_KEY_TIMER_OUT_BUTTOM_CURRENT, 0);
        timer = new CountDownTimer(currentResendTime > 0 ? currentResendTime * unit :
                resendTime * unit, unit) {
            @Override
            public void onTick(long millisUntilFinished) {
                int tiem = (int) (millisUntilFinished / 1000);
                setText(tiem + tipText);
                SPUtils.put(getContext(), Constants.SP_KEY_TIMER_OUT_BUTTOM_CURRENT, tiem);
            }

            @Override
            public void onFinish() {
                SPUtils.put(getContext(), Constants.SP_KEY_TIMER_OUT_BUTTOM_CURRENT, 0);
                setText(defaultText);
                started = false;
                setEnabled(true);
            }
        };
        if (currentResendTime > 0) {
            timer.start();//使用计时器 设置验证码的时间限制
            started = true;
            setEnabled(false);
        }
        setOnClickListener(v -> {
            if (mOnstartListener == null) {
                throw new NullPointerException("mClickListener is null");
            }
            mOnstartListener.onStart();
        });
//        setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP && !started) {
//                timer.onTick(resendTime * unit);
//                timer.start();//使用计时器 设置验证码的时间限制
//                started = true;
//                setEnabled(false);
//            }
//            return false;
//        });
    }

    public void start() {
        timer = new CountDownTimer(resendTime * unit, unit) {
            @Override
            public void onTick(long millisUntilFinished) {
                int tiem = (int) (millisUntilFinished / 1000);
                setText(tiem + tipText);
                SPUtils.put(getContext(), Constants.SP_KEY_TIMER_OUT_BUTTOM_CURRENT, tiem);
            }

            @Override
            public void onFinish() {
                SPUtils.put(getContext(), Constants.SP_KEY_TIMER_OUT_BUTTOM_CURRENT, 0);
                setText(defaultText);
                started = false;
                setEnabled(true);
            }
        };
        timer.start();//使用计时器 设置验证码的时间限制
        started = true;
        setEnabled(false);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN && !started) {
//            if (mOnstartListener == null) {
//                throw new NullPointerException("mClickListener is null");
//            }
//            mOnstartListener.onStart();
//        }
//        return super.onTouchEvent(event);
//    }

    public interface OnstartListener {
        void onStart();
    }
}

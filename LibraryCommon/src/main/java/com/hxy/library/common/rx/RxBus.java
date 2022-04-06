package com.hxy.library.common.rx;

/**
 * Created by jingbin on 16/5/17.
 */
public class RxBus {
    public static final int RX_SELECT_HOME_TAB = 0x0001;//切换首页tab
    public static final int RX_TOKEN_TIMEOUT = 0x0002;//token过期处理
    public static final int RX_REFRESH_USER_INFO = 0x0003;//用户更新信息后通知 fragmenttabmine更新数据
    /**
     * 答疑里面取消和关注后更新我的关注列表
     */
    public static final int RX_QA_ATTENTION = 0x0004;
    public static final int RX_REFRESH_MIAN_TAB_HOME_DATA = 0x0005;//刷新首页数据
    public static final int RX_EXAM_PAGER_TEST_TIMER = 0x0006;
    public static final int RX_SEND_MSG_CODE_TIMER = 0x0007;
    public static final int RX_REWARD_TEACHER_BY_WX = 0x0008;

    public static int RX_PAY_WEIXIN = 0x0009;
    public static final int RX_CAMP_EXAM_PAGER_NEXT_QUESTION = 0x1001;//训练营题库跳转
    public static final int RX_CAMP_EXAM_RETRY = 0x1002;//重新做题
    public static final int RX_CAMP_EXAM_KNOWLEDGE_UPDATE_GRASP_STATUS = 0x1003;//修改知识点掌握情况
    public static final int RX_CAMP_BUYED_STATEUS = 0x1004;//训练营购买情况
    public static final int RX_CAMP_HOMEWORK_FINISH_STATEUS = 0x1005;//课后作业做题情况


    /**
     * 参考网址: http://hanhailong.com/2015/10/09/RxBus%E2%80%94%E9%80%9A%E8%BF%87RxJava%E6%9D%A5%E6
     * %9B%BF%E6%8D%A2EventBus/
     * http://www.loongwind.com/archives/264.html
     * https://theseyears.gitbooks.io/android-architecture-journey/content/rxbus.html
     */
    private static volatile RxBus instance;

    private final io.reactivex.subjects.Subject<Object> _bus =
            io.reactivex.subjects.PublishSubject.create().toSerialized();

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void send(RxMessage obj) {
        if (_bus.hasObservers()) {
            _bus.onNext(obj);
        }
    }

    public void post(int code, Object o) {
        _bus.onNext(new RxMessage(code, o));

    }

    public io.reactivex.subjects.Subject<Object> toObservable() {
        return _bus;
    }

    public <T> io.reactivex.Observable<T> toObservable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }

    public <T> io.reactivex.Observable<T> toObservable(final int code, final Class<T> eventType) {
        return _bus.ofType(RxMessage.class).filter(rxMessage -> rxMessage.getCode() == code && eventType.isInstance(rxMessage.getObject())).map(rxMessage -> rxMessage.getObject()).cast(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return _bus.hasObservers();
    }

}

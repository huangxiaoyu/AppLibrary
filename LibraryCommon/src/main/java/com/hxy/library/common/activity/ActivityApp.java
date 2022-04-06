package com.hxy.library.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hxy.app.librarycore.R;
import com.hxy.app.librarycore.databinding.ActivityAppBinding;
import com.hxy.app.librarycore.utils.BaseContract;
import com.hxy.library.common.fragment.FragmentBase;
import com.hxy.library.common.utils.BaseContract;
import com.hxy.library.common.utils.LogUtils;

import java.util.List;

/**
 * 项目：MeiYueHongJiu
 * <p>
 * huangxiaoyu
 * <p>
 * 2018/5/7
 * <p>
 * desc 二次封装acitivity基类,处理fragment跳转
 */
public abstract class ActivityApp<P extends BaseContract.Presenter> extends ActivityBase<ActivityAppBinding, P> implements View
        .OnClickListener {


    /**
     * 获取上一个转入activity的intent值,该方法优先于getFirstFragment如果activity中有值需要传入fragment
     * .可以取值后放入getFirstFragment方法中的fragmen对象中
     *
     * @param intent
     */
    protected abstract void handleIntent(Intent intent);

    /**
     * 获取第一个fragment
     *
     * @return
     */
    protected abstract FragmentBase getFirstFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        beforOnCreate();
        setSupportActionBar(mBinding.tbBar);
        mBinding.tbBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mBinding.tbBar.setNavigationOnClickListener(v -> removeFragment());
        if (getIntent() != null) {//如果传来的有参数,抛出调用参数的入口
            handleIntent(getIntent());
        }
        FragmentManager fm = getSupportFragmentManager();
        int n = fm.getFragments().size();
        if (getSupportFragmentManager().getFragments() == null || getSupportFragmentManager()
                .getFragments().size() == 0) {
            FragmentBase firstFragment = getFirstFragment();
            if (firstFragment != null) {
                //                addFragment(firstFragment);
                addFragment(null, firstFragment);
            }
        }
    }

    public void setCenterTitle(String title) {
        mBinding.tvTitle.setText(title);
    }


    protected int getFragmentContentId() {
        return R.id.flContainer;
    }

    /**
     * 设置空间的显示/隐藏
     *
     * @param view
     * @param visiable
     */
    public void setViewVisiabel(View view, int visiable) {
        view.setVisibility(visiable);
    }

    /**
     * 设置空间的显示/隐藏
     *
     * @param id
     * @param visiable
     */
    public void setViewVisiabel(int id, int visiable) {
        findViewById(id).setVisibility(visiable);
    }

    /**
     * 设置空间的显示/隐藏
     *
     * @param view
     */
    public void setViewCklickListener(View view) {
        view.setOnClickListener(this);
    }

    /**
     * 设置空间的显示/隐藏
     *
     * @param id
     */
    public void setViewCklickListener(int id) {
        findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_add) {

        }
    }

    /**
     * @param from
     * @param to
     */
    public void addFragment(FragmentBase from, FragmentBase to) {
        if (mFragment != to) {
            mFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim
            // .fade_out);
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim
                    .push_right_in, R.anim.push_right_out);
            if (from == null) {
                transaction.add(getFragmentContentId(), to, to.getClass().getCanonicalName()); //
                // 隐藏当前的fragment，add下一个到Activity中
            } else if (!to.isAdded()) {    // 先判断是否被add过
                LogUtils.e("classname" + to.getClass().getCanonicalName());
                transaction.hide(from).add(getFragmentContentId(), to,
                        to.getClass().getCanonicalName()); //
                // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to); // 隐藏当前的fragment，显示下一个
            }
            transaction.addToBackStack(null).commit();
        }
    }

    @Override
    /**
     * 监听返回按键 如果只剩下一个fragment 结束掉当前的activity
     */ public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1 || getSupportFragmentManager().getFragments().size() == 1) {//如果只有一个fragment了
                // 就退出当前的activity
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 移出当前的fragment
     */
    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void removeFragment(String tag) {
        Fragment fragment = getFragment(tag);
        if (fragment != null && fragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    public void removeAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) != null) {
                transaction.remove(fragments.get(i));
            }
        }
        transaction.commit();
    }

    public boolean hasFragment(String tag) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public Fragment getFragment(String tag) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getTag().equalsIgnoreCase(tag)) {
                return fragments.get(i);
            }
        }
        return null;
    }

    public void showFragment(String tag) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getTag().equals(tag)) {
                transaction.show(fragments.get(i));
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
    }

    public void removeFragment(Bundle bundle) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            if (bundle != null) {
                FragmentBase fragment =
                        (FragmentBase) getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
                fragment.refreshUI(bundle);
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(mActivity);//统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(mActivity);//统计时长
    }
}

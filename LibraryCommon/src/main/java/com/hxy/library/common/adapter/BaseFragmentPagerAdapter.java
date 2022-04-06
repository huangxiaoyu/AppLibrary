package com.hxy.library.common.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * huangxiaoyu  2019-06-13 11:44
 * <p>
 * desc
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    String[] titles;
    ArrayList<String> titleList;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.titles = titles;
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, ArrayList<String> titleList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > position) {
            return titles[position];
        } else if (titleList != null && titleList.size() > position) {
            return titleList.get(position);
        }
        return super.getPageTitle(position);
    }
}

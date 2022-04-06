package com.hxy.app.baselibrary;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hxy.app.baselibrary.databinding.ActivityScrollingBinding;
import com.hxy.library.common.activity.ActivityBase;
import com.hxy.library.common.utils.BaseContract;

public class ScrollingActivity extends ActivityBase<ActivityScrollingBinding, BaseContract.Presenter> {


    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = mBinding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = mBinding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}
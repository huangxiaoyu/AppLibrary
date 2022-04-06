package com.hxy.library.common.view;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.hxy.app.librarycore.R;

/**
 * 2018/11/26 15:55
 * <p>
 * huangxiaoyu
 * <p>
 * desc
 */
public class CustomerLoadMoreView extends LoadMoreView {
    @Override
    public boolean isLoadEndGone() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.viewLoadMore;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.viewLoadFail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.viewLoadFinish;
    }
}

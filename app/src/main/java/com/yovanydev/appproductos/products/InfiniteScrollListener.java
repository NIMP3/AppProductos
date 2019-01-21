package com.yovanydev.appproductos.products;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 5;
    private final LinearLayoutManager mLayoutManager;
    private final DataLoading mDataLoading;

    public InfiniteScrollListener(LinearLayoutManager mLayoutManager, DataLoading mDataLoading) {
        this.mLayoutManager = checkNotNull(mLayoutManager);
        this.mDataLoading = checkNotNull(mDataLoading);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0 || mDataLoading.isLoadinData() || !mDataLoading.isThereMoreData()) return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = mLayoutManager.getItemCount();
        final int firstVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) onLoadMore();
    }

    public abstract void onLoadMore();
}

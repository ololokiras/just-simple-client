package com.ds24.ds24android.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ds24.ds24android.repository.Constants;

/**
 * Created by well on 13.05.2017.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */
    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {

                loadMoreItems();
                Log.d("scroll_deb_condition","done");
            }
            else {
                Log.d("scroll_deb_visibleItem",String.valueOf(visibleItemCount+firstVisibleItemPosition));
                Log.d("scroll_deb_ItemCount",String.valueOf(totalItemCount));
                Log.d("scroll_deb_condition","false");
            }

        }
        Log.d("scroll_deb_isLoading",String.valueOf(isLoading()));
        Log.d("scroll_deb_isLastPage",String.valueOf(isLastPage()));
        Log.d("scroll_deb_condition","false");

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}

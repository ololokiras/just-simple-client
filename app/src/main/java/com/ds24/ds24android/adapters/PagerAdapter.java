package com.ds24.ds24android.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ds24.ds24android.fragments.CommentsFragment;
import com.ds24.ds24android.fragments.DetailFragment;
import com.ds24.ds24android.fragments.UpdatesFragment;
import com.ds24.ds24android.repository.Constants;

/**
 * Created by well on 01.05.2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    int requestId;
    Bundle bundle;

    public PagerAdapter(FragmentManager fm, int numOfTabs, int requestId) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.requestId = requestId;
        bundle = new Bundle();
        bundle.putInt(Constants.requestId, requestId);
    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                return detailFragment;
            case 1:
                CommentsFragment commentsFragment = new CommentsFragment();
                commentsFragment.setArguments(bundle);
                return commentsFragment;
            case 2:
                UpdatesFragment updatesFragment = new UpdatesFragment();
                updatesFragment.setArguments(bundle);
                return updatesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}


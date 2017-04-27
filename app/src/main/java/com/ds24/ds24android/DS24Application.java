package com.ds24.ds24android;

import android.app.Application;

/**
 * Created by well on 22.04.2017.
 */

public class DS24Application extends Application{
    private static DS24Application mInstance;
    public static Filter filterInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance=this;
        filterInstance=new Filter();
    }

    public static synchronized DS24Application getInstance(){
        return mInstance;
    }

    public static synchronized Filter getFilterInstance(){
        if(filterInstance==null)
            filterInstance=new Filter();
        return filterInstance;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
    }
}

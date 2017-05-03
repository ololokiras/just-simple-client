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

    public static Filter previousFilterState=new Filter();

    public static synchronized Filter getFilterInstance(){
        if(filterInstance==null)
            filterInstance=new Filter();
        return filterInstance;
    }

    //region clear filter
    public static void clearFilter(){
        filterInstance=new Filter();
    }

    public static void clearHouse(){
        filterInstance.houseData=null;
        filterInstance.flatData=null;
    }

    public static void clearStreet(){
        filterInstance.streetData=null;
        clearHouse();
    }

    public static void clearFlat(){
        filterInstance.flatData=null;
    }

    public static void clearContractor(){
        filterInstance.contractorData=null;
        clearStreet();
    }

    public static void clearReason(){
        filterInstance.reasonData=null;
    }

    public static void clearWorkType(){
        filterInstance.workTypeData=null;
        clearReason();
    }
    public static void clearResponsible() {
        filterInstance.responsibleData=null;
    }
    public static void clearEmployee() {
        filterInstance.employeeData=null;
    }
    public static void clearStatus() {
        filterInstance.statusData=null;
    }
    public static void clearRequestType() {
        filterInstance.requestTypeData=null;
    }
    //endregion

    @Override
    public void onTerminate(){
        super.onTerminate();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
    }


    public static void clearPeriod() {
        filterInstance.startDate=null;
        filterInstance.endDate=null;
    }
}

package com.ds24.ds24android.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.Filter;
import com.ds24.ds24android.R;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.model.login.LoginJson;

import java.util.concurrent.TimeoutException;

import es.dmoral.toasty.Toasty;

/**
 * Created by well on 22.04.2017.
 */

public class Functions {
    public static String getLoginPassToBase64(String login, String pass){
        String finalString=login+" : "+pass;
        return Base64.encodeToString(finalString.getBytes(),Base64.NO_WRAP);
    }

    public static LoginJson getLoginJSON(){
        LoginJson loginJson=new LoginJson();
        loginJson.devType= Constants.devType;
        loginJson.pushToken=Constants.pushToken;
        return loginJson;
    }

    public static String encodeToBase64(String source){
        return Base64.encodeToString(source.getBytes(),Base64.NO_WRAP);
    }

    public static void restartToMainActivity(){
        Context baseContext= DS24Application.getInstance().getBaseContext();
        PreferencesBuffer.eraseToken(baseContext);
        Intent i=baseContext.getPackageManager().getLaunchIntentForPackage(baseContext.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        baseContext.startActivity(i);
    }

    public static String prepareTreeToSimpleString(String source){
        String result=source.replace(".","").replace("|","");
        return result;
    }

    public static void showToastErrorMessage(Context ctx){
        Toasty.error(ctx,ctx.getString(R.string.operation_error)).show();
    }

    public static boolean compareFilterState(){

        Filter previous=DS24Application.previousFilterState;
        Filter current=DS24Application.getFilterInstance();

        if(previous==null && current==null)
            return true;

        if(previous.contractorData!=null && current.contractorData==null)
            return false;
        if(previous.contractorData==null && current.contractorData!=null)
            return false;
        if(previous.contractorData!=null && current.contractorData!=null)
            if(previous.contractorData.cntId!=current.contractorData.cntId)
                return false;

        if(previous.streetData!=null && current.streetData==null)
            return false;
        if(previous.streetData==null && current.streetData!=null)
            return false;
        if(previous.streetData!=null && current.streetData!=null)
            if(!TextUtils.isEmpty(previous.streetData.street) && !TextUtils.isEmpty(current.streetData.street))
                if(!previous.streetData.street.equals(current.streetData.street))
                    return false;

        if(previous.houseData!=null && current.houseData==null)
            return false;
        if(previous.houseData==null && current.houseData!=null)
            return false;
        if(previous.houseData!=null && current.houseData!=null)
            if(previous.houseData.houseId!=current.houseData.houseId)
                return false;

        if(previous.flatData!=null && current.flatData==null)
            return false;
        if(previous.flatData==null && current.flatData!=null)
            return false;
        if(previous.flatData!=null && current.flatData!=null)
            if (previous.flatData.flatId!=current.flatData.flatId)
                return false;

        if(previous.workTypeData!=null && current.workTypeData==null)
            return false;
        if(previous.workTypeData==null && current.workTypeData!=null)
            return false;
        if(previous.workTypeData!=null && current.workTypeData!=null)
            if(previous.workTypeData.workTypeId!=current.workTypeData.workTypeId)
                return false;

        if(previous.reasonData!=null && current.reasonData==null)
            return false;
        if(previous.reasonData==null && current.reasonData!=null)
            return false;
        if(previous.reasonData!=null && current.reasonData!=null)
            if(previous.reasonData.reasonId!=current.reasonData.reasonId)
                return false;

        if(previous.responsibleData!=null && current.responsibleData==null)
            return false;
        if(previous.responsibleData==null && current.responsibleData!=null)
            return false;
        if(previous.responsibleData!=null && current.responsibleData!=null)
            if(previous.responsibleData.respId!=current.responsibleData.respId)
                return false;

        if(previous.employeeData!=null && current.employeeData==null)
            return false;
        if(previous.employeeData==null && current.employeeData!=null)
            return false;
        if(previous.employeeData!=null && current.employeeData!=null)
            if(previous.employeeData.empId!=current.employeeData.empId)
                return false;

        if(previous.statusData!=null && current.statusData==null)
            return false;
        if(previous.statusData==null && current.statusData!=null)
            return false;
        if(previous.statusData!=null && current.statusData!=null)
            if(previous.statusData.statusId!=current.statusData.statusId)
                return false;

        if(previous.requestTypeData!=null && current.requestTypeData==null)
            return false;
        if(previous.requestTypeData==null && current.requestTypeData!=null)
            return false;
        if(previous.requestTypeData!=null && current.requestTypeData!=null)
            if(previous.requestTypeData.rtypeId!=current.requestTypeData.rtypeId)
                return false;

        if(TextUtils.isEmpty(previous.startDate) && !TextUtils.isEmpty(current.startDate))
            return false;
        if(!TextUtils.isEmpty(previous.startDate) && !TextUtils.isEmpty(current.startDate))
            if(!previous.startDate.equals(current.startDate))
                return false;

        if(TextUtils.isEmpty(previous.endDate) && !TextUtils.isEmpty(current.endDate))
            return false;
        if(!TextUtils.isEmpty(previous.endDate) && !TextUtils.isEmpty(current.endDate))
            if(!previous.endDate.equals(current.endDate))
                return false;

        return true;
    }


    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String fetchErrorMessage(Throwable throwable, Context ctx) {
        String errorMsg = ctx.getResources().getString(R.string.operation_error);

        if (!Functions.isOnline(ctx)) {
            errorMsg = ctx.getResources().getString(R.string.internet_connection_error);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = ctx.getResources().getString(R.string.internet_connection_error);
        }

        return errorMsg;
    }
}

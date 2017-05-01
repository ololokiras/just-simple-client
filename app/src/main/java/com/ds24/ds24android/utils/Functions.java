package com.ds24.ds24android.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.retrofit.model.login.LoginJson;

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
        Intent i=baseContext.getPackageManager().getLaunchIntentForPackage(baseContext.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        baseContext.startActivity(i);
    }

    public static String prepareTreeToSimpleString(String source){
        String result=source.replace(":","").replace("|","");
        return result;
    }
}

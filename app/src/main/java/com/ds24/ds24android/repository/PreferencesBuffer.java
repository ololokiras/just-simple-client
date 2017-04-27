package com.ds24.ds24android.repository;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Created by well on 22.04.2017.
 */

public class PreferencesBuffer {
    //region token
    public static void setToken(String token,Context ctx){
        ctx.getSharedPreferences(Constants.preferenceName,Context.MODE_PRIVATE).edit().putString(Constants.token,token).commit();
    }

    @Nullable
    public static String getToken(Context ctx){
        return ctx.getSharedPreferences(Constants.preferenceName,Context.MODE_PRIVATE).getString(Constants.token,null);
    }

    public static void eraseToken(Context ctx){
        ctx.getSharedPreferences(Constants.preferenceName,Context.MODE_PRIVATE).edit().remove(Constants.token).commit();
    }
    //endregion
}

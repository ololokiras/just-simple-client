package com.ds24.ds24android.retrofit.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 22.04.2017.
 */

public class LoginJson{
    @SerializedName("devType")
    @Expose
    public String devType;
    @SerializedName("pushToken")
    @Expose
    public String pushToken;
}

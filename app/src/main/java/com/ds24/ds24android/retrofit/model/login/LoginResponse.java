package com.ds24.ds24android.retrofit.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 22.04.2017.
 */

public class LoginResponse {
    @SerializedName("ok")
    @Expose
    public Boolean ok;
    @SerializedName("credentials")
    @Expose
    public Boolean credentials;
    @SerializedName("token")
    @Expose
    public String token;
}

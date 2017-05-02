package com.ds24.ds24android.retrofit.model.requestDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by well on 02.05.2017.
 */

public class RequestDetail {
    @SerializedName("ok")
    @Expose
    public Boolean ok;
    @SerializedName("token")
    @Expose
    public Boolean token;
    @SerializedName("data")
    @Expose
    public RequestDetailData data = null;
}

package com.ds24.ds24android.retrofit.model.asks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 24.04.2017.
 */

public class RequestionAsk {
    @SerializedName("act")
    @Expose
    public String act;
    @SerializedName("req")
    @Expose
    public ReqParams req;
}

package com.ds24.ds24android.retrofit.model.requestDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 02.05.2017.
 */

public class RequestDetailAsk {
    @SerializedName("act")
    @Expose
    public String act;
    @SerializedName("req")
    @Expose
    public Req req;
}

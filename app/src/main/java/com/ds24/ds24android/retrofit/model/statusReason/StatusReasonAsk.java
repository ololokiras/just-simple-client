package com.ds24.ds24android.retrofit.model.statusReason;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 09.05.2017.
 */

public class StatusReasonAsk {
    @SerializedName("act")
    @Expose
    public String act;
    @SerializedName("req")
    @Expose
    public Req req;
}

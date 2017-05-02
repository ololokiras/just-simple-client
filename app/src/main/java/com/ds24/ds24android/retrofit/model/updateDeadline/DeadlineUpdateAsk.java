package com.ds24.ds24android.retrofit.model.updateDeadline;

import com.ds24.ds24android.retrofit.model.commentUpdate.RequestionDetailParams;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 02.05.2017.
 */

public class DeadlineUpdateAsk {
    @SerializedName("act")
    @Expose
    public String act;
    @SerializedName("req")
    @Expose
    public RequestionDetailParams req;
    @SerializedName("data")
    @Expose
    public DeadlineUpdateData data;
}

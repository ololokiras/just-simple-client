package com.ds24.ds24android.retrofit.model.comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 01.05.2017.
 */

public class Req {
    @SerializedName("start")
    @Expose
    public Integer start;
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("request_id")
    @Expose
    public Integer requestId;
}

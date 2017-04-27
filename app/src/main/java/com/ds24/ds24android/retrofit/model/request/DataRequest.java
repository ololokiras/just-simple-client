package com.ds24.ds24android.retrofit.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 24.04.2017.
 */

public class DataRequest {
    @SerializedName("rn")
    @Expose
    public Integer rn;
    @SerializedName("request_id")
    @Expose
    public Integer requestId;
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("house")
    @Expose
    public String house;
    @SerializedName("flat")
    @Expose
    public String flat;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
}

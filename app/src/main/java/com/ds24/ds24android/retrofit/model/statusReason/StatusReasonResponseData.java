package com.ds24.ds24android.retrofit.model.statusReason;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 09.05.2017.
 */

public class StatusReasonResponseData implements Serializable{
    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("reason_id")
    @Expose
    public int reasonId;
}

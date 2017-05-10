package com.ds24.ds24android.retrofit.model.statusReason;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by well on 09.05.2017.
 */

public class StatusReasonResponse {
    @SerializedName("ok")
    @Expose
    public Boolean ok;
    @SerializedName("token")
    @Expose
    public Boolean token;
    @SerializedName("data")
    @Expose
    public ArrayList<StatusReasonResponseData> data=null;
}

package com.ds24.ds24android.retrofit.model.status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 26.04.2017.
 */

public class StatusResponseData {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("status_id")
    @Expose
    public String statusId;
}

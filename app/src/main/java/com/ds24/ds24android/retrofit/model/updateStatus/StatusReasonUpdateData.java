package com.ds24.ds24android.retrofit.model.updateStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 02.05.2017.
 */

public class StatusReasonUpdateData {
    @SerializedName("status_id")
    @Expose
    public int statusId;

    @SerializedName("reason_id")
    @Expose
    public int reasonId;
}

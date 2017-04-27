package com.ds24.ds24android.retrofit.model.reason;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 26.04.2017.
 */

public class ReasonResponseData {
    @SerializedName("ess")
    @Expose
    public String reason;
    @SerializedName("ess_id")
    @Expose
    public int reasonId;
}

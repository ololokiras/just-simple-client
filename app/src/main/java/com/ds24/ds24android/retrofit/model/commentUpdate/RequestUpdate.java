package com.ds24.ds24android.retrofit.model.commentUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 01.05.2017.
 */

public class RequestUpdate {
    @SerializedName("ok")
    @Expose
    public boolean ok;
    @SerializedName("token")
    @Expose
    public boolean token;
    @SerializedName("data")
    @Expose
    RequestUpdateData data;
}

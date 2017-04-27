package com.ds24.ds24android.retrofit.model.requestType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 26.04.2017.
 */

public class RequestTypeResponseData {
    @SerializedName("rtype_id")
    @Expose
    public int rtypeId;
    @SerializedName("rtype")
    @Expose
    public String rtype;
}

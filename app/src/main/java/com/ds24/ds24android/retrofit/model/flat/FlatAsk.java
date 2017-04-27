package com.ds24.ds24android.retrofit.model.flat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 26.04.2017.
 */

public class FlatAsk {
    @SerializedName("act")
    @Expose
    public String act;
    @SerializedName("req")
    @Expose
    public Req req;
}

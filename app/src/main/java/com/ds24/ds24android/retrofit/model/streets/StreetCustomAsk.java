package com.ds24.ds24android.retrofit.model.streets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 25.04.2017.
 */

public class StreetCustomAsk {
    @SerializedName("act")
    @Expose
    public String act="street_list";
    @SerializedName("req")
    @Expose
    public Req req;


}

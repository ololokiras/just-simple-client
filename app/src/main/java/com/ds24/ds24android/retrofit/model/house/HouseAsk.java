package com.ds24.ds24android.retrofit.model.house;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 26.04.2017.
 */

public class HouseAsk {
    @SerializedName("act")
    @Expose
    public String act="house_list";
    @SerializedName("req")
    @Expose
    public Req req;
}

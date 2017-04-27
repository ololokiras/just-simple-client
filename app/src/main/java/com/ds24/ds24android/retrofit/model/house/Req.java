package com.ds24.ds24android.retrofit.model.house;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 26.04.2017.
 */

public class Req {
    @SerializedName("cnt_id")
    @Expose
    public int contractorId;
    @SerializedName("street")
    @Expose
    public String street;

}

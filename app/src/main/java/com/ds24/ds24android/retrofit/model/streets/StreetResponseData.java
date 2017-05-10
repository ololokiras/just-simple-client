package com.ds24.ds24android.retrofit.model.streets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 25.04.2017.
 */

public class StreetResponseData implements Serializable {
    @SerializedName("street")
    @Expose
    public String street;
}

package com.ds24.ds24android.retrofit.model.house;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 26.04.2017.
 */

public class HouseResponseData implements Serializable{
    @SerializedName("house_id")
    @Expose
    public int houseId;
    @SerializedName("house")
    @Expose
    public String house;
}

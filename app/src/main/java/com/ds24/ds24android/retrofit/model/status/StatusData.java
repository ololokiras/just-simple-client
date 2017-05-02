package com.ds24.ds24android.retrofit.model.status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 02.05.2017.
 */

public class StatusData implements Serializable{
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("status_id")
    @Expose
    public Integer statusId;
}

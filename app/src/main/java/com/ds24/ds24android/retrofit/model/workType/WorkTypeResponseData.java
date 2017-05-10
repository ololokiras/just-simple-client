package com.ds24.ds24android.retrofit.model.workType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 26.04.2017.
 */

public class WorkTypeResponseData implements Serializable{
    @SerializedName("type_id")
    @Expose
    public int workTypeId;
    @SerializedName("type")
    @Expose
    public String workType;
}

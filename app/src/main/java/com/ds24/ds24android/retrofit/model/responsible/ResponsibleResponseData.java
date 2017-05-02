package com.ds24.ds24android.retrofit.model.responsible;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 26.04.2017.
 */

public class ResponsibleResponseData implements Serializable{
    @SerializedName("resp")
    @Expose
    public String resp;
    @SerializedName("resp_id")
    @Expose
    public int respId;
}

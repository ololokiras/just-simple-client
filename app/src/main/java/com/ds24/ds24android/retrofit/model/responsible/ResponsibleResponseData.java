package com.ds24.ds24android.retrofit.model.responsible;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 26.04.2017.
 */

public class ResponsibleResponseData {
    @SerializedName("resp")
    @Expose
    public String resp;
    @SerializedName("resp_id")
    @Expose
    public int respId;
}

package com.ds24.ds24android.retrofit.model.contractors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by well on 24.04.2017.
 */

public class ContractorResponse {
    @SerializedName("ok")
    @Expose
    public Boolean ok;
    @SerializedName("token")
    @Expose
    public Boolean token;
    @SerializedName("data")
    @Expose
    public ArrayList<ContractorResponseData> data = null;
}

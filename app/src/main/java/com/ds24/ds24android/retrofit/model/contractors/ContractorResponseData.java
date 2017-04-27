package com.ds24.ds24android.retrofit.model.contractors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 24.04.2017.
 */

public class ContractorResponseData implements Serializable {
    @SerializedName("cnt")
    @Expose
    public String cnt;
    @SerializedName("cnt_id")
    @Expose
    public Integer cntId;
}

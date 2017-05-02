package com.ds24.ds24android.retrofit.model.commentUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 01.05.2017.
 */

public class UpdateAskInnerComment {
    @SerializedName("act")
    @Expose
    public String act;
    @SerializedName("req")
    @Expose
    public RequestionDetailParams req;
    @SerializedName("data")
    @Expose
    public UpdateAskComment data;
}

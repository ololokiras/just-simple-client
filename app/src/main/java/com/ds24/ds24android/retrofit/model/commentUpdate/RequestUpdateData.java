package com.ds24.ds24android.retrofit.model.commentUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 01.05.2017.
 */

public class RequestUpdateData {
    @SerializedName("affected_rows")
    @Expose
    int affected_rows=2;
}

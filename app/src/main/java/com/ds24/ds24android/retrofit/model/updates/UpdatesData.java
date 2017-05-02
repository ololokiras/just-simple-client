package com.ds24.ds24android.retrofit.model.updates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 01.05.2017.
 */

public class UpdatesData {
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("rn")
    @Expose
    public Integer rn;
}

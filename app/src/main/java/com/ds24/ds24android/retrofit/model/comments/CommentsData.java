package com.ds24.ds24android.retrofit.model.comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 01.05.2017.
 */

public class CommentsData {
    @SerializedName("mtype")
    @Expose
    public Integer mtype;
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("rn")
    @Expose
    public Integer rn;
}

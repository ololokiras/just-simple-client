package com.ds24.ds24android.retrofit.model.comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 01.05.2017.
 */

public class CommentAsk {
    @SerializedName("act")
    @Expose
    public String act;
    @SerializedName("req")
    @Expose
    public Req req;
}

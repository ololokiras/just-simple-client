package com.ds24.ds24android.retrofit.model.comments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by well on 01.05.2017.
 */

public class Comments {
    @SerializedName("ok")
    @Expose
    public Boolean ok;
    @SerializedName("token")
    @Expose
    public Boolean token;
    @SerializedName("data")
    @Expose
    public ArrayList<CommentsData> data = null;
}

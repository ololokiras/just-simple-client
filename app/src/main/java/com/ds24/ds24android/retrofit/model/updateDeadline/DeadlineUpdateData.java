package com.ds24.ds24android.retrofit.model.updateDeadline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 02.05.2017.
 */

public class DeadlineUpdateData {
    @SerializedName("deadline_at")
    @Expose
    public String deadlineAt;
}

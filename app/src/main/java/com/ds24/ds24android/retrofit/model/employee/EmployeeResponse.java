package com.ds24.ds24android.retrofit.model.employee;

import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by well on 26.04.2017.
 */

public class EmployeeResponse {
    @SerializedName("ok")
    @Expose
    public Boolean ok;
    @SerializedName("token")
    @Expose
    public Boolean token;
    @SerializedName("data")
    @Expose
    public ArrayList<EmployeeResponseData> data=null;
}

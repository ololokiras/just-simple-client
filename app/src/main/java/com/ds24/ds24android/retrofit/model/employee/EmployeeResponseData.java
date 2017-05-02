package com.ds24.ds24android.retrofit.model.employee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by well on 26.04.2017.
 */

public class EmployeeResponseData implements Serializable{
    @SerializedName("emp")
    @Expose
    public String emp;
    @SerializedName("emp_id")
    @Expose
    public int empId;
}

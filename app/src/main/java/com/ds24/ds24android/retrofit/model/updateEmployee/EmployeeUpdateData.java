package com.ds24.ds24android.retrofit.model.updateEmployee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 02.05.2017.
 */

public class EmployeeUpdateData {
    @SerializedName("emp_id")
    @Expose
    public int empId;
}

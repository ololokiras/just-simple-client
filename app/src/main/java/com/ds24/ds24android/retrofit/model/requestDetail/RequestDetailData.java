package com.ds24.ds24android.retrofit.model.requestDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 02.05.2017.
 */

public class RequestDetailData {
    @SerializedName("request_id")
    @Expose
    public Integer requestId;
    @SerializedName("rtype")
    @Expose
    public String rtype;
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("house")
    @Expose
    public String house;
    @SerializedName("flat")
    @Expose
    public String flat;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("floor")
    @Expose
    public String floor;
    @SerializedName("entr")
    @Expose
    public String entr;
    @SerializedName("autophone")
    @Expose
    public String autophone;
    @SerializedName("contphone")
    @Expose
    public String contphone;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("ess")
    @Expose
    public String ess;
    @SerializedName("emp")
    @Expose
    public String emp;
    @SerializedName("resp")
    @Expose
    public String resp;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("deadline_at")
    @Expose
    public String deadlineAt;
    @SerializedName("emp_note")
    @Expose
    public String empNote;
    @SerializedName("status_id")
    @Expose
    public Integer statusId;
    @SerializedName("reason_id")
    @Expose
    public Integer reasonId;
    @SerializedName("type_id")
    @Expose
    public Integer typeId;
    @SerializedName("ess_id")
    @Expose
    public Integer essId;
    @SerializedName("emp_id")
    @Expose
    public Integer empId;
    @SerializedName("resp_id")
    @Expose
    public Integer respId;
}

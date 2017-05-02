package com.ds24.ds24android.retrofit.model.asks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by well on 24.04.2017.
 */

public class ReqParams {
    @SerializedName("start")
    @Expose
    public Integer start;
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("cnt_id")
    @Expose
    public Integer cntId;
    @SerializedName("street")
    @Expose
    public String street;
    @SerializedName("house_id")
    @Expose
    public Integer houseId;
    @SerializedName("flat_id")
    @Expose
    public Integer flatId;
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
    @SerializedName("status_id")
    @Expose
    public String statusId;
    @SerializedName("rtype_id")
    @Expose
    public Integer rtypeId;
    @SerializedName("request_id")
    @Expose
    public Integer requestId;
    @SerializedName("date_start")
    @Expose
    public String dateStart;
    @SerializedName("date_end")
    @Expose
    public String dateEnd;
}

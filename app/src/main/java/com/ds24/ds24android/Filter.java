package com.ds24.ds24android;

import com.ds24.ds24android.retrofit.model.contractors.ContractorResponseData;
import com.ds24.ds24android.retrofit.model.employee.EmployeeResponseData;
import com.ds24.ds24android.retrofit.model.flat.FlatResponseData;
import com.ds24.ds24android.retrofit.model.house.HouseResponseData;
import com.ds24.ds24android.retrofit.model.reason.ReasonResponseData;
import com.ds24.ds24android.retrofit.model.requestType.RequestTypeResponseData;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponseData;
import com.ds24.ds24android.retrofit.model.status.StatusResponseData;
import com.ds24.ds24android.retrofit.model.streets.StreetResponseData;
import com.ds24.ds24android.retrofit.model.workType.WorkTypeResponseData;

/**
 * Created by well on 25.04.2017.
 */

public class Filter {
    public ContractorResponseData contractorData=null;
    public StreetResponseData streetData=null;
    public HouseResponseData houseData=null;
    public FlatResponseData flatData=null;
    public WorkTypeResponseData workTypeData=null;
    public ReasonResponseData reasonData=null;
    public ResponsibleResponseData responsibleData=null;
    public EmployeeResponseData employeeData=null;
    public StatusResponseData statusData =null;
    public RequestTypeResponseData requestTypeData=null;
    public String startDate=null;
    public String endDate=null;
}

package com.ds24.ds24android.repository;

import com.ds24.ds24android.retrofit.model.statusTree.StatusResponseData;

/**
 * Created by well on 22.04.2017.
 */

public class Constants {
    public static String baseURL="http://109.195.134.64:8081";

    public static String preferenceName="ds24";
    public static String token="token";

    public static String errorTag="error_tag";

    public static String defaultStatusDataName ="Новые";
    public static String defaultStatusDataIds ="1:65:70:5";

    //for authorization
    public static String devType="Android";
    public static String pushToken="someGoogleToken";


    //keys for "activity for result"
    public static int loginActivityKey=1;
    public static int contractorsActivityKey=11;
    public static int streetActivityKey=12;
    public static int statusChangeActivityKey=21;
    public static int responsibleChangeActivityKey=22;
    public static int employeeChangeActivityKey=23;
    public static int MY_PERMISSIONS_REQUEST_CALL_PHONE=101;
    public static int reasonActivityKey=24;

    //actions string list
    public static String getRequestListAction="list";
    public static String getContractorsAction="cnt_list";
    public static String getStreetList="street_list";
    public static String getHouseList="house_list";
    public static String getFlatList="flat_list";
    public static String getWorkTypeList="type_list";
    public static String getReasonList="ess_list";
    public static String getResponsibleList="resp_list";
    public static String getEmployeeList="emp_list";
    public static String getStatusTreeList="status_tree_list";
    public static String getRequestTypeList="reqtype_list";
    public static String getCommentList="comment_list";
    public static String setUpdateRequest="update";
    public static String getUpdatesList="activity_list";
    public static String getRequestDetail="detail";
    public static String getStatusList="status_list";
    public static String getStatusReasonList="status_reason_list";



    //intent keys
    public static String contractors="contractors";
    public static String requestId="requestId";
    public static String statusChange="statusChange";
    public static String responsibleChange="responsibleChange";
    public static String employeeChange="employeeChange";
    public static String statusId="statusId";
    public static String reasonId="reasonId";
    public static String employeeId="employeeId";
    public static String responsibleId="responsibleId";


    public static int paginationSize=50;
}

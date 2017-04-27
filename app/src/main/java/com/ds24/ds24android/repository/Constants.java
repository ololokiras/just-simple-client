package com.ds24.ds24android.repository;

/**
 * Created by well on 22.04.2017.
 */

public class Constants {
    public static String baseURL="http://109.195.134.64:8081";

    public static String preferenceName="ds24";
    public static String token="token";

    public static String errorTag="error_tag";

    //for authorization
    public static String devType="Android";
    public static String pushToken="someGoogleToken";


    //keys for "activity for result"
    public static int loginActivityKey=1;
    public static int contractorsActivityKey=11;
    public static int streetActivityKey=12;

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


    //intent keys
    public static String contractors="contractors";
    public static String streets="streets";

    public static int paginationSize=10;
}

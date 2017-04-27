package com.ds24.ds24android.retrofit;



import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.retrofit.model.asks.RequestionAsk;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.contractors.ContractorResponse;
import com.ds24.ds24android.retrofit.model.employee.EmployeeResponse;
import com.ds24.ds24android.retrofit.model.flat.FlatAsk;
import com.ds24.ds24android.retrofit.model.flat.FlatResponse;
import com.ds24.ds24android.retrofit.model.house.HouseAsk;
import com.ds24.ds24android.retrofit.model.house.HouseResponse;
import com.ds24.ds24android.retrofit.model.login.LoginJson;
import com.ds24.ds24android.retrofit.model.login.LoginResponse;
import com.ds24.ds24android.retrofit.model.reason.ReasonAsk;
import com.ds24.ds24android.retrofit.model.reason.ReasonResponse;
import com.ds24.ds24android.retrofit.model.request.Requestion;
import com.ds24.ds24android.retrofit.model.requestType.RequestTypeResponse;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponse;
import com.ds24.ds24android.retrofit.model.status.StatusResponse;
import com.ds24.ds24android.retrofit.model.streets.StreetCustomAsk;
import com.ds24.ds24android.retrofit.model.streets.StreetResponse;
import com.ds24.ds24android.retrofit.model.workType.WorkTypeResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by well on 04.04.2017.
 */

public interface ServerAPI {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.baseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();


    //запрос регистрации пользователя
   // @Headers("authorization: a2hlcmtha29qdG8gOiBiYW5hbg==")
    @POST("/registration")
    Call<LoginResponse> getLogin(
            @Header("authorization") String authorization,
            @Body LoginJson loginJson);

    //запрос на получение заявок
    @POST("/request")
    Call<Requestion> getRequestions(
            @Header("authorization") String token,
            @Body RequestionAsk requestionAsk);

    //получение списка юр.лиц
    @POST("/reference")
    Call<ContractorResponse> getContractors(
            @Header("authorization") String token,
            @Body SimpleAsk ask);

    //получение улиц
    @POST("/reference")
    Call<StreetResponse> getStreetsSimple(
            @Header("authorization") String token,
            @Body SimpleAsk ask);

    //получение улиц в зависимости от юр.лица
    @POST("/reference")
    Call<StreetResponse> getStreetsContractors(
            @Header("authorization") String token,
            @Body StreetCustomAsk ask);

    //получение домов в зависимости от юр.лиц и улицы
    @POST("/reference")
    Call<HouseResponse> getHouses(
            @Header("authorization") String token,
            @Body HouseAsk ask);

    //получение списка квартир в зависимости от дома
    @POST("/reference")
    Call<FlatResponse> getFlats(
            @Header("authorization") String token,
            @Body FlatAsk ask);

    //получение типов работ
    @POST("/reference")
    Call<WorkTypeResponse> getWorkTypes(
            @Header("authorization") String token,
            @Body SimpleAsk ask);

    //получение списпа причин
    @POST("/reference")
    Call<ReasonResponse> getReasons(
            @Header("authorization") String token,
            @Body ReasonAsk ask);

    //получение списка ответственных
    @POST("/reference")
    Call<ResponsibleResponse> getResponsibles(
            @Header("authorization") String token,
            @Body SimpleAsk ask);

    //получение списка исполнителей
    @POST("/reference")
    Call<EmployeeResponse> getEmployees(
            @Header("authorization") String token,
            @Body SimpleAsk ask);

    //получение списка статусов
    @POST("/reference")
    Call<StatusResponse> getStatuses(
            @Header("authorization") String token,
            @Body SimpleAsk ask);

    //получение списка типа заявок
    @POST("/reference")
    Call<RequestTypeResponse> getRequestTypes(
            @Header("authorization") String token,
            @Body SimpleAsk ask);

}

package com.ds24.ds24android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds24.ds24android.adapters.filterAdapters.EmployeeAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.employee.EmployeeResponse;
import com.ds24.ds24android.retrofit.model.employee.EmployeeResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeChangeActivity extends AppCompatActivity implements EmployeeAdapter.EmployeeSelectedListener {

    RecyclerView employeeChangeRecycler;
    ServerAPI serverAPI;
    EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_change);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        initUI();
        doRequest();
    }

    private void initUI() {
        employeeChangeRecycler=(RecyclerView)findViewById(R.id.employee_change_recycler);
        employeeChangeRecycler.setHasFixedSize(true);
        employeeChangeRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getEmployeeList;
        Call<EmployeeResponse> employeeResponseCall=serverAPI.getEmployees(
                Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                ask);
        employeeResponseCall.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            fillRecycler(response.body().data);
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<EmployeeResponseData> data) {
        employeeAdapter=new EmployeeAdapter(this,data,this);
        employeeChangeRecycler.setAdapter(employeeAdapter);
    }

    @Override
    public void onEmployeeSelect(EmployeeResponseData employeeData) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.employeeChange,employeeData);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}

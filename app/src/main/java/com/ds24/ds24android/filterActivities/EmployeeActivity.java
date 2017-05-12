package com.ds24.ds24android.filterActivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
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

public class EmployeeActivity extends AppCompatActivity implements EmployeeAdapter.EmployeeSelectedListener {

    EmployeeAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView employeeRecycler;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler);
        this.ctx=this;
        serverAPI= ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        employeeRecycler=(RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        employeeRecycler.setLayoutManager(layoutManager);
        employeeRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getEmployeeList;
        Call<EmployeeResponse> callEmployee=serverAPI.getEmployees(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        callEmployee.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().ok){
                        if(response.body().token){
                            fillRecycler(response.body().data);
                        } else
                            Functions.restartToMainActivity();
                    } else
                        Functions.showToastErrorMessage(ctx);
                } else
                    Functions.showToastErrorMessage(ctx);
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Functions.showToastErrorMessage(ctx);
            }
        });
    }

    private void fillRecycler(ArrayList<EmployeeResponseData> data) {
        if(DS24Application.getFilterInstance().employeeData!=null){
            if(DS24Application.getFilterInstance().employeeData.empId>0)
                adapter=new EmployeeAdapter(this,data,this,DS24Application.getFilterInstance().employeeData.empId);
        }
        else
            adapter=new EmployeeAdapter(this,data,this,-1);
        employeeRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onEmployeeSelect(EmployeeResponseData employeeData) {
        DS24Application.getFilterInstance().employeeData=employeeData;
        setResult(RESULT_OK);
        finish();
    }
}

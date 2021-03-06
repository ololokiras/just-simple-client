package com.ds24.ds24android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

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
    int incomeEmployeeId;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx=this;
        setContentView(R.layout.activity_simple_recycler);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        incomeEmployeeId=getIntent().getIntExtra(Constants.employeeId,-1);
        initUI();
        doRequest();
    }

    private void initUI() {
        employeeChangeRecycler=(RecyclerView)findViewById(R.id.recycler);
        employeeChangeRecycler.setHasFixedSize(true);
        employeeChangeRecycler.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                if(response.isSuccessful()){
                    if(response.body().ok){
                        if(response.body().token)
                            fillRecycler(response.body().data);
                        else
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
        employeeAdapter=new EmployeeAdapter(this,data,this,incomeEmployeeId);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed(){
        setResult(RESULT_CANCELED,null);
        finish();
    }
}

package com.ds24.ds24android.filterActivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.RequestTypeAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.requestType.RequestTypeResponse;
import com.ds24.ds24android.retrofit.model.requestType.RequestTypeResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestTypeActivity extends AppCompatActivity implements RequestTypeAdapter.RequestTypeSelectListener {

    RequestTypeAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView requestTypeRecycler;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx=this;
        setContentView(R.layout.activity_simple_recycler);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        requestTypeRecycler=(RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        requestTypeRecycler.setLayoutManager(layoutManager);
        requestTypeRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getRequestTypeList;
        Call<RequestTypeResponse> callRequestType=serverAPI.getRequestTypes(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        callRequestType.enqueue(new Callback<RequestTypeResponse>() {
            @Override
            public void onResponse(Call<RequestTypeResponse> call, Response<RequestTypeResponse> response) {
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
            public void onFailure(Call<RequestTypeResponse> call, Throwable t) {
                Functions.showToastErrorMessage(ctx);
            }
        });
    }

    private void fillRecycler(ArrayList<RequestTypeResponseData> data) {
        if(DS24Application.getFilterInstance().requestTypeData!=null) {
            if (DS24Application.getFilterInstance().requestTypeData.rtypeId > 0)
                adapter=new RequestTypeAdapter(this,data,this,DS24Application.getFilterInstance().requestTypeData.rtypeId);
        }
        else
            adapter=new RequestTypeAdapter(this,data,this,-1);
        requestTypeRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onRequestTypeSelect(RequestTypeResponseData requestTypeData) {
        DS24Application.getFilterInstance().requestTypeData=requestTypeData;
        setResult(RESULT_OK);
        finish();
    }
}

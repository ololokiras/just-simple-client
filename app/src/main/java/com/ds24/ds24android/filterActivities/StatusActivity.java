package com.ds24.ds24android.filterActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.StatusAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.statusTree.StatusResponse;
import com.ds24.ds24android.retrofit.model.statusTree.StatusResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity implements StatusAdapter.StatusSelectListener {

    StatusAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView statusRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        statusRecycler=(RecyclerView)findViewById(R.id.status_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        statusRecycler.setLayoutManager(layoutManager);
        statusRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getStatusTreeList;
        Call<StatusResponse> statusCall=serverAPI.getStatuses(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        statusCall.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token){
                            fillRecycler(response.body().data);
                        }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<StatusResponseData> data) {
        adapter=new StatusAdapter(this,data,this);
        statusRecycler.setAdapter(adapter);
    }

    @Override
    public void onStatusSelect(StatusResponseData statusData) {
        DS24Application.getFilterInstance().statusData =statusData;
        setResult(RESULT_OK);
        finish();
    }
}

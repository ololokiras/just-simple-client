package com.ds24.ds24android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds24.ds24android.adapters.StatusChangeAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.status.Status;
import com.ds24.ds24android.retrofit.model.status.StatusData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusChangeActivity extends AppCompatActivity implements StatusChangeAdapter.StatusSelectListener {

    RecyclerView statusChangeRecycler;
    ServerAPI serverAPI;
    StatusChangeAdapter statusChangeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_change);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        initUI();
        doRequest();
    }

    private void initUI() {
        statusChangeRecycler=(RecyclerView)findViewById(R.id.status_change_recycler);
        statusChangeRecycler.setHasFixedSize(true);
        statusChangeRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getStatusList;
        Call<Status> statusCall=serverAPI.getStatusList(
                Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                ask);
        statusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            fillRecycler(response.body().data);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<StatusData> data){
        statusChangeAdapter=new StatusChangeAdapter(this,data,this);
        statusChangeRecycler.setAdapter(statusChangeAdapter);
    }

    @Override
    public void onStatusSelect(StatusData statusData) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.statusChange,statusData);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}

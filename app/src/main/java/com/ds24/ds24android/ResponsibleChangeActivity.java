package com.ds24.ds24android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds24.ds24android.adapters.filterAdapters.ResponsibleAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponse;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsibleChangeActivity extends AppCompatActivity implements ResponsibleAdapter.ResponsibleSelectListener {

    RecyclerView responsibleChangeRecycler;
    ServerAPI serverAPI;
    ResponsibleAdapter responsibleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsible_change);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        initUI();
        doRequest();
    }

    private void initUI() {
        responsibleChangeRecycler=(RecyclerView)findViewById(R.id.responsible_change_recycler);
        responsibleChangeRecycler.setHasFixedSize(true);
        responsibleChangeRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void doRequest(){
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getResponsibleList;
        Call<ResponsibleResponse> responseCall=serverAPI.getResponsibles(
                Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                ask);
        responseCall.enqueue(new Callback<ResponsibleResponse>() {
            @Override
            public void onResponse(Call<ResponsibleResponse> call, Response<ResponsibleResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            fillRecycler(response.body().data);
            }

            @Override
            public void onFailure(Call<ResponsibleResponse> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<ResponsibleResponseData> data) {
        responsibleAdapter=new ResponsibleAdapter(this,data,this);
        responsibleChangeRecycler.setAdapter(responsibleAdapter);
    }

    @Override
    public void onResponsibleSelect(ResponsibleResponseData responsibleData) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.responsibleChange,responsibleData);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}
package com.ds24.ds24android.filterActivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.ResponsibleAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponse;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponseData;
import com.ds24.ds24android.utils.Functions;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsibleActivity extends AppCompatActivity implements ResponsibleAdapter.ResponsibleSelectListener {

    ResponsibleAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView responsibleRecycler;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx=this;
        setContentView(R.layout.activity_simple_recycler);
        serverAPI= ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        responsibleRecycler=(RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        responsibleRecycler.setLayoutManager(layoutManager);
        responsibleRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getResponsibleList;
        Call<ResponsibleResponse> callResponsible=serverAPI.getResponsibles(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        callResponsible.enqueue(new Callback<ResponsibleResponse>() {
            @Override
            public void onResponse(Call<ResponsibleResponse> call, Response<ResponsibleResponse> response) {
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
            public void onFailure(Call<ResponsibleResponse> call, Throwable t) {
                Functions.showToastErrorMessage(ctx);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void fillRecycler(ArrayList<ResponsibleResponseData> data) {
        if(DS24Application.getFilterInstance().responsibleData!=null){
            if(DS24Application.getFilterInstance().responsibleData.respId>0)
                adapter=new ResponsibleAdapter(this,data,this,DS24Application.getFilterInstance().responsibleData.respId);
        }
        else
            adapter=new ResponsibleAdapter(this,data,this, -1);
        responsibleRecycler.setAdapter(adapter);
    }

    @Override
    public void onResponsibleSelect(ResponsibleResponseData responsibleData) {
        DS24Application.getFilterInstance().responsibleData=responsibleData;
        setResult(RESULT_OK);
        finish();
    }
}

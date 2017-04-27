package com.ds24.ds24android.filterActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.ContractorsAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.contractors.ContractorResponse;
import com.ds24.ds24android.retrofit.model.contractors.ContractorResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractsActivity extends AppCompatActivity implements ContractorsAdapter.ContractorSelectListener {

    ContractorsAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView contractorsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contracts);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        contractorsRecycler=(RecyclerView)findViewById(R.id.contractors_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        contractorsRecycler.setLayoutManager(layoutManager);
        contractorsRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getContractorsAction;
        Call<ContractorResponse> contractorResponseCall=serverAPI.getContractors(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        contractorResponseCall.enqueue(new Callback<ContractorResponse>() {
            @Override
            public void onResponse(Call<ContractorResponse> call, Response<ContractorResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token) {
                            fillRecycler(response.body().data);
                        }
            }

            @Override
            public void onFailure(Call<ContractorResponse> call, Throwable t) {

            }
        });
    }
    private void fillRecycler(ArrayList<ContractorResponseData> data){
        adapter=new ContractorsAdapter(this,data,this);
        contractorsRecycler.setAdapter(adapter);
    }

    @Override
    public void onContractorSelect(ContractorResponseData contractorResponseData) {
        DS24Application.getFilterInstance().contractorData=contractorResponseData;
        setResult(RESULT_OK);
        finish();

    }
}

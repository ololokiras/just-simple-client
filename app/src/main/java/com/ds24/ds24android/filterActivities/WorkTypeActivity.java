package com.ds24.ds24android.filterActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.WorkTypeAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.workType.WorkTypeResponse;
import com.ds24.ds24android.retrofit.model.workType.WorkTypeResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkTypeActivity extends AppCompatActivity implements WorkTypeAdapter.WorkTypeSelectListener {

    WorkTypeAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView workTypeRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_type);
        serverAPI= ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        workTypeRecycler=(RecyclerView)findViewById(R.id.work_type_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        workTypeRecycler.setLayoutManager(layoutManager);
        workTypeRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        SimpleAsk ask=new SimpleAsk();
        ask.act= Constants.getWorkTypeList;
        Call<WorkTypeResponse> callWorkType=serverAPI.getWorkTypes(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        callWorkType.enqueue(new Callback<WorkTypeResponse>() {
            @Override
            public void onResponse(Call<WorkTypeResponse> call, Response<WorkTypeResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token){
                            fillRecycler(response.body().data);
                        }

            }

            @Override
            public void onFailure(Call<WorkTypeResponse> call, Throwable t) {

            }
        });

    }

    private void fillRecycler(ArrayList<WorkTypeResponseData> data) {
        adapter= new WorkTypeAdapter(this,data,this);
        workTypeRecycler.setAdapter(adapter);
    }

    @Override
    public void onWorkTypeSelect(WorkTypeResponseData workTypeData) {
        DS24Application.getFilterInstance().workTypeData=workTypeData;
        setResult(RESULT_OK);
        finish();
    }
}

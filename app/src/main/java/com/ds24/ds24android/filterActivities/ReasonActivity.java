package com.ds24.ds24android.filterActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.ReasonAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.reason.ReasonAsk;
import com.ds24.ds24android.retrofit.model.reason.ReasonResponse;
import com.ds24.ds24android.retrofit.model.reason.ReasonResponseData;
import com.ds24.ds24android.retrofit.model.reason.Req;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReasonActivity extends AppCompatActivity implements ReasonAdapter.ReasonSelectListener {

    ReasonAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView reasonRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);
        serverAPI= ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        reasonRecycler=(RecyclerView)findViewById(R.id.reason_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        reasonRecycler.setLayoutManager(layoutManager);
        reasonRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        if(DS24Application.getFilterInstance().workTypeData==null)
            return;

        ReasonAsk ask=new ReasonAsk();
        ask.act= Constants.getReasonList;
        ask.req=new Req();
        ask.req.typeId=DS24Application.getFilterInstance().workTypeData.workTypeId;
        Call<ReasonResponse> callReason=serverAPI.getReasons(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        callReason.enqueue(new Callback<ReasonResponse>() {
            @Override
            public void onResponse(Call<ReasonResponse> call, Response<ReasonResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token){
                            fillRecycler(response.body().data);
                        }
            }

            @Override
            public void onFailure(Call<ReasonResponse> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<ReasonResponseData> data) {
        adapter=new ReasonAdapter(this,data,this);
        reasonRecycler.setAdapter(adapter);
    }

    @Override
    public void onReasonSelectListener(ReasonResponseData data) {
        DS24Application.getFilterInstance().reasonData=data;
        setResult(RESULT_OK);
        finish();
    }
}

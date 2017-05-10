package com.ds24.ds24android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ds24.ds24android.adapters.StatusReasonAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.statusReason.Req;
import com.ds24.ds24android.retrofit.model.statusReason.StatusReasonAsk;
import com.ds24.ds24android.retrofit.model.statusReason.StatusReasonResponse;
import com.ds24.ds24android.retrofit.model.statusReason.StatusReasonResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReasonStatusSelectActivity extends AppCompatActivity implements StatusReasonAdapter.ReasonSelectListener {

    RecyclerView reasonRecycler;
    ServerAPI serverAPI;
    StatusReasonAdapter statusReasonAdapter;
    int statusId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_status_select);
        statusId =getIntent().getIntExtra(Constants.statusId,-1);
        serverAPI= ServerAPI.retrofit.create(ServerAPI.class);
        initUI();
        doRequest();
    }


    private void initUI() {
        reasonRecycler=(RecyclerView)findViewById(R.id.reason_recycler);
        reasonRecycler.setHasFixedSize(true);
        reasonRecycler.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void doRequest() {
        StatusReasonAsk ask = new StatusReasonAsk();
        ask.act=Constants.getStatusReasonList;
        ask.req=new Req();
        ask.req.statusId=statusId;
        Call<StatusReasonResponse> call=serverAPI.getStatusReasons(
                Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                ask);
        call.enqueue(new Callback<StatusReasonResponse>() {
            @Override
            public void onResponse(Call<StatusReasonResponse> call, Response<StatusReasonResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            fillRecycler(response.body().data);

            }

            @Override
            public void onFailure(Call<StatusReasonResponse> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<StatusReasonResponseData> data) {
        statusReasonAdapter=new StatusReasonAdapter(this,data,this);
        reasonRecycler.setAdapter(statusReasonAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onReasonSelect(StatusReasonResponseData reasonData) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.reasonId,reasonData.reasonId);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}

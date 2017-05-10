package com.ds24.ds24android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

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
    int selectedStatusId;
    int incomeStatusId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_change);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        incomeStatusId=getIntent().getIntExtra(Constants.statusId,-1);
        initUI();
        doRequest();
    }

    private void initUI() {
        statusChangeRecycler=(RecyclerView)findViewById(R.id.status_change_recycler);
        statusChangeRecycler.setHasFixedSize(true);
        statusChangeRecycler.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        statusChangeAdapter=new StatusChangeAdapter(this,data,this,incomeStatusId);
        statusChangeRecycler.setAdapter(statusChangeAdapter);
    }

    @Override
    public void onStatusSelect(StatusData statusData) {
        if(statusData.reasons>0) {
            selectedStatusId=statusData.statusId;
            Intent intent=new Intent(this,ReasonStatusSelectActivity.class);
            intent.putExtra(Constants.statusId,statusData.statusId);
            startActivityForResult(intent,Constants.reasonActivityKey);
        }
        else{
           finishWithStatus(statusData);
        }
    }

    private void finishWithStatus(StatusData statusData){
        Intent intent=new Intent();
        intent.putExtra(Constants.statusId,statusData.statusId);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void finishWithStatusReason(int statusId, int reasonId){
        Intent intent=new Intent();
        intent.putExtra(Constants.statusId,statusId);
        intent.putExtra(Constants.reasonId,reasonId);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.reasonActivityKey)
            if(resultCode==RESULT_OK){
                if(data!=null){
                    int reasonId=data.getIntExtra(Constants.reasonId,-1);
                    if(reasonId>0)
                        finishWithStatusReason(selectedStatusId,reasonId);
                }
            }
    }
}

package com.ds24.ds24android.filterActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.StreetAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.SimpleAsk;
import com.ds24.ds24android.retrofit.model.streets.Req;
import com.ds24.ds24android.retrofit.model.streets.StreetCustomAsk;
import com.ds24.ds24android.retrofit.model.streets.StreetResponseData;
import com.ds24.ds24android.retrofit.model.streets.StreetResponse;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StreetActivity extends AppCompatActivity implements StreetAdapter.StreetSelectListener {

    StreetAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView streetsRecycler;
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
        streetsRecycler=(RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        streetsRecycler.setLayoutManager(layoutManager);
        streetsRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        Call<StreetResponse> streetCall;
        if(DS24Application.getFilterInstance().contractorData!=null){
            StreetCustomAsk streetCustomAsk=new StreetCustomAsk();
            streetCustomAsk.act=Constants.getStreetList;
            streetCustomAsk.req= new Req();
            streetCustomAsk.req.contractorId=DS24Application.getFilterInstance().contractorData.cntId;
            streetCall=serverAPI.getStreetsContractors(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        streetCustomAsk);
        }
        else {
            SimpleAsk ask=new SimpleAsk();
            ask.act=Constants.getStreetList;
            streetCall=serverAPI.getStreetsSimple(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                         ask);
        }
        streetCall.enqueue(new Callback<StreetResponse>() {
            @Override
            public void onResponse(Call<StreetResponse> call, Response<StreetResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().ok){
                        if(response.body().token)
                            fillRecycler(response.body().data);
                        else
                            Functions.restartToMainActivity();
                        } else
                            Functions.showToastErrorMessage(ctx);
                    } else
                        Functions.showToastErrorMessage(ctx);
                }

            @Override
            public void onFailure(Call<StreetResponse> call, Throwable t) {
                Functions.showToastErrorMessage(ctx);
            }
        });
    }

    private void fillRecycler(ArrayList<StreetResponseData> data) {
        if(DS24Application.getFilterInstance().streetData!=null) {
            if (!TextUtils.isEmpty(DS24Application.getFilterInstance().streetData.street))
                    adapter=new StreetAdapter(this,data,this,DS24Application.getFilterInstance().streetData.street);
        }
        else
            adapter=new StreetAdapter(this,data,this,"");
        streetsRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onStreetSelect(StreetResponseData streetData) {
        //DS24Application.getFilterInstance().streetData=streetData;
        Intent intent=new Intent();
        intent.putExtra(Constants.streets, streetData);
        setResult(RESULT_OK,intent);
        finish();
    }
}

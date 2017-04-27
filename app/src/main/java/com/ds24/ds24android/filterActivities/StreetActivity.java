package com.ds24.ds24android.filterActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        streetsRecycler=(RecyclerView)findViewById(R.id.street_recycler);
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
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token){
                            fillRecycler(response.body().data);
                        }
            }

            @Override
            public void onFailure(Call<StreetResponse> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<StreetResponseData> data) {
        adapter=new StreetAdapter(this,data,this);
        streetsRecycler.setAdapter(adapter);
    }

    @Override
    public void onStreetSelect(StreetResponseData streetData) {
        DS24Application.getFilterInstance().streetData=streetData;
        setResult(RESULT_OK);
        finish();
    }
}
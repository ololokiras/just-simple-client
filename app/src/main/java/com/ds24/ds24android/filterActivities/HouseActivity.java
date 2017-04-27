package com.ds24.ds24android.filterActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.HouseAdapter;
import com.ds24.ds24android.adapters.filterAdapters.StreetAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.house.HouseAsk;
import com.ds24.ds24android.retrofit.model.house.HouseResponse;
import com.ds24.ds24android.retrofit.model.house.HouseResponseData;
import com.ds24.ds24android.retrofit.model.house.Req;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseActivity extends AppCompatActivity implements HouseAdapter.HouseSelectListener {

    HouseAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView houseRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    private void initUI() {
        houseRecycler=(RecyclerView)findViewById(R.id.house_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        houseRecycler.setLayoutManager(layoutManager);
        houseRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        HouseAsk ask=new HouseAsk();
        ask.act= Constants.getHouseList;
        ask.req=new Req();
        if(DS24Application.filterInstance.contractorData.cntId>0)
            ask.req.contractorId=DS24Application.filterInstance.contractorData.cntId;
        else
            return;
        if(TextUtils.isEmpty(DS24Application.filterInstance.streetData.street))
            return;
        else
            ask.req.street=DS24Application.filterInstance.streetData.street;
        Call<HouseResponse> houseCall=serverAPI.getHouses(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        houseCall.enqueue(new Callback<HouseResponse>() {
            @Override
            public void onResponse(Call<HouseResponse> call, Response<HouseResponse> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token){
                            fillRecycler(response.body().data);
                        }
            }

            @Override
            public void onFailure(Call<HouseResponse> call, Throwable t) {

            }
        });
    }

    private void fillRecycler(ArrayList<HouseResponseData> data) {
        adapter=new HouseAdapter(this,data,this);
        houseRecycler.setAdapter(adapter);
    }

    @Override
    public void onHouseSelect(HouseResponseData houseData) {
        DS24Application.getFilterInstance().houseData=houseData;
        setResult(RESULT_OK);
        finish();
    }
}

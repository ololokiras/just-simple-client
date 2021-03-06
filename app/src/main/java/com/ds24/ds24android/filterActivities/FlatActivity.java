package com.ds24.ds24android.filterActivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;
import com.ds24.ds24android.adapters.filterAdapters.FlatAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.flat.FlatAsk;
import com.ds24.ds24android.retrofit.model.flat.FlatResponse;
import com.ds24.ds24android.retrofit.model.flat.FlatResponseData;
import com.ds24.ds24android.retrofit.model.flat.Req;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlatActivity extends AppCompatActivity implements FlatAdapter.FlatSelectListener {

    FlatAdapter adapter;
    ServerAPI serverAPI;
    RecyclerView flatRecycler;
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
        flatRecycler=(RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        flatRecycler.setLayoutManager(layoutManager);
        flatRecycler.setHasFixedSize(true);
        doRequest();
    }

    private void doRequest() {
        FlatAsk ask=new FlatAsk();
        if(DS24Application.filterInstance.houseData!=null) {
            ask.act= Constants.getFlatList;
            ask.req=new Req();
            ask.req.houseId=DS24Application.filterInstance.houseData.houseId;
        }
        else
            return;
        Call<FlatResponse> callFlat=serverAPI.getFlats(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        ask);
        callFlat.enqueue(new Callback<FlatResponse>() {
            @Override
            public void onResponse(Call<FlatResponse> call, Response<FlatResponse> response) {
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
            public void onFailure(Call<FlatResponse> call, Throwable t) {
                Functions.showToastErrorMessage(ctx);
            }
        });

    }

    private void fillRecycler(ArrayList<FlatResponseData> data) {
        if(DS24Application.getFilterInstance().flatData!=null){
            if(DS24Application.getFilterInstance().flatData.flatId>0)
                adapter=new FlatAdapter(this,data,this,DS24Application.getFilterInstance().flatData.flatId);
        }
        else
            adapter=new FlatAdapter(this,data,this,-1);
        flatRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onFlatSelect(FlatResponseData flatData) {
        DS24Application.getFilterInstance().flatData=flatData;
        setResult(RESULT_OK);
        finish();
    }
}

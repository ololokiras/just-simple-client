package com.ds24.ds24android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ds24.ds24android.adapters.RequestAdapter;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.ReqParams;
import com.ds24.ds24android.retrofit.model.asks.RequestionAsk;
import com.ds24.ds24android.retrofit.model.request.DataRequest;
import com.ds24.ds24android.retrofit.model.request.Requestion;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context ctx;
    ServerAPI serverAPI;

    RecyclerView recyclerRequest;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager layoutManager;

    RequestAdapter requestAdapter;
    ArrayList<DataRequest> dataRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx=this;
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.requests));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkLogin();
    }

    private void checkLogin() {
        String token= PreferencesBuffer.getToken(this);
        if(token==null){
            goToLogin();
        }
        else {
            initUI();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.loginActivityKey) {
            if (resultCode != RESULT_OK)
                return;

            initUI();
        }
    }

    private void initUI(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_main);

        recyclerRequest=(RecyclerView)findViewById(R.id.recycler_request);
        recyclerRequest.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerRequest.setLayoutManager(layoutManager);

        getRequests();
        swipeRefreshLayout.setOnRefreshListener(()->getRequests());
    }

    private void getRequests() {
        Call<Requestion> requestionCall=serverAPI.getRequestions(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        generateDefaultRequestionAsk(1));
        requestionCall.enqueue(new Callback<Requestion>() {
            @Override
            public void onResponse(Call<Requestion> call, Response<Requestion> response) {
                if(response.isSuccessful())
                    if(response.body().ok) {
                        if (response.body().token) {
                            dataRequests=response.body().data;
                            fillRecycler();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else
                            logOut();
                    }
            }

            @Override
            public void onFailure(Call<Requestion> call, Throwable t) {

            }
        });
    }

    private void fillRecycler() {
        requestAdapter=new RequestAdapter(this,dataRequests);
        requestAdapter.setLoadMoreListener(new RequestAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerRequest.post(new Runnable() {
                    @Override
                    public void run() {
                        int index=dataRequests.size()-1;
                        loadMore(index);
                    }
                });
            }
        });
        recyclerRequest.setAdapter(requestAdapter);
    }

    private void loadMore(int index) {
        dataRequests.add(null);
        requestAdapter.notifyItemInserted(dataRequests.size()-1);
        RequestionAsk requestionAsk=generateDefaultRequestionAsk(index);
        Call<Requestion> requestionCall=serverAPI.getRequestions(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        requestionAsk);
        requestionCall.enqueue(new Callback<Requestion>() {
            @Override
            public void onResponse(Call<Requestion> call, Response<Requestion> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token){
                            dataRequests.remove(dataRequests.size()-1);
                            ArrayList<DataRequest> results=response.body().data;
                            if(results.size()>0){
                                dataRequests.addAll(results);
                                requestAdapter.setMoreDataAvailable(true);

                                //fillRecycler();
                            }
                            else {
                                //requestAdapter.setMoreDataAvailable(false);
                            }
                            requestAdapter.notifyDataChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else
                            logOut();
            }

            @Override
            public void onFailure(Call<Requestion> call, Throwable t) {

            }
        });
    }

    private RequestionAsk generateDefaultRequestionAsk(int start) {
        RequestionAsk requestionAsk=new RequestionAsk();
        requestionAsk.act=Constants.getRequestListAction;
        requestionAsk.req=new ReqParams();
        requestionAsk.req.start=start;
        requestionAsk.req.count=Constants.paginationSize;

        return requestionAsk;
    }

    private void goToLogin() {
        Intent loginIntent=new Intent(this,LoginActivity.class);
        startActivityForResult(loginIntent, Constants.loginActivityKey);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            startFilterActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startFilterActivity() {
        Intent intent=new Intent(this,FilterActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_logout){
            logOut();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        PreferencesBuffer.eraseToken(this);
        Intent intent=getIntent();
        finish();
        startActivity(intent);
    }
}

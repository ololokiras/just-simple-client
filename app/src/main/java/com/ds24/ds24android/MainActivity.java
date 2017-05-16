package com.ds24.ds24android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ds24.ds24android.adapters.PaginationAdapter;
import com.ds24.ds24android.adapters.PaginationAdapterCallback;
import com.ds24.ds24android.adapters.PaginationScrollListener;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.asks.ReqParams;
import com.ds24.ds24android.retrofit.model.asks.RequestionAsk;
import com.ds24.ds24android.retrofit.model.request.DataRequest;
import com.ds24.ds24android.retrofit.model.request.Requestion;
import com.ds24.ds24android.retrofit.model.statusTree.StatusResponseData;
import com.ds24.ds24android.utils.Functions;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PaginationAdapter.OnRequestSelectedListener,
        PaginationAdapterCallback{

    Context ctx;
    ServerAPI serverAPI;

    RecyclerView recyclerRequest;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager layoutManager;
    ProgressBar progressBar;

    PaginationAdapter adapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    ArrayList<DataRequest> dataRequests;

    int lastFirstVisiblePosition=0;

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

        createDefaultFilter();

        checkLogin();
    }

    private void createDefaultFilter(){
        DS24Application.getFilterInstance().statusData=new StatusResponseData();
        DS24Application.getFilterInstance().statusData.status=Constants.defaultStatusDataName;
        DS24Application.getFilterInstance().statusData.statusId=Constants.defaultStatusDataIds;
        rememberFilterState();
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
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_main);

        recyclerRequest=(RecyclerView)findViewById(R.id.recycler_request);
        recyclerRequest.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerRequest.setLayoutManager(layoutManager);
        recyclerRequest.setItemAnimator(new DefaultItemAnimator());


        recyclerRequest.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading=true;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        getRequests();
        swipeRefreshLayout.setOnRefreshListener(()->getRequests());
    }

    private void getRequests() {
        dataRequests=new ArrayList<>();
        recyclerRequest.setAdapter(null);
        adapter=new PaginationAdapter(this,this);
        recyclerRequest.setAdapter(adapter);
        Call<Requestion> requestionCall=serverAPI.getRequestions(
                                        Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                                        generateDefaultRequestionAsk(1));

        requestionCall.enqueue(new Callback<Requestion>() {
            @Override
            public void onResponse(Call<Requestion> call, Response<Requestion> response) {
                if(response.isSuccessful()) {
                    if (response.body().ok) {
                        if (response.body().token) {
                            dataRequests = response.body().data;
                            adapter.addAll(dataRequests);
                            stopProgress();
                            swipeRefreshLayout.setRefreshing(false);

                            if(dataRequests.size()>=Constants.paginationSize) {
                                adapter.addLoadingFooter();
                                isLastPage = false;
                            }
                            else
                                isLastPage=true;
                        } else Functions.restartToMainActivity();
                    } else Functions.showToastErrorMessage(ctx);
                } else
                    Functions.showToastErrorMessage(ctx);

                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<Requestion> call, Throwable t) {
                stopProgress();
                Toasty.error(ctx,Functions.fetchErrorMessage(t, ctx)).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void loadNextPage(){
        int index=dataRequests.size()+1;
        RequestionAsk requestionAsk=generateDefaultRequestionAsk(index);
        Call<Requestion> requestionCall=serverAPI.getRequestions(
                Functions.encodeToBase64(PreferencesBuffer.getToken(this)),
                requestionAsk);
        requestionCall.enqueue(new Callback<Requestion>() {
            @Override
            public void onResponse(Call<Requestion> call, Response<Requestion> response) {
                if (response.isSuccessful()) {
                    if (response.body().ok) {
                        if (response.body().token) {
                            adapter.removeLoadingFooter();
                            isLoading = false;

                            ArrayList<DataRequest> results = response.body().data;
                            dataRequests.addAll(results);
                            adapter.addAll(results);

                            if (results.size() < Constants.paginationSize)
                                isLastPage = true;
                            else
                                adapter.addLoadingFooter();
                        } else Functions.restartToMainActivity();
                    } else Functions.showToastErrorMessage(ctx);
                }   else Functions.showToastErrorMessage(ctx);
            }

            @Override
            public void onFailure(Call<Requestion> call, Throwable t) {
                adapter.showRetry(true,Functions.fetchErrorMessage(t,ctx));
                Toasty.error(ctx,Functions.fetchErrorMessage(t,ctx)).show();
            }
        });
    }

    private void stopProgress(){
        progressBar.setVisibility(View.GONE);
    }

    private RequestionAsk generateDefaultRequestionAsk(int start) {
        RequestionAsk requestionAsk=new RequestionAsk();
        requestionAsk.act=Constants.getRequestListAction;
        requestionAsk.req=new ReqParams();
        requestionAsk.req.start=start;
        requestionAsk.req.count=Constants.paginationSize;
        if(DS24Application.getFilterInstance().contractorData!=null)
            requestionAsk.req.cntId=DS24Application.getFilterInstance().contractorData.cntId;

        if(DS24Application.getFilterInstance().streetData!=null)
            requestionAsk.req.street=DS24Application.getFilterInstance().streetData.street;

        if(DS24Application.getFilterInstance().houseData!=null)
            requestionAsk.req.houseId=DS24Application.getFilterInstance().houseData.houseId;

        if(DS24Application.getFilterInstance().flatData!=null)
            requestionAsk.req.flatId=DS24Application.getFilterInstance().flatData.flatId;

        if(DS24Application.getFilterInstance().workTypeData!=null)
            requestionAsk.req.typeId=DS24Application.getFilterInstance().workTypeData.workTypeId;

        if(DS24Application.getFilterInstance().reasonData!=null)
            requestionAsk.req.essId=DS24Application.getFilterInstance().reasonData.reasonId;

        if(DS24Application.getFilterInstance().employeeData!=null)
            requestionAsk.req.empId=DS24Application.getFilterInstance().employeeData.empId;

        if(DS24Application.getFilterInstance().responsibleData!=null)
            requestionAsk.req.respId=DS24Application.getFilterInstance().responsibleData.respId;

        if(DS24Application.getFilterInstance().statusData!=null)
            requestionAsk.req.statusId=DS24Application.getFilterInstance().statusData.statusId;

        if(DS24Application.getFilterInstance().requestTypeData!=null)
            requestionAsk.req.rtypeId=DS24Application.getFilterInstance().requestTypeData.rtypeId;

        if(!TextUtils.isEmpty(DS24Application.getFilterInstance().startDate))
            requestionAsk.req.dateStart=DS24Application.getFilterInstance().startDate;

        if(!TextUtils.isEmpty(DS24Application.getFilterInstance().endDate))
            requestionAsk.req.dateEnd=DS24Application.getFilterInstance().endDate;

        return requestionAsk;
    }

    @Override
    public void onRequestSelect(int requestId) {
        Intent detailRequestionIntent=new Intent(this, DetailedActivity.class);
        detailRequestionIntent.putExtra(Constants.requestId,requestId);
        rememberFilterState();
        startActivity(detailRequestionIntent);
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
            rememberFilterState();
            startFilterActivity();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void rememberFilterState(){
        DS24Application.previousFilterState=DS24Application.getFilterInstance().clone();
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
            Functions.restartToMainActivity();
        }

        if(id==R.id.nav_requests){
            initUI();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume(){
        super.onResume();
        if(!TextUtils.isEmpty(PreferencesBuffer.getToken(ctx))) {
            if (!Functions.compareFilterState())
                initUI();
            else{
                if(lastFirstVisiblePosition>0) {
                    recyclerRequest.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
                    lastFirstVisiblePosition = 0;
                }
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(recyclerRequest!=null)
            lastFirstVisiblePosition=((LinearLayoutManager)recyclerRequest.getLayoutManager()).findFirstVisibleItemPosition();
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }
}

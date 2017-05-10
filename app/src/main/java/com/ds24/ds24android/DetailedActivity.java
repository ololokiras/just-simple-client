package com.ds24.ds24android;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.adapters.PagerAdapter;

public class DetailedActivity extends AppCompatActivity {

    TabLayout tabLayout;
    int requestId;
    ViewPager viewPager;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        requestId=getIntent().getIntExtra(Constants.requestId,-1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(requestId>0)
            initUI();
    }

    private void initUI(){
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.detail)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.comments)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.updates)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),requestId);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setHeader();
    }

    private void setHeader(){
        getSupportActionBar().setTitle("Заявка #"+requestId);
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
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}

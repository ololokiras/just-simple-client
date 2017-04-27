package com.ds24.ds24android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ds24.ds24android.filterActivities.PeriodActivity;
import com.ds24.ds24android.filterActivities.ContractsActivity;
import com.ds24.ds24android.filterActivities.EmployeeActivity;
import com.ds24.ds24android.filterActivities.FlatActivity;
import com.ds24.ds24android.filterActivities.HouseActivity;
import com.ds24.ds24android.filterActivities.ReasonActivity;
import com.ds24.ds24android.filterActivities.RequestTypeActivity;
import com.ds24.ds24android.filterActivities.ResponsibleActivity;
import com.ds24.ds24android.filterActivities.StatusActivity;
import com.ds24.ds24android.filterActivities.StreetActivity;
import com.ds24.ds24android.filterActivities.WorkTypeActivity;
import com.ds24.ds24android.repository.Constants;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();
    }

    private void initUI() {
        RelativeLayout contractorsLayout=(RelativeLayout)findViewById(R.id.contractors_layout);
        contractorsLayout.setOnClickListener(v->contractorsFilter());

        RelativeLayout streetLayout=(RelativeLayout)findViewById(R.id.street_layout);
        streetLayout.setOnClickListener(v->streetFilter());

        RelativeLayout houseLayout=(RelativeLayout)findViewById(R.id.house_layout);
        houseLayout.setOnClickListener(v->houseFilter());

        RelativeLayout flatLayout=(RelativeLayout)findViewById(R.id.flat_layout);
        flatLayout.setOnClickListener(v->flatFilter());

        RelativeLayout workTypeLayout=(RelativeLayout)findViewById(R.id.work_type_layout);
        workTypeLayout.setOnClickListener(v->workTypeFilter());

        RelativeLayout reasonLayout=(RelativeLayout)findViewById(R.id.reason_layout);
        reasonLayout.setOnClickListener(v->reasonFilter());

        RelativeLayout responsibleLayout=(RelativeLayout)findViewById(R.id.responsible_layout);
        responsibleLayout.setOnClickListener(v->responsibleFilter());

        RelativeLayout employeeLayout=(RelativeLayout)findViewById(R.id.employee_layout);
        employeeLayout.setOnClickListener(v->employeeFilter());

        RelativeLayout statusLayout=(RelativeLayout)findViewById(R.id.status_layout);
        statusLayout.setOnClickListener(v->statusFilter());

        RelativeLayout requestTypeLayout=(RelativeLayout)findViewById(R.id.request_type_layout);
        requestTypeLayout.setOnClickListener(v->requestTypeFilter());

        RelativeLayout periodLayout=(RelativeLayout)findViewById(R.id.period_layout);
        periodLayout.setOnClickListener(v->periodFilter());
    }

    private void contractorsFilter() {
        Intent intent=new Intent(this, ContractsActivity.class);
        startActivityForResult(intent, Constants.contractorsActivityKey);
    }

    private void streetFilter() {
        Intent intent=new Intent(this, StreetActivity.class);
        startActivityForResult(intent,Constants.streetActivityKey);
    }

    private void houseFilter() {
        //ебануть проверку на лицо и улицу
        Intent intent=new Intent(this, HouseActivity.class);
        startActivity(intent);
    }

    private void flatFilter() {
        Intent intent=new Intent(this, FlatActivity.class);
        startActivity(intent);
    }

    private void workTypeFilter() {
        Intent intent=new Intent(this, WorkTypeActivity.class);
        startActivity(intent);
    }

    private void reasonFilter() {
        Intent intent=new Intent(this, ReasonActivity.class);
        startActivity(intent);
    }

    private void responsibleFilter() {
        Intent intent=new Intent(this, ResponsibleActivity.class);
        startActivity(intent);
    }

    private void employeeFilter() {
        Intent intent=new Intent(this, EmployeeActivity.class);
        startActivity(intent);
    }

    private void statusFilter() {
        Intent intent=new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    private void requestTypeFilter() {
        Intent intent=new Intent(this, RequestTypeActivity.class);
        startActivity(intent);
    }

    private void periodFilter() {
        Intent intent=new Intent(this, PeriodActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}

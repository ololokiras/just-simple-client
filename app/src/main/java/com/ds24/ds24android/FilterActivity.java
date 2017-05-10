package com.ds24.ds24android;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.ds24.ds24android.retrofit.model.contractors.ContractorResponseData;
import com.ds24.ds24android.retrofit.model.house.HouseResponseData;
import com.ds24.ds24android.retrofit.model.streets.StreetResponseData;
import com.ds24.ds24android.retrofit.model.workType.WorkTypeResponseData;
import com.ds24.ds24android.utils.Functions;

import org.w3c.dom.Text;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class FilterActivity extends AppCompatActivity {

    TextView contractorSelected;
    TextView streetSelected;
    TextView houseSelected;
    TextView flatSelected;
    TextView workTypeSelected;
    TextView reasonSelected;
    TextView responsibleSelected;
    TextView employeeSelected;
    TextView statusSelected;
    TextView requestTypeSelected;
    TextView periodSelected;

    ImageView contractorsDelete;
    ImageView streetDelete;
    ImageView houseDelete;
    ImageView flatDelete;
    ImageView workTypeDelete;
    ImageView reasonDelete;
    ImageView responsibleDelete;
    ImageView employeeDelete;
    ImageView statusDelete;
    ImageView requestTypeDelete;
    ImageView periodDelete;


    RelativeLayout clearFilterLayout;

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

        clearFilterLayout=(RelativeLayout)findViewById(R.id.clear_filter_layout);
        clearFilterLayout.setOnClickListener(v->clearFilter());

        contractorSelected=(TextView)findViewById(R.id.contractor_selected);
        streetSelected=(TextView)findViewById(R.id.street_selected);
        houseSelected=(TextView)findViewById(R.id.house_selected);
        flatSelected=(TextView)findViewById(R.id.flat_selected);
        workTypeSelected=(TextView)findViewById(R.id.work_type_selected);
        reasonSelected=(TextView)findViewById(R.id.reason_selected);
        responsibleSelected=(TextView)findViewById(R.id.responsible_selected);
        employeeSelected=(TextView)findViewById(R.id.employee_selected);
        statusSelected=(TextView)findViewById(R.id.status_selected);
        requestTypeSelected=(TextView)findViewById(R.id.request_type_selected);
        periodSelected=(TextView)findViewById(R.id.period_selected);

        contractorsDelete=(ImageView)findViewById(R.id.contractors_delete);
        contractorsDelete.setOnClickListener(v->contractorsClear());

        streetDelete=(ImageView)findViewById(R.id.street_delete);
        streetDelete.setOnClickListener(v->streetClear());

        houseDelete=(ImageView)findViewById(R.id.house_delete);
        houseDelete.setOnClickListener(v -> houseClear());

        flatDelete=(ImageView)findViewById(R.id.flat_delete);
        flatDelete.setOnClickListener(v -> flatClear());

        workTypeDelete=(ImageView)findViewById(R.id.work_type_delete);
        workTypeDelete.setOnClickListener(v -> workTypeClear());

        reasonDelete=(ImageView)findViewById(R.id.reason_delete);
        reasonDelete.setOnClickListener(v -> reasonClear());

        responsibleDelete=(ImageView)findViewById(R.id.responsible_delete);
        responsibleDelete.setOnClickListener(v -> responsibleClear());

        employeeDelete=(ImageView)findViewById(R.id.employee_delete);
        employeeDelete.setOnClickListener(v -> employeeClear());

        statusDelete=(ImageView)findViewById(R.id.status_delete);
        statusDelete.setOnClickListener(v -> statusClear());

        requestTypeDelete=(ImageView)findViewById(R.id.request_type_delete);
        requestTypeDelete.setOnClickListener(v -> requestTypeClear());

        periodDelete=(ImageView)findViewById(R.id.period_delete);
        periodDelete.setOnClickListener(v -> periodClear());
    }

    private void contractorsClear(){
        DS24Application.clearContractor();
        fillSelectedFields();
    }

    private void streetClear(){
        DS24Application.clearStreet();
        fillSelectedFields();
    }

    private void houseClear(){
        DS24Application.clearHouse();
        fillSelectedFields();
    }

    private void flatClear(){
        DS24Application.clearFlat();
        fillSelectedFields();
    }

    private void workTypeClear(){
        DS24Application.clearWorkType();
        fillSelectedFields();
    }

    private void reasonClear(){
        DS24Application.clearReason();
        fillSelectedFields();
    }

    private void responsibleClear(){
        DS24Application.clearResponsible();
        fillSelectedFields();
    }

    private void employeeClear(){
        DS24Application.clearEmployee();
        fillSelectedFields();
    }

    private void statusClear(){
        DS24Application.clearStatus();
        fillSelectedFields();
    }

    private void requestTypeClear(){
        DS24Application.clearRequestType();
        fillSelectedFields();
    }

    private void periodClear(){
        DS24Application.clearPeriod();
        fillSelectedFields();
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
        if(DS24Application.getFilterInstance().streetData!=null) {
            Intent intent = new Intent(this, HouseActivity.class);
            startActivityForResult(intent,Constants.houseActivityKey);
        }
        else
            Toasty.warning(this,getString(R.string.select_street)).show();
    }

    private void flatFilter() {
        if(DS24Application.getFilterInstance().houseData!=null) {
            Intent intent = new Intent(this, FlatActivity.class);
            startActivity(intent);
        }
        else
            Toasty.warning(this,getString(R.string.select_house)).show();
    }

    private void workTypeFilter() {
        Intent intent=new Intent(this, WorkTypeActivity.class);
        startActivityForResult(intent,Constants.workTypeChangeActivityKey);
    }

    private void reasonFilter() {
        if(DS24Application.getFilterInstance().workTypeData!=null) {
            Intent intent = new Intent(this, ReasonActivity.class);
            startActivity(intent);
        }
        else
            Toasty.warning(this,getString(R.string.select_work_type)).show();
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

    private void fillSelectedFields(){
        if(DS24Application.getFilterInstance().contractorData!=null)
            contractorSelected.setText(DS24Application.getFilterInstance().contractorData.cnt);
        else
            contractorSelected.setText("");

        if(DS24Application.getFilterInstance().streetData!=null)
            streetSelected.setText(DS24Application.getFilterInstance().streetData.street);
        else
            streetSelected.setText("");

        if(DS24Application.getFilterInstance().houseData!=null)
            houseSelected.setText(DS24Application.getFilterInstance().houseData.house);
        else houseSelected.setText("");

        if(DS24Application.getFilterInstance().flatData!=null)
            flatSelected.setText(DS24Application.getFilterInstance().flatData.flat);
        else
            flatSelected.setText("");

        if(DS24Application.getFilterInstance().workTypeData!=null)
            workTypeSelected.setText(DS24Application.getFilterInstance().workTypeData.workType);
        else
            workTypeSelected.setText("");

        if(DS24Application.getFilterInstance().reasonData!=null)
            reasonSelected.setText(DS24Application.getFilterInstance().reasonData.reason);
        else
            reasonSelected.setText("");

        if(DS24Application.getFilterInstance().responsibleData!=null)
            responsibleSelected.setText(DS24Application.getFilterInstance().responsibleData.resp);
        else
            responsibleSelected.setText("");

        if(DS24Application.getFilterInstance().employeeData!=null)
            employeeSelected.setText(DS24Application.getFilterInstance().employeeData.emp);
        else
            employeeSelected.setText("");

        if(DS24Application.getFilterInstance().statusData!=null) {
            statusSelected.setText(Functions.prepareTreeToSimpleString(DS24Application.getFilterInstance().statusData.status));
            Log.v("status_data", DS24Application.getFilterInstance().statusData.statusId);
        }
        else
            statusSelected.setText("");

        if(DS24Application.getFilterInstance().requestTypeData!=null)
            requestTypeSelected.setText(DS24Application.getFilterInstance().requestTypeData.rtype);
        else
            requestTypeSelected.setText("");

        String dateString="";
        if(!TextUtils.isEmpty(DS24Application.getFilterInstance().startDate)) {
            dateString+="от ";
            dateString += DS24Application.getFilterInstance().startDate;
        }

        if(!TextUtils.isEmpty(DS24Application.getFilterInstance().endDate)) {
            dateString+=" по ";
            dateString += DS24Application.getFilterInstance().endDate;
        }

        periodSelected.setText(dateString);
    }

    private void clearFilter(){
        DS24Application.clearFilter();
        fillSelectedFields();
    }

    @Override
    protected void onResume(){
        super.onResume();
        fillSelectedFields();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.contractorsActivityKey) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    ContractorResponseData result = (ContractorResponseData) data.getSerializableExtra(Constants.contractors);
                    if (result != null) {
                        if (DS24Application.getFilterInstance().contractorData != null) {
                            if (DS24Application.getFilterInstance().contractorData.cntId != result.cntId) {
                                contractorsClear();
                                DS24Application.getFilterInstance().contractorData = result;
                                fillSelectedFields();
                            }
                        } else {
                            DS24Application.getFilterInstance().contractorData = result;
                            fillSelectedFields();
                        }
                    }
                }
            }
        }

        if(requestCode==Constants.streetActivityKey){
            if(resultCode==RESULT_OK){
                if(data!=null) {
                    StreetResponseData result = (StreetResponseData) data.getSerializableExtra(Constants.streets);
                    if (result != null) {
                        if (DS24Application.getFilterInstance().streetData != null) {
                            if (!DS24Application.getFilterInstance().streetData.street.equals(result.street)) {
                                streetClear();
                                DS24Application.getFilterInstance().streetData = result;
                                fillSelectedFields();
                            }
                        } else {
                            DS24Application.getFilterInstance().streetData = result;
                            fillSelectedFields();
                        }
                    }
                }
            }
        }

        if(requestCode==Constants.houseActivityKey){
            if(resultCode==RESULT_OK){
                if(data!=null){
                    HouseResponseData result= (HouseResponseData) data.getSerializableExtra(Constants.houses);
                    if(result!=null){
                        if(DS24Application.getFilterInstance().houseData!=null){
                            if(DS24Application.getFilterInstance().houseData.houseId!=result.houseId){
                                houseClear();
                                DS24Application.getFilterInstance().houseData=result;
                                fillSelectedFields();
                            }
                        } else {
                            DS24Application.getFilterInstance().houseData=result;
                            fillSelectedFields();
                        }
                    }
                }
            }
        }

        if(requestCode==Constants.workTypeChangeActivityKey){
            if(resultCode==RESULT_OK){
                if(data!=null){
                    WorkTypeResponseData result= (WorkTypeResponseData) data.getSerializableExtra(Constants.workType);
                    if(result!=null){
                        if(DS24Application.getFilterInstance().workTypeData!=null){
                            if(DS24Application.getFilterInstance().workTypeData.workTypeId!=result.workTypeId){
                                workTypeClear();
                                DS24Application.getFilterInstance().workTypeData=result;
                                fillSelectedFields();
                            }
                        } else {
                            DS24Application.getFilterInstance().workTypeData=result;
                            fillSelectedFields();
                        }
                    }
                }
            }
        }
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
}

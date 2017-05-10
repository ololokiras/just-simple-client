package com.ds24.ds24android.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ds24.ds24android.EmployeeChangeActivity;
import com.ds24.ds24android.R;
import com.ds24.ds24android.ResponsibleChangeActivity;
import com.ds24.ds24android.StatusChangeActivity;
import com.ds24.ds24android.repository.Constants;
import com.ds24.ds24android.repository.PreferencesBuffer;
import com.ds24.ds24android.retrofit.ServerAPI;
import com.ds24.ds24android.retrofit.model.commentUpdate.RequestUpdate;
import com.ds24.ds24android.retrofit.model.commentUpdate.RequestionDetailParams;
import com.ds24.ds24android.retrofit.model.employee.EmployeeResponseData;
import com.ds24.ds24android.retrofit.model.requestDetail.Req;
import com.ds24.ds24android.retrofit.model.requestDetail.RequestDetail;
import com.ds24.ds24android.retrofit.model.requestDetail.RequestDetailAsk;
import com.ds24.ds24android.retrofit.model.requestDetail.RequestDetailData;
import com.ds24.ds24android.retrofit.model.responsible.ResponsibleResponseData;
import com.ds24.ds24android.retrofit.model.updateDeadline.DeadlineUpdateAsk;
import com.ds24.ds24android.retrofit.model.updateDeadline.DeadlineUpdateData;
import com.ds24.ds24android.retrofit.model.updateEmployee.EmployeeUpdateAsk;
import com.ds24.ds24android.retrofit.model.updateEmployee.EmployeeUpdateData;
import com.ds24.ds24android.retrofit.model.updateResponsible.ResponsibleUpdateAsk;
import com.ds24.ds24android.retrofit.model.updateResponsible.ResponsibleUpdateData;
import com.ds24.ds24android.retrofit.model.updateStatus.StatusReasonUpdateAsk;
import com.ds24.ds24android.retrofit.model.updateStatus.StatusReasonUpdateData;
import com.ds24.ds24android.retrofit.model.updateStatus.StatusUpdateAsk;
import com.ds24.ds24android.retrofit.model.updateStatus.StatusUpdateData;
import com.ds24.ds24android.utils.Functions;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int requestId;

    TextView noteText;
    TextView addressText;
    TextView statusText;
    TextView empText;
    TextView respText;
    TextView deadlineText;
    TextView aonText;
    TextView contPhoneText;
    TextView workTypeText;
    TextView createdAtText;

    RelativeLayout statusChangeLayout;
    RelativeLayout employeeChangeLayout;
    RelativeLayout responsibleChangeLayout;
    RelativeLayout deadlineChangeLayout;

    RelativeLayout aonCallLayout;
    RelativeLayout conPhoneLayout;

    SwipeRefreshLayout swipeRefreshLayout;

    ServerAPI serverAPI;

    RequestDetailData detailData;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requestId=getArguments().getInt(Constants.requestId);
        }
        serverAPI=ServerAPI.retrofit.create(ServerAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        addressText=(TextView)rootView.findViewById(R.id.address);

        aonText=(TextView)rootView.findViewById(R.id.aon_value);
        contPhoneText=(TextView)rootView.findViewById(R.id.cont_phone_value);
        aonCallLayout=(RelativeLayout)rootView.findViewById(R.id.aon_layout);
        aonCallLayout.setOnClickListener(v->aonCall());
        conPhoneLayout=(RelativeLayout)rootView.findViewById(R.id.cont_phone_layout);
        conPhoneLayout.setOnClickListener(v->contPhoneCall());

        noteText=(TextView)rootView.findViewById(R.id.note);
        workTypeText=(TextView)rootView.findViewById(R.id.work_type);
        createdAtText=(TextView)rootView.findViewById(R.id.created_at_text);

        statusText=(TextView)rootView.findViewById(R.id.status_value);
        statusChangeLayout=(RelativeLayout)rootView.findViewById(R.id.status_change_layout);
        statusChangeLayout.setOnClickListener(v->changeStatus());

        empText=(TextView)rootView.findViewById(R.id.employee_value);
        employeeChangeLayout=(RelativeLayout)rootView.findViewById(R.id.employee_change_layout);
        employeeChangeLayout.setOnClickListener(v->changeEmployee());

        respText=(TextView)rootView.findViewById(R.id.responsible_value);
        responsibleChangeLayout=(RelativeLayout)rootView.findViewById(R.id.responsible_change_layout);
        responsibleChangeLayout.setOnClickListener(v->changeResponsible());

        deadlineText=(TextView)rootView.findViewById(R.id.deadline_value);
        deadlineChangeLayout=(RelativeLayout)rootView.findViewById(R.id.deadline_change_layout);
        deadlineChangeLayout.setOnClickListener(v->changeDeadline());


        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_detail);
        swipeRefreshLayout.setOnRefreshListener(()->doRequest(requestId));
        doRequest(requestId);


        return rootView;
    }

    private void doRequest(int requestId) {
        RequestDetailAsk requestionDetailAsk=new RequestDetailAsk();
        requestionDetailAsk.act= Constants.getRequestDetail;
        requestionDetailAsk.req=new Req();
        requestionDetailAsk.req.requestId=requestId;

        Call<RequestDetail> callDetail=serverAPI.getRequestDetail(
                Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplicationContext())),
                requestionDetailAsk);

        callDetail.enqueue(new Callback<RequestDetail>() {
            @Override
            public void onResponse(Call<RequestDetail> call, Response<RequestDetail> response) {
                if(response.isSuccessful()){
                    if(response.body().ok)
                        if(response.body().token) {
                            fillContent(response.body().data);
                            detailData = response.body().data;
                        }
                }
            }

            @Override
            public void onFailure(Call<RequestDetail> call, Throwable t) {

            }
        });

    }

    private void fillContent(RequestDetailData data) {
        String addressString="";
        addressString+=data.house;
        if(!TextUtils.isEmpty(data.floor))
            addressString+=" эт.: "+data.floor;
        if(!TextUtils.isEmpty(data.flat))
            addressString+=" кв. "+data.flat;
        if(!TextUtils.isEmpty(data.entr))
            addressString+=" п. "+data.entr;

        addressText.setText(addressString);
        aonText.setText(data.autophone);
        contPhoneText.setText(data.contphone);
        noteText.setText(data.note);
        workTypeText.setText(data.type+", "+data.ess);

        String createdTitle="Создан ";
        createdTitle+=data.createdBy+" "+data.createdAt;

        if(!TextUtils.isEmpty(data.updatedAt))
            createdTitle+=" \n Обновлена "+data.updatedAt;

        createdAtText.setText(createdTitle);


        String statusString=data.status;
        if(!TextUtils.isEmpty(data.reason))
            statusString+=", "+data.reason;
        statusText.setText(statusString);

        empText.setText(data.emp);
        respText.setText(data.resp);
        if(!TextUtils.isEmpty(data.deadlineAt))
            deadlineText.setText(data.deadlineAt);

        swipeRefreshLayout.setRefreshing(false);
    }


    private void aonCall(){
        if(checkCallPermission()) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + aonText.getText().toString()));
            startActivity(callIntent);
        }
    }

    private void contPhoneCall(){
        if(checkCallPermission()) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contPhoneText.getText().toString()));
            startActivity(callIntent);
        }
    }

    private boolean checkCallPermission() {
        int permission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE);
        if(permission==PackageManager.PERMISSION_GRANTED)
            return true;

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CALL_PHONE},
                Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE);

        return false;
    }

    private void changeStatus(){
        Intent intent=new Intent(getActivity(), StatusChangeActivity.class);
        intent.putExtra(Constants.statusId,detailData.statusId);
        startActivityForResult(intent,Constants.statusChangeActivityKey);
    }

    private void changeEmployee(){
        Intent intent=new Intent(getActivity(), EmployeeChangeActivity.class);
        intent.putExtra(Constants.employeeId,detailData.empId);
        startActivityForResult(intent,Constants.employeeChangeActivityKey);
    }

    private void changeResponsible(){
        Intent intent=new Intent(getActivity(), ResponsibleChangeActivity.class);
        intent.putExtra(Constants.responsibleId,detailData.respId);
        startActivityForResult(intent,Constants.responsibleChangeActivityKey);
    }

    private void changeDeadline(){
        Calendar newCalendar=Calendar.getInstance();
        SimpleDateFormat  dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            updateDeadline(dateFormatter.format(newDate.getTime()));
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.statusChangeActivityKey){
            if(resultCode==RESULT_OK){
                if(data!=null){
                    int statusId=data.getIntExtra(Constants.statusId,-1);
                    int reasonId=data.getIntExtra(Constants.reasonId,-1);
                    if(statusId>0 && reasonId>0)
                        updateStatusWithReason(statusId,reasonId);
                    if(statusId>0 && reasonId<1)
                        updateStatus(statusId);
                }
            }
        }

        if(requestCode==Constants.responsibleChangeActivityKey){
            if(resultCode==RESULT_OK){
                if(data!=null){

                    updateResponsible((ResponsibleResponseData)data.getExtras().getSerializable(Constants.responsibleChange));
                }
            }
        }

        if(requestCode==Constants.employeeChangeActivityKey){
            if(resultCode==RESULT_OK){
                if(data!=null){
                    updateEmployee((EmployeeResponseData)data.getExtras().getSerializable(Constants.employeeChange));
                }
            }
        }
    }

    private void updateStatus(int statusId){
        StatusUpdateAsk ask=new StatusUpdateAsk();
        ask.act=Constants.setUpdateRequest;
        ask.req=new RequestionDetailParams();
        ask.req.requestId=requestId;
        ask.data=new StatusUpdateData();
        ask.data.statusId=statusId;
        Call<RequestUpdate> updateCall=serverAPI.statusUpdateRequest(
                Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplication())),
                ask);
        updateCall.enqueue(new Callback<RequestUpdate>() {
            @Override
            public void onResponse(Call<RequestUpdate> call, Response<RequestUpdate> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            doRequest(requestId);
            }

            @Override
            public void onFailure(Call<RequestUpdate> call, Throwable t) {

            }
        });
    }

    private void updateStatusWithReason(int statusId, int reasonId) {
        StatusReasonUpdateAsk ask=new StatusReasonUpdateAsk();
        ask.act=Constants.setUpdateRequest;
        ask.req=new RequestionDetailParams();
        ask.req.requestId=requestId;
        ask.data=new StatusReasonUpdateData();
        ask.data.statusId =statusId;
        ask.data.reasonId=reasonId;
        Call<RequestUpdate> updateCall=serverAPI.statusReasonUpdateRequest(
                Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplication())),
                 ask);
        updateCall.enqueue(new Callback<RequestUpdate>() {
            @Override
            public void onResponse(Call<RequestUpdate> call, Response<RequestUpdate> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            doRequest(requestId);
            }

            @Override
            public void onFailure(Call<RequestUpdate> call, Throwable t) {

            }
        });
    }

    private void updateResponsible(ResponsibleResponseData data){
        ResponsibleUpdateAsk ask=new ResponsibleUpdateAsk();
        ask.act=Constants.setUpdateRequest;
        ask.req=new RequestionDetailParams();
        ask.req.requestId=requestId;
        ask.data=new ResponsibleUpdateData();
        ask.data.respId=data.respId;
        Call<RequestUpdate> updateCall=serverAPI.responsibleUpdateRequest(Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplication())),
                ask);
        updateCall.enqueue(new Callback<RequestUpdate>() {
            @Override
            public void onResponse(Call<RequestUpdate> call, Response<RequestUpdate> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            doRequest(requestId);
            }

            @Override
            public void onFailure(Call<RequestUpdate> call, Throwable t) {

            }
        });
    }

    private void updateEmployee(EmployeeResponseData data){
        EmployeeUpdateAsk ask=new EmployeeUpdateAsk();
        ask.act=Constants.setUpdateRequest;
        ask.req=new RequestionDetailParams();
        ask.req.requestId=requestId;
        ask.data=new EmployeeUpdateData();
        ask.data.empId=data.empId;
        Call<RequestUpdate> updateCall=serverAPI.employeeUpdateRequest(Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplication())),
                ask);
        updateCall.enqueue(new Callback<RequestUpdate>() {
            @Override
            public void onResponse(Call<RequestUpdate> call, Response<RequestUpdate> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token)
                            doRequest(requestId);
            }

            @Override
            public void onFailure(Call<RequestUpdate> call, Throwable t) {

            }
        });
    }

    private void updateDeadline(String deadlineAt){
        DeadlineUpdateAsk ask=new DeadlineUpdateAsk();
        ask.act=Constants.setUpdateRequest;
        ask.req=new RequestionDetailParams();
        ask.req.requestId=requestId;
        ask.data=new DeadlineUpdateData();
        ask.data.deadlineAt=deadlineAt;
        Call<RequestUpdate> updateCall=serverAPI.deadlineUpdateRequest(Functions.encodeToBase64(PreferencesBuffer.getToken(getActivity().getApplication())),
                ask);
        updateCall.enqueue(new Callback<RequestUpdate>() {
            @Override
            public void onResponse(Call<RequestUpdate> call, Response<RequestUpdate> response) {
                if(response.isSuccessful())
                    if(response.body().ok)
                        if(response.body().token) {
                            doRequest(requestId);
                        }
            }

            @Override
            public void onFailure(Call<RequestUpdate> call, Throwable t) {

            }
        });
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

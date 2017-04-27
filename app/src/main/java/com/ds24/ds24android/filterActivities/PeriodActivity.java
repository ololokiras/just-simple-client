package com.ds24.ds24android.filterActivities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PeriodActivity extends AppCompatActivity implements View.OnClickListener{

    TextView startDate;
    TextView endDate;
    Button acceptButton;
    int from_year, from_month, from_day,to_year, to_month, to_day;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    boolean startDateSelected=false, endDateSelected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);

        initUI();
    }

    private void initUI() {
        acceptButton=(Button)findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(v->accept());

        startDate=(TextView)findViewById(R.id.start_text);
        endDate =(TextView) findViewById(R.id.end_text);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        Calendar beforeMonth=newCalendar;
        beforeMonth.add(Calendar.MONTH,-1);
        fromDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            startDate.setText(dateFormatter.format(newDate.getTime()));
            startDateSelected=true;
        },newCalendar.get(Calendar.YEAR), newCalendar.get(beforeMonth.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            endDate.setText(dateFormatter.format(newDate.getTime()));
            endDateSelected=true;
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if(v == startDate) {
            fromDatePickerDialog.show();
        } else if(v == endDate) {
            toDatePickerDialog.show();
        }
    }

    private void accept(){
        boolean canFinish=false;
        if(startDateSelected){
            DS24Application.getFilterInstance().startDate=startDate.getText().toString();
            canFinish=true;
        }
        if(endDateSelected){
            DS24Application.getFilterInstance().endDate=endDate.getText().toString();
            canFinish=true;
        }
        if(canFinish){
            setResult(RESULT_OK);
            finish();
        }
    }

}

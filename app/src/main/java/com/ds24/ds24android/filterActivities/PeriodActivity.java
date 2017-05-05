package com.ds24.ds24android.filterActivities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ds24.ds24android.DS24Application;
import com.ds24.ds24android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PeriodActivity extends AppCompatActivity implements View.OnClickListener{

    TextView startDateText;
    TextView endDateText;
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

        startDateText =(TextView)findViewById(R.id.start_text);
        endDateText =(TextView) findViewById(R.id.end_text);
        startDateText.setOnClickListener(this);
        endDateText.setOnClickListener(this);
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        Calendar startDate=newCalendar;
        startDate.add(Calendar.MONTH,-1);
        if(!TextUtils.isEmpty(DS24Application.getFilterInstance().startDate)){
            try {
                startDate.setTime(dateFormatter.parse(DS24Application.getFilterInstance().startDate));
                startDateText.setText(DS24Application.getFilterInstance().startDate);
            } catch (ParseException e) {
            }
        }
        fromDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            this.startDateText.setText(dateFormatter.format(newDate.getTime()));
            startDateSelected=true;
        },newCalendar.get(startDate.YEAR), newCalendar.get(startDate.MONTH), newCalendar.get(startDate.DAY_OF_MONTH));


        Calendar endDate=Calendar.getInstance();
        if(!TextUtils.isEmpty(DS24Application.getFilterInstance().endDate)){
            try {
                endDate.setTime(dateFormatter.parse(DS24Application.getFilterInstance().endDate));
                endDateText.setText(DS24Application.getFilterInstance().endDate);
            } catch (ParseException e) {

            }
        }
        toDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = endDate;
            newDate.set(year, monthOfYear, dayOfMonth);
            endDateText.setText(dateFormatter.format(newDate.getTime()));
            endDateSelected=true;
        },newCalendar.get(endDate.YEAR), newCalendar.get(endDate.MONTH), newCalendar.get(endDate.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if(v == startDateText) {
            fromDatePickerDialog.show();
        } else if(v == endDateText) {
            toDatePickerDialog.show();
        }
    }

    private void accept(){
        boolean canFinish=false;
        if(startDateSelected){
            DS24Application.getFilterInstance().startDate= startDateText.getText().toString();
            canFinish=true;
        }
        if(endDateSelected){
            DS24Application.getFilterInstance().endDate= endDateText.getText().toString();
            canFinish=true;
        }
        if(canFinish){
            setResult(RESULT_OK);
            finish();
        }
    }

}

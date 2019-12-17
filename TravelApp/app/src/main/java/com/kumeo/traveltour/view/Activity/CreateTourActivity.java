package com.kumeo.traveltour.view.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.kumeo.traveltour.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CreateTourActivity extends AppCompatActivity {

    public EditText etTourName;
    public EditText etStartDate;
    public EditText etEndDate;
    public EditText etSourceLat;
    public EditText etSourceLong;
    public EditText etDesLong;
    public EditText etDesLat;
    public EditText etAdults;
    public EditText etChilds;
    public EditText etMinCost;
    public EditText etMaxCost;
    public EditText etAvatar;

    public Button btnCreate;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_create_tour);

        initialize();

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRequiredFeild())
                {
                    SplashActivity.appPreference.showToast("Succeess");
                }
            }
        });
        chooseDate(etStartDate);
        chooseDate(etEndDate);



    }











    private void chooseDate(EditText etStartDate)
    {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar, etStartDate);

            }
        };
        etStartDate.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               new DatePickerDialog(CreateTourActivity.this, date, myCalendar
                                                       .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                       myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                           }
                                       }
        );
    }
    private void updateLabel(Calendar myCalendar, EditText etStartDate) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etStartDate.setText(sdf.format(myCalendar.getTime()));
    }
    public void initialize()
    {
        etTourName=findViewById(R.id.etTourName);
        etStartDate=findViewById(R.id.etStartDate);
        etEndDate=findViewById(R.id.etEndDate);
        etSourceLong=findViewById(R.id.etSourceLong);
        etSourceLat=findViewById(R.id.etSourceLat);
        etDesLat=findViewById(R.id.etDesLat);
        etDesLong=findViewById(R.id.etDesLong);
        etAdults=findViewById(R.id.etAdults);
        etChilds=findViewById(R.id.etChilds);
        etMinCost=findViewById(R.id.etMinCost);
        etMaxCost=findViewById(R.id.etMaxCost);
        etAvatar=findViewById(R.id.etAvatar);
    }
    public boolean checkRequiredFeild() {
        if (TextUtils.isEmpty(etTourName.getText())) {
            etTourName.setError("Tour  Name is required");
            return false;
        }
        if (TextUtils.isEmpty(etStartDate.getText())) {
            etStartDate.setError("Start Date is required");
            return false;
        }
        if (TextUtils.isEmpty(etEndDate.getText())) {
            etEndDate.setError("End Date is required");
            return false;
        }
        if (TextUtils.isEmpty(etSourceLat.getText())) {
            etSourceLat.setError("Source Latitude is required");
            return false;
        }
        if (TextUtils.isEmpty(etSourceLong.getText())) {
            etSourceLong.setError("Source Longitude is required");
            return false;
        }
        if (TextUtils.isEmpty(etDesLat.getText())) {
            etDesLat.setError("Destination Latitude is required");
            return false;
        }
        if (TextUtils.isEmpty(etDesLong.getText())) {
            etDesLong.setError("Destination Longitude is required");
            return false;
        }

        String startDate = etStartDate.getText().toString();
        String endDate=etEndDate.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strStartDate = null;
        Date strEndDate=null;

        try{
            strStartDate = sdf.parse(startDate);
            strEndDate=sdf.parse(endDate);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        if (strEndDate.getTime() < strStartDate.getTime()) {
            etStartDate.setError("End date must be after start date");
            etEndDate.setError("End date must be after start date");
            return false;
        }

        return true;
    }
}

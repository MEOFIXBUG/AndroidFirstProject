package com.kumeo.traveltour.view.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kumeo.traveltour.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddStopPointActivity extends AppCompatActivity {

    private EditText etSPName;
    private  EditText etSPAddress;
    private  EditText etMinCost;
    private  EditText etMaxCost;
    private  EditText etTimeArrive;
    private  EditText etDateArrive;
    private  EditText etDateLeave;

    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop_point);

        configPopUpWindow();
        initialize();
        chooseDate(etDateArrive);
        chooseDate(etDateLeave);

        btnSave=findViewById(R.id.btnSAVE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRequiredField();
            }
        });


    }

    private void configPopUpWindow()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double pop_up_width =dm.widthPixels*0.875;
        double pop_up_height=dm.heightPixels*0.875;

        getWindow().setLayout((int)pop_up_width, (int)pop_up_height);
    }

    public void initialize()
    {
        etSPName=findViewById(R.id.etSPName);
        etSPAddress=findViewById(R.id.etSPAddress);
        etMinCost=findViewById(R.id.etMinCost);
        etMaxCost=findViewById(R.id.etMaxCost);
        etTimeArrive=findViewById(R.id.etTimeArrive);
        etDateArrive=findViewById(R.id.etDateArrive);
        etDateLeave=findViewById(R.id.etDateLeave);
    }
    public boolean checkRequiredField() {
        if (TextUtils.isEmpty(etSPName.getText())) {
            etSPName.setError("Stop point name is required");
            return false;
        }
        if (TextUtils.isEmpty(etTimeArrive.getText())) {
            etTimeArrive.setError("Time is required");
            return false;
        }
        if (TextUtils.isEmpty(etDateArrive.getText())) {
            etDateArrive.setError("End Date is required");
            return false;
        }
        if (TextUtils.isEmpty(etDateLeave.getText())) {
            etDateLeave.setError("End Date is required");
            return false;
        }
        /*if (TextUtils.isEmpty(etSourceLat.getText())) {
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
        }*/

        String startDate = etDateArrive.getText().toString();
        String endDate=etDateLeave.getText().toString();
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
            etDateArrive.setError("Leave date must be after arrive date");
            etDateLeave.setError("Leave date must be after arrive date");
            return false;
        }

        return true;
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
                                               new DatePickerDialog(AddStopPointActivity.this, date, myCalendar
                                                       .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                       myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                           }
                                       }
        );
    }
    private void updateLabel(Calendar myCalendar, EditText etStartDate) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etStartDate.setText(sdf.format(myCalendar.getTime()));
    }
}

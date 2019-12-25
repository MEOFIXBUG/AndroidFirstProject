package com.kumeo.traveltour.view.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

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
    private  EditText etTimeLeave;
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
        timePicker(etTimeArrive);
        timePicker(etTimeLeave);

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

        double pop_up_width =dm.widthPixels*0.85;
        double pop_up_height=dm.heightPixels*0.8;

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
        etTimeLeave=findViewById(R.id.etTimeLeave);
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
    private void timePicker(EditText eReminderTime)
    {
        eReminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStopPointActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eReminderTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }
}

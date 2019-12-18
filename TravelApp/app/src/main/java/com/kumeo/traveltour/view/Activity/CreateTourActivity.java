package com.kumeo.traveltour.view.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Space;

import androidx.appcompat.app.AppCompatActivity;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.extras.converter;
import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.response.LoginResponse;
import com.kumeo.traveltour.retrofit.Service.Tour.TourAPI;
import com.kumeo.traveltour.retrofit.Service.TourInterface;
import com.kumeo.traveltour.retrofit.retrofitRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateTourActivity extends AppCompatActivity{

    public EditText etTourName;
    public EditText etStartDate;
    public EditText etEndDate;
   /* public EditText etSourceLat;
    public EditText etSourceLong;
    public EditText etDesLong;
    public EditText etDesLat;*/
    public EditText etAdults;
    public EditText etChilds;
    public EditText etMinCost;
    public EditText etMaxCost;
    public EditText etAvatar;
    public boolean isPrivate=false;

    public Button btnCreate;

    private TourAPI tourapi;
    private Tour reqTour;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_create_tour);

        initialize();

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRequiredField())
                {
                    try {
                        reqTour= makeTourRequest();
                        createTour(reqTour);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                }
            }
        );
        chooseDate(etStartDate);
        chooseDate(etEndDate);

    }





    private void createTour(Tour reqTour)
    {

        tourapi= retrofitRequest.getRetrofitInstance().create(TourAPI.class);
        Call<Tour> callTour= tourapi.createTour(reqTour);
        callTour.enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.isSuccessful()){
                    SplashActivity.appPreference.showToast("Create Successful");
                    //SplashActivity.appPreference.showToast(response.body().getName());
                    //SplashActivity.appPreference.showToast(response.body().getStartDate()+"");
                    SplashActivity.appPreference.showToast(response.body().getHostId()+" host id");
                    SplashActivity.appPreference.showToast(response.body().getID()+"id");
                    openAddStopPointActivity();
                } else {
                    SplashActivity.appPreference.showToast("Create tour failed in some fields");
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {
                SplashActivity.appPreference.showToast("Create Failed");
            }
        });
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
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etStartDate.setText(sdf.format(myCalendar.getTime()));
    }
    public void initialize()
    {
        etTourName=findViewById(R.id.etTourName);
        etStartDate=findViewById(R.id.etStartDate);
        etEndDate=findViewById(R.id.etEndDate);
/*        etSourceLong=findViewById(R.id.etSourceLong);
        etSourceLat=findViewById(R.id.etSourceLat);
        etDesLat=findViewById(R.id.etDesLat);
        etDesLong=findViewById(R.id.etDesLong);*/
        etAdults=findViewById(R.id.etAdults);
        etChilds=findViewById(R.id.etChilds);
        etMinCost=findViewById(R.id.etMinCost);
        etMaxCost=findViewById(R.id.etMaxCost);
        etAvatar=findViewById(R.id.etAvatar);
    }
    public boolean checkRequiredField() {
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

    private Tour makeTourRequest() throws ParseException {
        Tour res = new Tour();
        res.setName(etTourName.getText().toString());
        res.setStartDate(converter.milisecondDate(etStartDate.getText().toString()));
        res.setEndDate(converter.milisecondDate(etEndDate.getText().toString()));
        /*res.setSourceLat((Integer.parseInt(etSourceLat.getText().toString())));
        res.setSourceLong((Integer.parseInt(etSourceLong.getText().toString())));
        res.setDesLat(Integer.parseInt(etDesLat.getText().toString()));
        res.setDesLong(Integer.parseInt(etDesLong.getText().toString()));*/

        if (!TextUtils.isEmpty(etAdults.getText()))res.setAdults(Integer.parseInt(etAdults.getText().toString()));
        if( !TextUtils.isEmpty(etChilds.getText()))res.setChilds(Integer.parseInt(etChilds.getText().toString()));
        if(!TextUtils.isEmpty(etAvatar.getText()))res.setAvatar(etAvatar.getText().toString());
        /*if (!TextUtils.isEmpty(etSourceLong.getText()))res.setSourceLong(Integer.parseInt(etSourceLong.getText().toString()));
        if (!TextUtils.isEmpty(etSourceLat.getText()))res.setSourceLat(Integer.parseInt(etSourceLat.getText().toString()));
        if (!TextUtils.isEmpty(etDesLong.getText()))res.setDesLong(Integer.parseInt(etDesLong.getText().toString()));
        if (!TextUtils.isEmpty(etDesLat.getText()))res.setDesLat(Integer.parseInt(etDesLat.getText().toString()));*/
        if(!TextUtils.isEmpty(etMinCost.getText()))res.setMinCost(Integer.parseInt(etMinCost.getText().toString()));
        if(!TextUtils.isEmpty(etMaxCost.getText()))res.setMaxCost(Integer.parseInt(etMaxCost.getText().toString()));
        res.setIsPrivate(isPrivate);

        return res;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdoBtnIsPrivate:
                if (checked)
                    isPrivate=true;
                break;
        }
    }


    public void openAddStopPointActivity() {
        Intent intent=new Intent(CreateTourActivity.this, AddStopPointActivity.class);
        startActivity(intent);
    }
}

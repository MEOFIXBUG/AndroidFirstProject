package com.ygaps.travelapp.view.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.extras.ReadExcel;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.extras.converter;
import com.ygaps.travelapp.model.Province;
import com.ygaps.travelapp.model.ServiceType;
import com.ygaps.travelapp.model.StopPoint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class AddStopPointActivity extends AppCompatActivity {

    private EditText etSPName;
    private  EditText etSPAddress;
    private  EditText etMinCost;
    private  EditText etMaxCost;
    private  EditText etTimeArrive;
    private  EditText etTimeLeave;
    private  EditText etDateArrive;
    private  EditText etDateLeave;
    private Spinner spinnerService;
    private Spinner spinnerProvince;
    private int serviceType;
    private long province;
    private double longitudeFromMap;//nhan tu man hinh tour map
    private double latitudeFromMap;

    private Button btnSave;
    private List<String>serviceName;
    private List<String>provinces;
    private List<Province>listProvinces;
    private List<ServiceType>listServiceType;
    ArrayList<StopPoint> listStopPoint=new ArrayList<StopPoint>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop_point);

        configPopUpWindow();

        //readExcel file
        serviceName= ReadExcel.getListServiceName(AddStopPointActivity.this);
        provinces=ReadExcel.getListProvincesName(AddStopPointActivity.this);
        listProvinces=ReadExcel.readProvinceExcelFile(AddStopPointActivity.this);
        listServiceType=ReadExcel.readServiceTypeExcelFile(AddStopPointActivity.this);

        initialize();
        chooseDate(etDateArrive);
        chooseDate(etDateLeave);
        timePicker(etTimeArrive);
        timePicker(etTimeLeave);
        setAdapterSpinner(spinnerProvince, provinces);
        setAdapterSpinner(spinnerService, serviceName);

        listStopPoint= SharePreferenceListStopPoint.loadData(this);//doc tu file len

        recieveDataFromMapAvtivity();

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SplashActivity.appPreference.showToast(listProvinces.get(position).getCode());
                province=listProvinces.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SplashActivity.appPreference.showToast(listServiceType.get(position).getId()+"");
                serviceType=listServiceType.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave=findViewById(R.id.btnSAVE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRequiredField();

                try {
                    openTourMapActivity();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
        spinnerService=findViewById(R.id.service_spinner);
        spinnerProvince=findViewById(R.id.province_spinner);


    }
    public boolean checkRequiredField() {
        if (TextUtils.isEmpty(etSPName.getText())) {
            etSPName.setError("Stop point name is required");
            return false;
        }
        if (TextUtils.isEmpty(etTimeArrive.getText())) {
            etTimeArrive.setError("Time Arrive is required");
            return false;
        }
        if (TextUtils.isEmpty(etTimeLeave.getText())) {
            etTimeArrive.setError("Time Leave is required");
            return false;
        }
        if (TextUtils.isEmpty(etDateArrive.getText())) {
            etDateArrive.setError("End Date is required");
            return false;
        }
        if (TextUtils.isEmpty(etDateLeave.getText())) {
            etDateLeave.setError("Leave Date is required");
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
    public void setAdapterSpinner(Spinner spinnerTP, List<String>list)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinnerTP.setAdapter(adapter);
    }
    public void openTourMapActivity() throws ParseException {
        Intent intent=new Intent(AddStopPointActivity.this, TourMapsActivity.class);

        StopPoint newStopPoint =makeStopPointRequest();
        intent.putExtra("newStopPoint",newStopPoint);
        listStopPoint.add(newStopPoint);
        SharePreferenceListStopPoint.saveData(listStopPoint, this);

        startActivity(intent);
    }
    private StopPoint makeStopPointRequest() throws ParseException {
        StopPoint res = new StopPoint();

        //bat buoc
        res.setName(etSPName.getText().toString());
        res.setArrivalAt(converter.milisecondDate(etDateArrive.getText().toString()));
        res.setLeaveAt(converter.milisecondDate(etDateLeave.getText().toString()));
        res.setServiceTypeId(serviceType);
        //long, lat
        res.setLong(longitudeFromMap);
        res.setLat(latitudeFromMap);

        //optional
        res.setProvinceId(province);
        if (!TextUtils.isEmpty(etSPAddress.getText()))res.setAddress(etSPAddress.getText().toString());
        if(!TextUtils.isEmpty(etMinCost.getText()))res.setMinCost(parseInt(etMinCost.getText().toString()));
        if(!TextUtils.isEmpty(etMaxCost.getText()))res.setMaxCost(parseInt(etMaxCost.getText().toString()));

        return res;
    }

    private void recieveDataFromMapAvtivity()
    {
        if(getIntent()!=null) {
            Intent intent = getIntent();

            longitudeFromMap=intent.getDoubleExtra("longitude", 0);//danh dau day la su kien edit
            latitudeFromMap=intent.getDoubleExtra("latitude", 0);

            //SplashActivity.appPreference.showToast(longitudeFromMap+"--"+latitudeFromMap);
        }
    }

}

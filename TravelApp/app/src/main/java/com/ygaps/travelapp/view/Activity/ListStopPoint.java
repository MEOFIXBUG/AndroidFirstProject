package com.ygaps.travelapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.StopListAdapter;
import com.ygaps.travelapp.extras.OpenActivity;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.model.MessageResponse;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.model.StopPointsOfTour;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.retrofit.Service.Tour.TourAPI;
import com.ygaps.travelapp.retrofit.retrofitRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStopPoint extends AppCompatActivity {
    private static final String TAG = ListStopPoint.class.getSimpleName();
    ArrayList<StopPoint> listStopPoint=new ArrayList<StopPoint>();
    private StopListAdapter adapter;
    private RecyclerView my_recycler_view;
    private LinearLayoutManager layoutManager;
    private long tourId;
    private Button btnAdd;
    private TourAPI tourapi;

    private StopPointsOfTour stopPointsReq;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_stoppoint);

        stopPointsReq=new StopPointsOfTour();
        listStopPoint=SharePreferenceListStopPoint.loadData(ListStopPoint.this);
        //plashActivity.appPreference.showToast("co "+ listStopPoint.size()+" stop point.");

        my_recycler_view=findViewById(R.id.rcvStopPoint);
        configAdapter();

        tourId=SharePreferenceListStopPoint.loadTourId(this);

        btnAdd=findViewById(R.id.btnAddNewStopPoint);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPointsReq.setTourId(tourId);
                stopPointsReq.setStopPoints(listStopPoint);
                setStopPoint(stopPointsReq);
            }
        });
       // SplashActivity.appPreference.showToast(tourId+" tourId");
    }


    private void setStopPoint(StopPointsOfTour reqStop)
    {

        tourapi= retrofitRequest.getRetrofitInstance().create(TourAPI.class);
        Call<MessageResponse> callTour= tourapi.createListStopPoint(reqStop);
        callTour.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.code()==200) {
                    SplashActivity.appPreference.showToast(response.body().getMessage());
                    listStopPoint.clear();
                    SharePreferenceListStopPoint.saveData(listStopPoint, ListStopPoint.this);
                    //openMapActivity();
                    OpenActivity.openHomeActivity(ListStopPoint.this);
                }
                else
                {
                    SplashActivity.appPreference.showToast("Add stop point failed in some fields..");
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                SplashActivity.appPreference.showToast("Add stop point failed. Try again.");
            }
        });

    }

    private void openMapActivity() {
        Intent intent=new Intent(ListStopPoint.this, TourMapsActivity.class);
        startActivity(intent);
    }

    public void recieveFromMapActivity()
    {
        if(getIntent()!=null) {
            Intent intent = getIntent();
            tourId=intent.getLongExtra("tourId", 0);
        }
    }
    public void configAdapter()
    {
        layoutManager = new LinearLayoutManager(ListStopPoint.this);
        my_recycler_view.setLayoutManager(layoutManager);
        my_recycler_view.setHasFixedSize(true);
        adapter = new StopListAdapter(ListStopPoint.this,listStopPoint);

        adapter.setOnItemClickListener((stop) ->
        {
            //SplashActivity.appPreference.showToast("hehe!!");
        });
        my_recycler_view.setAdapter(adapter);
    }
}

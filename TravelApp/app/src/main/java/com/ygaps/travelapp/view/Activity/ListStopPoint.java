package com.ygaps.travelapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.StopListAdapter;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.model.StopPoint;

import java.util.ArrayList;

public class ListStopPoint extends AppCompatActivity {

    ArrayList<StopPoint> listStopPoint=new ArrayList<StopPoint>();
    private StopListAdapter adapter;
    private RecyclerView my_recycler_view;
    private LinearLayoutManager layoutManager;
    private long tourId;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_stoppoint);

        listStopPoint=SharePreferenceListStopPoint.loadData(ListStopPoint.this);
        //plashActivity.appPreference.showToast("co "+ listStopPoint.size()+" stop point.");

        my_recycler_view=findViewById(R.id.rcvStopPoint);
        configAdapter();

        recieveFromMapActivity();
        SplashActivity.appPreference.showToast(tourId+" tourId");
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
            SplashActivity.appPreference.showToast("hehe!!");
        });
        my_recycler_view.setAdapter(adapter);
    }
}

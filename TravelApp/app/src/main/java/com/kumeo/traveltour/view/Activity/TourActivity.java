package com.kumeo.traveltour.view.Activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.adapter.TourAdapter;
import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.response.TourResponse;
import com.kumeo.traveltour.viewmodel.TourViewModel;

import java.util.ArrayList;
import java.util.List;

public class TourActivity extends AppCompatActivity {

    private static final String TAG = TourActivity.class.getSimpleName();
    private ArrayList<Tour> tourArrayList = new ArrayList<>();
    TourViewModel tourViewModel;
    ListView listView;
    RadioGroup whichRadio;
    TourAdapter adapter;
    private ProgressBar progress_circular_tour;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress_circular_tour = (ProgressBar) findViewById(R.id.progress_circular_tour);
        listView=(ListView)findViewById(R.id.tour_list);
        adapter= new TourAdapter(this,tourArrayList);
        listView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        });
        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        getTour();
    }

    private void getTour() {
        LiveData<TourResponse> TourList= tourViewModel.getTourResponseLiveData(3,1);
        TourList.observe(this,tourResponse->{
            if (tourResponse != null) {
                progress_circular_tour.setVisibility(View.GONE);
                List<Tour> tours = tourResponse.getTours();
                Log.d(TAG, "data:: " + tours.get(0).getName());
               tourArrayList.addAll(tours);
                adapter.notifyDataSetChanged();
            }
        });
    }

}

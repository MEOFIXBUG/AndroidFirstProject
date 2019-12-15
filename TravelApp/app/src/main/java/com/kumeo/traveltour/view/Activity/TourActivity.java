package com.kumeo.traveltour.view.Activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView my_recycler_view;
    private TourAdapter adapter;
    private ProgressBar progress_circular_tour;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialization();

        getTour();
    }

    private void initialization() {
        progress_circular_tour = (ProgressBar) findViewById(R.id.progress_circular_tour);
        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        //layoutManager set chieu hien thi list VERTICAL/HORIZONTAL, va xac dinh context cho recycle view do
        layoutManager = new LinearLayoutManager(TourActivity.this);
        my_recycler_view.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        my_recycler_view.setHasFixedSize(true);//toi uu hien thi

        // adapter
        adapter = new TourAdapter(TourActivity.this, tourArrayList);
        my_recycler_view.setAdapter(adapter);

        // View Model
        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        tourViewModel.init(49,1);

    }
    private void getTour() {
        LiveData<TourResponse> TourList= tourViewModel.getTourResponseLiveData();
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

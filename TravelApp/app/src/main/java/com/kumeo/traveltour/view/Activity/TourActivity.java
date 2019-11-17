package com.kumeo.traveltour.view.Activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;

import com.kumeo.traveltour.R;
import com.kumeo.traveltour.model.Tour;
import com.kumeo.traveltour.response.TourResponse;
import com.kumeo.traveltour.viewmodel.TourViewModel;

import java.util.ArrayList;

public class TourActivity extends AppCompatActivity {

    private static final String TAG = TourActivity.class.getSimpleName();
    private ArrayList<Tour> tourArrayList = new ArrayList<>();
    TourViewModel tourViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
        //getTour();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        });

    }

    private void getTour() {
       String auth= SplashActivity.appPreference.getToken();
        LiveData<TourResponse> a= tourViewModel.getTourResponseLiveData(auth,1,1);
        Log.d(TAG, "articles title pos 0:: " + a.getValue().getTotal());
    }

}

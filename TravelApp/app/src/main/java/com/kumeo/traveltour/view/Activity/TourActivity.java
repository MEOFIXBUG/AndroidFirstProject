package com.kumeo.traveltour.view.Activity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;


import com.kumeo.traveltour.R;

import com.kumeo.traveltour.view.Fragment.MyTripFragment;
import com.kumeo.traveltour.view.Fragment.TravelFragment;


public class TourActivity extends AppCompatActivity  {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new TravelFragment());

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    fragment = new TravelFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_mytrips:
                    toolbar.setTitle("My Trips");
                    fragment = new MyTripFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_friend:
                    toolbar.setTitle("Friend");
                    fragment = new TravelFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    fragment = new TravelFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

}

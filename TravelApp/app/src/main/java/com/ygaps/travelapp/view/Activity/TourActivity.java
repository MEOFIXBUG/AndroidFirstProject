package com.ygaps.travelapp.view.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import com.ygaps.travelapp.R;

import com.ygaps.travelapp.retrofit.Service.TourInterface;
import com.ygaps.travelapp.view.Fragment.MyTripFragment;
import com.ygaps.travelapp.view.Fragment.ProfileFragment;
import com.ygaps.travelapp.view.Fragment.TravelFragment;

import static android.view.View.VISIBLE;


public class TourActivity extends AppCompatActivity implements TourInterface {
    private Toolbar toolbar;
    public static SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        searchView= (SearchView) findViewById(R.id.search);
        toolbar.setTitle("Home");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new TravelFragment());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        //transaction.addToBackStack(null);
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
                    searchView.setVisibility(VISIBLE);
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
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    public void openCreateTourActivity() {
        Intent intent=new Intent(TourActivity.this, CreateTourActivity.class);
        startActivity(intent);
    }
}

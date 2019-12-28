package com.ygaps.travelapp.view.Activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.view.Fragment.GeneralSPFragment;
import com.ygaps.travelapp.view.Fragment.InviteFragment;
import com.ygaps.travelapp.view.Fragment.ReviewSPFragment;
import com.ygaps.travelapp.view.Fragment.StopPointFragment;

public class DetailStopPoint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stop_point);

        configPopUpWindow();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_SP_details);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new GeneralSPFragment());
    }
    private void configPopUpWindow()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double pop_up_width =dm.widthPixels*0.85;
        double pop_up_height=dm.heightPixels*0.9;

        getWindow().setLayout((int)pop_up_width, (int)pop_up_height);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_SP_general:
                    //toolbar.setTitle("[Tour Name]");
                    fragment = new GeneralSPFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_SP_review:
                    //toolbar.setTitle("[Tour Name]");
                    fragment = new ReviewSPFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}

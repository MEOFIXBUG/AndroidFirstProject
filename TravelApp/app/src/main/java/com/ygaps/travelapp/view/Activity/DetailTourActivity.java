package com.ygaps.travelapp.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.view.Fragment.InviteFragment;
import com.ygaps.travelapp.view.Fragment.StopPointFragment;
import com.ygaps.travelapp.viewmodel.TourViewModel;


public class DetailTourActivity extends AppCompatActivity {

    private static final String TAG = DetailTourActivity.class.getSimpleName();
    public static  TourViewModel tourViewModel;
    public static long tourID;
//    ImageView placeImageView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);
        tourViewModel= ViewModelProviders.of(this).get(TourViewModel.class);
        Intent intent= getIntent();
        tourID=intent.getLongExtra("tourID",1);
        toolbar = findViewById(R.id.toolbar);
        String tourName=intent.getStringExtra("tourName");
        toolbar.setTitle(tourName);
        //Intent intent= getIntent();
        //int tourID=intent.getIntExtra("tourId",0);
        //intialization();
       // getTourInfo(tourID);
        Log.d(TAG,"xxx :" +tourID);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new StopPointFragment());

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
                    toolbar.setTitle("a");
                    fragment = new StopPointFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_mytrips:
                    toolbar.setTitle("b");
                    fragment = new InviteFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_friend:
                    toolbar.setTitle("3");
                    fragment = new StopPointFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("4");
                    fragment = new InviteFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
//    private void intialization(){
//        //placeImageView= (ImageView)findViewById(R.id.place_image);
//        tourViewModel = ViewModelProviders.of(this).get(TourViewModel.class);
//    }
//    private void getTourInfo(int id){
//        LiveData<TourInfoResponse> data = tourViewModel.getTourInfo(id);
//        if(data!= null) {
//            data.observe(this,(TourInfoResponse)->{
//                Log.d(TAG, "123total result:: " + TourInfoResponse.getName());
//                if (TourInfoResponse.getAvatar() != null && !TourInfoResponse.getAvatar().isEmpty())
//                    Picasso.get().load(TourInfoResponse.getAvatar()).error(R.drawable.place)
//                            .placeholder(R.drawable.place).into(placeImageView);
//            });
//        }
//    }
}

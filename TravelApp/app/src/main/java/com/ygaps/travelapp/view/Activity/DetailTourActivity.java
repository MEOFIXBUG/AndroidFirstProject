package com.ygaps.travelapp.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.response.TourInfoResponse;
import com.ygaps.travelapp.view.Fragment.InviteFragment;
import com.ygaps.travelapp.view.Fragment.StopPointFragment;
import com.ygaps.travelapp.viewmodel.TourViewModel;


public class DetailTourActivity extends AppCompatActivity {

    private static final String TAG = DetailTourActivity.class.getSimpleName();
    public static  TourViewModel tourViewModel;
    public static long tourID;
    public static boolean Editable;
    public Button btnRemoveTour;
//    ImageView placeImageView;
    private Toolbar toolbar;
    public static TourInfoResponse TourInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);
<<<<<<< HEAD
        //configPopUpWindow();
=======
        //getActionBar().hide();
>>>>>>> 68f69301307d04a843f8987215a448945cbad56c
        tourViewModel= ViewModelProviders.of(this).get(TourViewModel.class);
        toolbar = findViewById(R.id.toolbar);

        //btnRemoveTour=findViewById(R.id.btnRemoveTour);

        Intent intent = getIntent();
        tourID=intent.getLongExtra("tourID",1);
        SplashActivity.appPreference.showToast(tourID+"");
        Editable=intent.getBooleanExtra("Editable",false);
        String tourName=intent.getStringExtra("tourName");
        toolbar.setTitle(tourName);
<<<<<<< HEAD

=======
        //Intent intent= getIntent();
        //int tourID=intent.getIntExtra("tourId",0);
        //intialization();
       // getTourInfo(tourID);
        Log.d(TAG,"xxx :" +tourID);
        Log.d(TAG,"EDIT :" +Editable);
>>>>>>> 68f69301307d04a843f8987215a448945cbad56c
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new StopPointFragment());

        /*btnRemoveTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }
    private void configPopUpWindow()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double pop_up_width =dm.widthPixels*0.85;
        double pop_up_height=dm.heightPixels*0.9;

        getWindow().setLayout((int)pop_up_width, (int)pop_up_height);
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
<<<<<<< HEAD
                    toolbar.setTitle("[Tour Name]");
=======
>>>>>>> 68f69301307d04a843f8987215a448945cbad56c
                    fragment = new StopPointFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_mytrips:
<<<<<<< HEAD
                    toolbar.setTitle("[Tour Name]");
=======
>>>>>>> 68f69301307d04a843f8987215a448945cbad56c
                    fragment = new InviteFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_friend:
//                    fragment = new StopPointFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:

//                    fragment = new InviteFragment();
//                    loadFragment(fragment);
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

package com.ygaps.travelapp.view.Activity;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ygaps.travelapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.view.Fragment.InviteFragment;
import com.ygaps.travelapp.view.Fragment.StopPointFragment;

import java.util.ArrayList;

public class TourMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton btnListSP;
    private double longitude;
    private double latitude;
    private long tourId;
    StopPoint newStopPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //button list stop point
        btnListSP=(FloatingActionButton)findViewById(R.id.btnList);
        btnListSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListStopPointInfo();
            }
        });

        tourId=SharePreferenceListStopPoint.loadTourId(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationSP);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        recieveFromSPActivity();
        //now getIntent() should always return the last received intent
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.763250, 106.682215);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        //mMap.setMyLocationEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                longitude=latLng.longitude;
                latitude=latLng.latitude;
                openAddStopPointActivity();
                /*MarkerOptions markerOptions=new MarkerOptions();

                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" - "+ latLng.longitude);
                longitude=latLng.longitude;
                latitude=latLng.latitude;


                //mMap.clear();// clear marker cu
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                openAddStopPointActivity();

                if(newStopPoint.getServiceTypeId()==1){
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant));
                }
                else if(newStopPoint.getServiceTypeId()==2)
                {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel));
                }
                else if(newStopPoint.getServiceTypeId()==3)
                {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.rest));
                }
                else if(newStopPoint.getServiceTypeId()==4)
                {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.orther));
                }

                mMap.addMarker(markerOptions);*/
            }
        });
    }

    public void makeMarker()
    {
        MarkerOptions markerOptions=new MarkerOptions();

        LatLng latLng=new LatLng(newStopPoint.getLat(), newStopPoint.getLong());
        markerOptions.position(latLng);

        //mMap.clear();// clear marker cu
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        //openAddStopPointActivity();
        int height = 90;
        int width = 90;
        BitmapDrawable bitmapdraw;

        if(newStopPoint.getServiceTypeId()==1){
            bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.restaurant);
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant));
        }
        else if(newStopPoint.getServiceTypeId()==2)
        {
            bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.hotel);
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel));
        }
        else if(newStopPoint.getServiceTypeId()==3)
        {
            bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.rest);
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.rest));
        }
        else
        {
            bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.orther);
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.orther));
        }
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        markerOptions.title(newStopPoint.getName());
        mMap.addMarker(markerOptions);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuSP_list_stop_point:
                    Intent intent = new Intent(TourMapsActivity.this, DetailTourActivity.class);

                    intent.putExtra("tourID",tourId);
                    intent.putExtra("Editable",true);
                    startActivity(intent);
                    return true;
                case R.id.menuSP_rating_stop_point:
                    return true;
                case R.id.menuSP_delete_stop_point:
                    return true;

            }
            return false;
        }
    };
    public void openAddStopPointActivity() {
        Intent intent=new Intent(TourMapsActivity.this, AddStopPointActivity.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        startActivity(intent);
    }

    public void openListStopPointInfo() {
        Intent intent=new Intent(TourMapsActivity.this, ListStopPoint.class);
        //intent.putExtra("tourId", tourId);
        startActivity(intent);
    }

    public void recieveFromSPActivity()
    {
        if(getIntent()!=null && getIntent().getSerializableExtra("newStopPoint")!=null) {
            newStopPoint = (StopPoint) getIntent().getSerializableExtra("newStopPoint");
            makeMarker();
        }
    }
    public void recieveFromCreateToutActivity()
    {
        if(getIntent()!=null) {
            Intent intent = getIntent();
            tourId=intent.getLongExtra("tourId", 0);
        }
    }
}

package com.ygaps.travelapp.view.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ygaps.travelapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ygaps.travelapp.extras.SharePreferenceListStopPoint;
import com.ygaps.travelapp.model.StopPoint;

import java.util.ArrayList;

public class TourMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton btnListSP;
    private double longitude;
    private double latitude;
    private long tourId;

    ArrayList<StopPoint> listStopPoint=new ArrayList<StopPoint>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        listStopPoint=SharePreferenceListStopPoint.loadData(TourMapsActivity.this);//doc tu file len
        recieveFromSPActivity();
        recieveFromCreateToutActivity();
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
                MarkerOptions markerOptions=new MarkerOptions();

                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" - "+ latLng.longitude);
                longitude=latLng.longitude;
                latitude=latLng.latitude;

                mMap.clear();// clear marker cu
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

                openAddStopPointActivity();

                mMap.addMarker(markerOptions);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }

    public void openAddStopPointActivity() {
        Intent intent=new Intent(TourMapsActivity.this, AddStopPointActivity.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        startActivity(intent);
    }

    public void openListStopPointInfo() {
        Intent intent=new Intent(TourMapsActivity.this, ListStopPoint.class);
        intent.putExtra("tourId", tourId);
        startActivity(intent);
    }

    public void recieveFromSPActivity()
    {
        if(getIntent()!=null && getIntent().getSerializableExtra("newStopPoint")!=null) {
            StopPoint newStopPoint = (StopPoint) getIntent().getSerializableExtra("newStopPoint");
            listStopPoint.add(newStopPoint);
            SharePreferenceListStopPoint.saveData(listStopPoint, TourMapsActivity.this);
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

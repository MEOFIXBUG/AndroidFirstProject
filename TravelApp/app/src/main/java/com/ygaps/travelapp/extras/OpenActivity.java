package com.ygaps.travelapp.extras;

import android.content.Context;
import android.content.Intent;

import com.ygaps.travelapp.view.Activity.CreateTourActivity;
import com.ygaps.travelapp.view.Activity.DetailStopPoint;
import com.ygaps.travelapp.view.Activity.DetailTourActivity;
import com.ygaps.travelapp.view.Activity.SplashActivity;
import com.ygaps.travelapp.view.Activity.TourActivity;
import com.ygaps.travelapp.view.Activity.TourMapsActivity;

public class OpenActivity {

    public static void openDetailActivity(Context context,  long tourId, String tourName,  boolean isEditable)
    {
        SplashActivity.appPreference.showToast("onclick " + tourId);
        Intent intent = new Intent(context, DetailTourActivity.class);
        intent.putExtra("tourID",tourId);
        intent.putExtra("tourName",tourName);
        intent.putExtra("Editable",isEditable);
        context.startActivity(intent);
    }
    public static void openMapActivity(Context context) {
        Intent intent=new Intent(context, TourMapsActivity.class);
        context.startActivity(intent);
    }
    public static void openHomeActivity(Context context) {
        Intent intent=new Intent(context, TourActivity.class);
        context.startActivity(intent);
    }

    public static void openStopPointDetail(Context context) {
        Intent intent=new Intent(context, DetailStopPoint.class);
        context.startActivity(intent);
    }

}

package com.ygaps.travelapp.extras;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ygaps.travelapp.model.StopPoint;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceListStopPoint {

    public static void saveData(ArrayList<StopPoint>listStopPoint, Context activity){
        SharedPreferences mySharePreferences=activity.getSharedPreferences("shared_Prefernces",MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharePreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listStopPoint);
        editor.putString("task_list", json);
        editor.apply();
    }

    public static void saveTourIdFromCreateTour(long tourId, Context activity){
        SharedPreferences mySharePreferences=activity.getSharedPreferences("shared_Prefernces",MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharePreferences.edit();
        editor.putLong("tourId", tourId);
        editor.apply();
    }

    public static long loadTourId(Context activity) {
        long tourId;
        SharedPreferences sharedPreferences = activity.getSharedPreferences("shared_Prefernces", MODE_PRIVATE);
        tourId = sharedPreferences.getLong("tourId", 0);

        return tourId;
    }

    public static ArrayList<StopPoint> loadData(Context activity) {
        ArrayList<StopPoint>listStopPoint;
        SharedPreferences sharedPreferences = activity.getSharedPreferences("shared_Prefernces", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Type type = new TypeToken<ArrayList<StopPoint>>() {}.getType();
        listStopPoint = gson.fromJson(json, type);

        if (listStopPoint== null) {
            listStopPoint = new ArrayList<>();
        }

        return listStopPoint;
    }

    public static void clear(Context activity)
    {
        SharedPreferences preferences = activity.getSharedPreferences("shared_Prefernces", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}

package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StopPointsOfTour {
    @SerializedName("tourId")
    private long tourId;
    @SerializedName("stopPoints")
    private ArrayList<StopPoint> stopPoints;
    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public ArrayList<StopPoint> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(ArrayList<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }


}

package com.ygaps.travelapp.response;

import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.model.StopPoint;

import java.util.List;

public class StopPointList {
    @SerializedName("stopPoints")
    private List<StopPoint> stopPoints;

    public List<StopPoint> getStopPoints ()
    {
        return stopPoints;
    }

    public void setStopPoints (List<StopPoint> stopPoints)
    {
        this.stopPoints = stopPoints;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [stopPoints = "+stopPoints+"]";
    }
}

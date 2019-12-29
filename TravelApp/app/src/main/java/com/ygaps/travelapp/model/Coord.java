package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Coord {
    private double lat;
    @SerializedName("long")
    private double lng;

    public double getLat ()
    {
        return lat;
    }

    public void setLat (double lat)
    {
        this.lat = lat;
    }

    public double getLong ()
    {
        return lng;
    }

    public void setLong (double lng)
    {
        this.lng = lng;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lat = "+lat+", long = "+lng+"]";
    }
}

package com.ygaps.travelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StopPoint implements Serializable {
    @SerializedName("leaveAt")
    private long leaveAt;
    @SerializedName("name")
    private String name;
    @SerializedName("maxCost")
    private long maxCost;
    @SerializedName("serviceTypeId")
    private int serviceTypeId;
    @SerializedName("id")
    private int id;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("minCost")
    private long minCost;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    @Expose
    private double lng;
    @SerializedName("arrivalAt")
    private long arrivalAt;
    private String address;
    private long provinceId;
    public long getLeaveAt ()
    {
        return leaveAt;
    }

    public void setLeaveAt (long leaveAt)
    {
        this.leaveAt = leaveAt;
    }


    public long getMaxCost ()
    {
        return maxCost;
    }

    public void setMaxCost (long maxCost)
    {
        this.maxCost = maxCost;
    }

    public int getServiceTypeId ()
    {
        return serviceTypeId;
    }

    public void setServiceTypeId (int serviceTypeId)
    {
        this.serviceTypeId = serviceTypeId;
    }

    public void setId (int id)
    {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public long getMinCost ()
    {
        return minCost;
    }

    public void setMinCost (long minCost)
    {
        this.minCost = minCost;
    }


    public double getLong ()
    {
        return lng;
    }

    public void setLong (double lng)
    {
        this.lng = lng;
    }








    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    public long getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(long arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [leaveAt = "+leaveAt+", name = "+name+", maxCost = "+maxCost+", serviceTypeId = "+serviceTypeId+", id = "+id+", avatar = "+avatar+", minCost = "+minCost+", lat = "+lat+", lng = "+lng+", arrivalAt = "+arrivalAt+"]";
    }
}

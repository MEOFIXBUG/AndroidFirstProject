package com.kumeo.traveltour.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopPoint {
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
    private long lat;
    @SerializedName("long")
    @Expose
    private long lng;
    @SerializedName("arrivalAt")
    private long arrivalAt;
    public long getLeaveAt ()
    {
        return leaveAt;
    }

    public void setLeaveAt (long leaveAt)
    {
        this.leaveAt = leaveAt;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
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

    public long getLat ()
    {
        return lat;
    }

    public void setLat (long lat)
    {
        this.lat = lat;
    }

    public long getLong ()
    {
        return lng;
    }

    public void setLong (long lng)
    {
        this.lng = lng;
    }

    public long getArrivalAt ()
    {
        return arrivalAt;
    }

    public void setArrivalAt (long arrivalAt)
    {
        this.arrivalAt = arrivalAt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [leaveAt = "+leaveAt+", name = "+name+", maxCost = "+maxCost+", serviceTypeId = "+serviceTypeId+", id = "+id+", avatar = "+avatar+", minCost = "+minCost+", lat = "+lat+", lng = "+lng+", arrivalAt = "+arrivalAt+"]";
    }
}

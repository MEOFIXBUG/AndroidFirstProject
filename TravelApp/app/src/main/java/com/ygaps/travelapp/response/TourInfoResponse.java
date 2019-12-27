package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.UserInfo;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.comment;

import java.util.List;

public class TourInfoResponse {
    @SerializedName("endDate")
    @Expose
    private long endDate;
    @SerializedName("adults")
    @Expose
    private int adults;
    @SerializedName("hostId")
    @Expose
    private String hostId;
    @SerializedName("isPrivate")
    @Expose
    private boolean isPrivate;
    @SerializedName("childs")
    @Expose
    private int childs;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("maxCost")
    @Expose
    private String maxCost;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("minCost")
    @Expose
    private String minCost;
    @SerializedName("startDate")
    @Expose
    private long startDate;
    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("stopPoints")
    @Expose
    private List<StopPoint> stopPoints=null;
    @SerializedName("comments")
    @Expose
    private List<comment> comments=null;
    @SerializedName("members")
    @Expose
    private List<UserInfo> members=null;

    public List<StopPoint> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoint(List<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }

    public List<comment> getComments() {
        return comments;
    }

    public void setComment(List<comment> comments) {
        this.comments = comments;
    }
    public List<UserInfo> getUInfo() {
        return members;
    }

    public void setUInfo(List<UserInfo> userInfos) {
        this.members = userInfos;
    }
    public long getEndDate ()
    {
        return endDate;
    }

    public void setEndDate (long endDate)
    {
        this.endDate = endDate;
    }

    public int getAdults ()
    {
        return adults;
    }

    public void setAdults (int adults)
    {
        this.adults = adults;
    }
    public String getHostId ()
    {
        return hostId;
    }

    public void setHostId (String hostId)
    {
        this.hostId = hostId;
    }

    public boolean getIsPrivate ()
    {
        return isPrivate;
    }

    public void setIsPrivate (boolean isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    public int getChilds ()
    {
        return childs;
    }

    public void setChilds (int childs)
    {
        this.childs = childs;
    }
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getMaxCost ()
    {
        return maxCost;
    }

    public void setMaxCost (String maxCost)
    {
        this.maxCost = maxCost;
    }

    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    public String getMinCost ()
    {
        return minCost;
    }

    public void setMinCost (String minCost)
    {
        this.minCost = minCost;
    }

    public long getStartDate ()
    {
        return startDate;
    }

    public void setStartDate (long startDate)
    {
        this.startDate = startDate;
    }

    public int getStatus ()
    {
        return status;
    }

    public void setStatus (int status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [comments = "+comments+", endDate = "+endDate+", adults = "+adults+", stopPoints = "+stopPoints+", hostId = "+hostId+", isPrivate = "+isPrivate+", childs = "+childs+", members = "+members+", name = "+name+", maxCost = "+maxCost+", id = "+id+", minCost = "+minCost+", startDate = "+startDate+", status = "+status+"]";
    }
}

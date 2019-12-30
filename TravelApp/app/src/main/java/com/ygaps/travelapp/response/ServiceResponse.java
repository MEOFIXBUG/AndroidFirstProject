package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponse {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(long maxCost) {
        this.maxCost = maxCost;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSelfStarRating() {
        return selfStarRating;
    }

    public void setSelfStarRating(int selfStarRating) {
        this.selfStarRating = selfStarRating;
    }

    public long getMinCost() {
        return minCost;
    }

    public void setMinCost(long minCost) {
        this.minCost = minCost;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("name")
    private String name;
    @SerializedName("contact")
    private String contact;
    @SerializedName("maxCost")
    private long maxCost;
    @SerializedName("serviceTypeId")
    private int serviceTypeId;

    @SerializedName("id")
    private int id;

    @SerializedName("selfStarRating")
    private int selfStarRating;


    @SerializedName("minCost")
    private long minCost;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    @Expose
    private double lng;
    private String address;
}

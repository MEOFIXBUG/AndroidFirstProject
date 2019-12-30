package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class isAccept {
    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    @SerializedName("tourId")
    private String tourId;
    @SerializedName("isAccepted")
    private Boolean isAccepted;
}
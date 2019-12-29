package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class ReviewTour  {
    @SerializedName("tourId")
    private long tourId;
    @SerializedName("point")
    private long point;
    @SerializedName("review")
    private String review;

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


}

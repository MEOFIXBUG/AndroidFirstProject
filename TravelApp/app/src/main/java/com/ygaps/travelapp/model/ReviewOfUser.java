package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class ReviewOfUser {
    @SerializedName("id")
    private long id;
    @SerializedName("review")
    private String review;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar")
    private String avatar;

    @SerializedName("point")
    private long point;
    @SerializedName("createdOn")
    private long createdOn;

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



}

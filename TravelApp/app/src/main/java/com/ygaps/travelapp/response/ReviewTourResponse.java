package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.model.ReviewOfUser;


import java.util.List;

public class ReviewTourResponse {
    @SerializedName("total")
    @Expose
    private long total;
    @SerializedName("reviewList")
    @Expose
    private List<ReviewOfUser> reviewList=null;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ReviewOfUser> getreviewList() {
        return reviewList;
    }

    public void setreviewList(List<ReviewOfUser> reviews) {
        this.reviewList = reviews;
    }


}

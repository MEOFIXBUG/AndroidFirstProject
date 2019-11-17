package com.kumeo.traveltour.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kumeo.traveltour.model.Tour;

import java.util.List;

public class TourResponse {
    @SerializedName("total")
    @Expose
    private long total;
    @SerializedName("tours")
    @Expose
    private List<Tour> tours=null;

    public long getTotal() {
        return total;
    }

    public void setTotal(Integer totalResults) {
        this.total = totalResults;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> Tours) {
        this.tours = Tours;
    }

}

package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.model.Tour;

import java.util.ArrayList;
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
    public List<Tour> getUnDeletedTour() {

        List<Tour> res= new ArrayList<>();
        for (Tour a: this.tours) {
            if(a.getStatus()!=-1){
                res.add(a);
            }
        }
        return res;
    }

    public void setTours(List<Tour> Tours) {
        this.tours = Tours;
    }
}

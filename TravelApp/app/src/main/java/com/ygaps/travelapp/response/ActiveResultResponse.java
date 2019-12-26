package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveResultResponse {

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @SerializedName("success")
    @Expose
    private Boolean success;

}

package com.ygaps.travelapp.response;

import com.google.gson.annotations.SerializedName;

public class RecoveryResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    private String message;
}

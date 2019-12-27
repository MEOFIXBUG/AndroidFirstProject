package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusResponse {
    @SerializedName("message")
    @Expose
    private String message;
    public String getMessage() { return message; }

    public void setMessage(String value) { this.message = value; }
}

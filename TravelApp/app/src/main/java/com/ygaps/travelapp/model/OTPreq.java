package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class OTPreq {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private String value;
}

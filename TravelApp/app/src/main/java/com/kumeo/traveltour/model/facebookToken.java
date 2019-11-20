
package com.kumeo.traveltour.model;
import com.google.gson.annotations.SerializedName;

public class facebookToken {
    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

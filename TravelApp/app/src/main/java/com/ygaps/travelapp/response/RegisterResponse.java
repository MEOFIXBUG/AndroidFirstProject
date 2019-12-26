package com.ygaps.travelapp.response;

import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.model.Account;

public class RegisterResponse extends Account {
    @SerializedName("email_verified")
    private boolean emailVerified;
    @SerializedName("phone_verified")
    private boolean phoneVerified;

    public boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified( boolean EmailVerified ) {
        this.emailVerified=EmailVerified;
    }
    public boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified( boolean PhoneVerified ) {
        this.phoneVerified=PhoneVerified;
    }

}

package com.kumeo.traveltour.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("address")
    private String adress;
    @SerializedName("password")
    private String password;
    @SerializedName("fullName")
    private String full_name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("emailPhone")
    private String emailPhone;
    @SerializedName("gender")
    private boolean gender; //0: Female, 1: Male
    @SerializedName("dob")
    private String dob; //yyyy-mm-dd

    public boolean getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getFullName() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setGender( boolean Gender ) {
        this.gender=Gender;
    }

    public void setEmail( String Email ) {
        this.email=Email;
    }

    public void setPassword(String Password ) {
        this.password=Password;
    }

    public void setPhone(String Phone ) {
        this.phone=Phone;
    }

    public void setAdress(String Address ) {
        this.adress=Address;
    }

    public void setFullName( String FullName ) {
        this.full_name=FullName;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public void setEmailPhone( String EmailPhone) {

        this.emailPhone=EmailPhone;
    }
}

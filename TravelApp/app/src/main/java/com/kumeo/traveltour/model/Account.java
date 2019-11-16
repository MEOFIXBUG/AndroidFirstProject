package com.kumeo.traveltour.model;

import com.google.gson.annotations.SerializedName;

public class Account {

    private String adress;
    @SerializedName("password")
    private String password;

    private String fullName;

    private String email;

    private String phone;
    @SerializedName("emailPhone")
    private String emailPhone;

    private boolean gender; //0: Female, 1: Male

    private String dob; //yyyy-mm-dd

    public boolean getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getFullName() {
        return fullName;
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
        this.fullName=FullName;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public void setEmailPhone( String EmailPhone) {

        this.emailPhone=EmailPhone;
    }
}

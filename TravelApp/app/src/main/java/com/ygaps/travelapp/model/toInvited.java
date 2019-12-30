package com.ygaps.travelapp.model;

public class toInvited {
    private String tourId;
    private String invitedUserId ;
    private boolean isInvited;

    public boolean  getIsInvited(){return isInvited;}
    public void setInvited(boolean IsInvited) {
        isInvited = IsInvited;
    }
    public String getTourId() { return tourId; }

    public void setTourId(String value) { this.tourId = value; }
    public String getInvitedUserId() { return invitedUserId; }

    public void setInvitedUserId(String value) { this.invitedUserId = value; }



}

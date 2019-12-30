package com.ygaps.travelapp.model;

public class Tour {
    private long id;
    private long status;
    private String name;
    private String minCost;
    private String maxCost;
    private long startDate;
    private long endDate;
    private long adults;
    private long childs;
    private boolean isPrivate;
    private String avatar;
    private long sourceLat;
    private long sourceLong;
    private long desLat;
    private long desLong;
    private String hostId;
    private String hostName;
//    @SerializedName("stopPoints")
//    @Expose
//    private List<StopPoint> stopPoints=null;
//    @SerializedName("comments")
//    @Expose
//    private List<StopPoint> comments=null;
//    @SerializedName("comments")
//    @Expose
//    private List<UserInfo> members=null;


    public Tour() {
    }


    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public long getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(long sourceLat) {
        this.sourceLat = sourceLat;
    }

    public long getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(long sourceLong) {
        this.sourceLong = sourceLong;
    }

    public long getDesLat() {
        return desLat;
    }

    public void setDesLat(long desLat) {
        this.desLat = desLat;
    }

    public long getDesLong() {
        return desLong;
    }

    public void setDesLong(long desLong) {
        this.desLong = desLong;
    }


    public long getID() { return id; }

    public void setID(long value) { this.id = value; }


    public long getStatus() { return status; }

    public void setStatus(long value) { this.status = value; }


    public String getName() { return name; }

    public void setName(String value) { this.name = value; }


    public String getMinCost() { return minCost; }

    public void setMinCost(String value) { this.minCost = value; }


    public String getMaxCost() { return maxCost; }

    public void setMaxCost(String value) { this.maxCost = value; }


    public long getStartDate() { return startDate; }

    public void setStartDate(long value) { this.startDate = value; }


    public long getEndDate() { return endDate; }

    public void setEndDate(long value) { this.endDate = value; }


    public long getAdults() { return adults; }

    public void setAdults(long value) { this.adults = value; }

    public long getChilds() { return childs; }

    public void setChilds(long value) { this.childs = value; }

    public boolean getIsPrivate() { return isPrivate; }
    public void setIsPrivate(boolean value) { this.isPrivate = value; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String value) { this.avatar = value; }
    public String getHostName() { return this.hostName; }
    public void setHostName(String value) { this.hostName = value; }
}

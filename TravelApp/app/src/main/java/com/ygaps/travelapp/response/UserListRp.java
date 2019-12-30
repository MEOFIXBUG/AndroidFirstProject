package com.ygaps.travelapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.model.UserInfo;

import java.util.List;

public class UserListRp {
    @SerializedName("total")
    @Expose
    private long total;
    @SerializedName("users")
    @Expose
    private List<UserInfo> users=null;

    public long getTotal() {
        return total;
    }

    public void setTotal(Integer totalResults) {
        this.total = totalResults;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> data) {
        this.users = data;
    }
}

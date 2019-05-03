package com.test.genplaza.users.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UsersResponse {
    @Expose
    @SerializedName("page")
    private int page;

    @Expose
    @SerializedName("per_page")
    private int perPage;

    @Expose
    @SerializedName("total")
    private int total;

    @Expose
    @SerializedName("total_pages")
    private int totalPages;

    @Expose
    @SerializedName("data")
    private List<User> userList;


    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<User> getUserList() {
        return userList!=null?userList:new ArrayList<User>();
    }
}

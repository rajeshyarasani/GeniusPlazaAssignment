package com.test.genplaza.users.ui.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @Expose
    @SerializedName("id")
    private int userId;
    @Expose
    @SerializedName("first_name")
    private String firstName;
    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    @SerializedName("avatar")
    private String avatar;

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName != null ? firstName : ""; // to avoid null pointer exception
    }

    public String getLastName() {
        return lastName != null ? lastName : "";
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserName() {
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            return firstName + " " + lastName;
        } else if (firstName.isEmpty()) {
            return lastName;
        } else if (lastName.isEmpty()) {
            return firstName;
        }
        return " ";
    }
}

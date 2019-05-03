package com.test.genplaza.users.network.model;

public class NewUser {

    private String name;
    private String job;
    private String id;
    private String createdAt;

    public NewUser(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
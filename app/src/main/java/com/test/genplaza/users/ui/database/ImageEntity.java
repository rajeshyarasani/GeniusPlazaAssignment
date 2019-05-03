package com.test.genplaza.users.ui.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "ImageEntity")
public class ImageEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "imageId")
    private String imageId;

    @ColumnInfo(name = "comments")
    private List<String> imageComments;

    public List<String> getComments() {
        return imageComments != null ? imageComments : new ArrayList<String>();
    }

    public void setComments(List<String> comments) {
        this.imageComments = comments;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
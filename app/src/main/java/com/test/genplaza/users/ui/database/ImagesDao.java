package com.test.genplaza.users.ui.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface ImagesDao {

    @Query("SELECT * FROM ImageEntity WHERE imageId == :iid LIMIT 1")
    ImageEntity getImageWithComments(String iid);

    @Insert
    void insertImageTable(ImageEntity imageTable);

    @Update
    void updateImageTable(ImageEntity imageTable);
}
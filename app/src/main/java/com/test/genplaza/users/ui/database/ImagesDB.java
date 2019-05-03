package com.test.genplaza.users.ui.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ImageEntity.class}, version = RoomDBConstants.ROOM_DB_VERSION)
@TypeConverters({AxxessConverter.class})
public abstract class ImagesDB extends RoomDatabase {

    private static final String DB_NAME = RoomDBConstants.ROOM_DB_NAME;
    private static volatile ImagesDB instance;

    static synchronized ImagesDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public static void closeDB() {
        try {
            if (instance != null && instance.isOpen()) {
                instance.close();
                instance = null;
            }
        } catch (Exception e) {
            instance = null;
        }
    }
    private static ImagesDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                ImagesDB.class,
                DB_NAME)
                .allowMainThreadQueries().
                        build();
    }

    public abstract ImagesDao getRepoDao();
}
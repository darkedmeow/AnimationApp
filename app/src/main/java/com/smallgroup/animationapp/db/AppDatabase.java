package com.smallgroup.animationapp.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {UserProject.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase db;

    public AppDatabase() {}
    public abstract UserProjectDao userProjectDao();

    public AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, "anim-db").build();
        }
        return db;
    }

}

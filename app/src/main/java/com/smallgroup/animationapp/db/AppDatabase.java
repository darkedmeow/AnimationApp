package com.smallgroup.animationapp.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {UserProject.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public AppDatabase() {}
    public abstract UserProjectDao userProjectDao();

}

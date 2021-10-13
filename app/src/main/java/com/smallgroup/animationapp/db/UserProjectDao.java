package com.smallgroup.animationapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserProjectDao {

    @Query("SELECT * FROM user_project")
    List<UserProject> loadAll();

    @Query("SELECT * FROM user_project WHERE uid IN (:userIds)")
    List<UserProject> loadAllByIds(int userIds);

    @Query("SELECT title FROM user_project")
    LiveData<List<String>> getAllProjectTitle();

    @Insert
    void insert(UserProject project);

    @Delete
    void delete(UserProject project);

}

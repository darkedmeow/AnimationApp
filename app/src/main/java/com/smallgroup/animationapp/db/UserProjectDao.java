package com.smallgroup.animationapp.db;

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

    @Insert
    void insert(UserProject project);

    @Delete
    void delete(UserProject project);

}

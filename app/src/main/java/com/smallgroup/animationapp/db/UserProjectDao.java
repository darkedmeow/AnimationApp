package com.smallgroup.animationapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.smallgroup.animationapp.domain.model.Frame;

import java.util.List;

@Dao
public interface UserProjectDao {

    @Query("SELECT * FROM user_project")
    List<UserProject> loadAll();


    @Query("SELECT * FROM user_project WHERE uid IN (:userIds)")
    List<UserProject> loadAllByIds(int userIds);

    @Query("SELECT frames FROM user_project WHERE uid = (:id)")
    List<Frame> getListFrameById(int id);

    @Query("SELECT title FROM user_project")
    List<String> getAllProjectTitle();

    @Query("SELECT uid, title FROM user_project")
    List<TitleProject> getAllProjectUidAndTitle();

    @Update
    void update(UserProject project);

    @Insert
    long insert(UserProject project);

    @Delete
    void delete(UserProject project);

}

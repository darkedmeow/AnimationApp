package com.smallgroup.animationapp.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "TABLE_PROJECTS")
public class AnimationProject {

    @PrimaryKey
    @ColumnInfo(name = "project_id")
    public int idProject;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "frames_list")
    private ArrayList<String> frames;
}

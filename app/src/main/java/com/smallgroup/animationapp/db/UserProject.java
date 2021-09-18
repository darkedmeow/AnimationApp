package com.smallgroup.animationapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.smallgroup.animationapp.domain.model.Frame;
import com.smallgroup.animationapp.utils.Converter;

import java.util.List;

@Entity(tableName = "user_project")
public class UserProject {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "frames", typeAffinity = ColumnInfo.BLOB)
    @TypeConverters(Converter.class)
    public List<Frame> frames;



}

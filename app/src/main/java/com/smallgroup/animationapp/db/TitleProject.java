package com.smallgroup.animationapp.db;

import androidx.room.Entity;

@Entity
public class TitleProject {

    public String title;
    public int uid;

    public TitleProject() {
    }

    public TitleProject(String title, int uid) {
        this.title = title;
        this.uid = uid;
    }
}

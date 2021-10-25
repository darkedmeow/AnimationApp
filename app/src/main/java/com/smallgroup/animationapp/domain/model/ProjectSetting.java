package com.smallgroup.animationapp.domain.model;

import java.io.Serializable;

public class ProjectSetting implements Serializable {

    public String title;
    public String fps;
    private int color;

    public ProjectSetting(String title, String fps, int color) {
        this.title = title;
        this.fps = fps;
        this.color = color;
    }

    public ProjectSetting(String title, String fps) {
        this.title = title;
        this.fps = fps;
        this.color = -1;
    }

    public String info() {
        return title + " " + fps + " " + color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

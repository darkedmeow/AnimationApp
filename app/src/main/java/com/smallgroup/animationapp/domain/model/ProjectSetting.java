package com.smallgroup.animationapp.domain.model;

public class ProjectSetting {

    public String title;
    public int fps;
    private int color;

    public ProjectSetting(String title, int fps, int color) {
        this.title = title;
        this.fps = fps;
        this.color = color;
    }

    public ProjectSetting(String title, int fps) {
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

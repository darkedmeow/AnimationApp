package com.smallgroup.animationapp.domain.model;

import android.net.Uri;

public class ProjectPreview {

    private String title;
    private Uri imgIcon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(Uri imgIcon) {
        this.imgIcon = imgIcon;
    }
}

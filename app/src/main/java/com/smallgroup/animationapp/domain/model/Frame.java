package com.smallgroup.animationapp.domain.model;

import androidx.room.TypeConverters;

import com.smallgroup.animationapp.utils.Converter;

import java.io.Serializable;
import java.util.ArrayList;

public class Frame implements Serializable {

    @TypeConverters(Converter.class)
    private ArrayList<DrawnPath> paths;

    public Frame() {
        paths = new ArrayList<>();
    }

    public Frame(ArrayList<DrawnPath> paths) {
        this.paths = paths;
    }

    public ArrayList<DrawnPath> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<DrawnPath> paths) {
        this.paths = paths;
    }

}

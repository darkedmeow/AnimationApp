package com.smallgroup.animationapp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Frame implements Serializable {

    private ArrayList<DrawnPath> paths;
    private int n;

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

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

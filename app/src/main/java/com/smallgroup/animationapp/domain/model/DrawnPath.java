package com.smallgroup.animationapp.domain.model;

import android.graphics.Paint;
import android.graphics.Path;

import java.io.Serializable;

public class DrawnPath implements Serializable {

    private Paint paint;
    private Path path;

    public DrawnPath() {}

    public DrawnPath(Paint paint, Path path) {
        this.paint = paint;
        this.path = path;
    }

    public DrawnPath(DrawnPath drawnPath) {
        this.paint = drawnPath.getPaint();
        this.path = drawnPath.getPath();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}

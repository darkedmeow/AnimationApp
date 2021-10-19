package com.smallgroup.animationapp.ui.app.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.smallgroup.animationapp.domain.model.DrawnPath;
import com.smallgroup.animationapp.domain.model.Frame;

import java.util.ArrayList;

public class DrawingView extends View {



    private ArrayList<Bitmap> listBitmaps; // frames of project in bitmap
    private ArrayList<DrawnPath> prevPaths;
    private ArrayList<Path> tempPaths;

    private ArrayList<DrawnPath> drawnPathList;
    private ArrayList<Frame> frames;

    private int backgroundColor;
    private int paintColor;
    private int strokeWidth;
    private int opacity = 127;

    private Path drawPath;
    private DrawnPath redoPath;
    private Paint prevFramePaint;
    private Paint drawPaint, canvasPaint, erasePaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    private boolean canErase;
    private boolean canRedo;

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setupDrawing();

    }

    private void init() {
        frames = new ArrayList<>();
        listBitmaps = new ArrayList<>();
        tempPaths = new ArrayList<>();
        prevPaths = new ArrayList<>();

        drawnPathList = new ArrayList<>();
    }

    public ArrayList<Bitmap> getListBitmaps() {
        return listBitmaps;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    private void setupDrawing() {

        drawPath = new Path();
        drawPaint = new Paint();
        prevFramePaint = new Paint();

        backgroundColor = Color.WHITE;
        paintColor = Color.BLACK;
        strokeWidth = 20;
        canErase = false;
        canRedo = false;

        drawPaint.setColor(paintColor);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(strokeWidth);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        erasePaint = new Paint();
        erasePaint.set(drawPaint);
        erasePaint.setColor(backgroundColor);
        erasePaint.setStrokeWidth(strokeWidth + 10);

        prevFramePaint.set(drawPaint);
        prevFramePaint.setAlpha(opacity);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }


    public void clearAndSaveBitmap(){

        prepareFrame();
        //save bitmap
        listBitmaps.add(Bitmap.createBitmap(canvasBitmap));
        frames.add(new Frame(drawnPathList));
        //COLOR FON
        canvasBitmap.eraseColor(backgroundColor);

        redoPath = new DrawnPath();
        canRedo = false;

        prevPaths.addAll(drawnPathList);
        prevPaths.stream()
                .filter(el -> el.getPaint().getColor() != backgroundColor)
                .forEach(el -> el.getPaint().setAlpha(opacity));

        drawnPathList.clear();

        invalidate();
    }

    public void prepareFrame() {
        prevPaths.clear();
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);

        prevPaths.forEach(el -> canvas.drawPath(el.getPath(), el.getPaint()));
        drawnPathList.forEach(drawnPath -> drawCanvas.drawPath(drawnPath.getPath(), drawnPath.getPaint()));
        tempPaths.forEach(path -> canvas.drawPath(path, drawPaint));
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, new Paint(drawPaint));
                if (canErase) {
                    drawnPathList.add(new DrawnPath(new Paint(erasePaint), new Path(drawPath)));
                }
                else {
                    drawnPathList.add(new DrawnPath(new Paint(drawPaint), new Path(drawPath)));
                }
                tempPaths.clear();
                drawPath.reset();
                break;
            default:
                return false;
        }

        tempPaths.add(new Path(drawPath));

        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //COLOR FON
        canvasBitmap.eraseColor(backgroundColor);

        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPaintColor(String color) {
        this.paintColor = Color.parseColor(color);
        drawPaint.setColor(paintColor);
    }

    public void undo() {
        if (drawnPathList.size() > 1) {
            canRedo = true;
            redoPath = drawnPathList.get(drawnPathList.size() - 1);
            drawnPathList.remove(drawnPathList.size() - 1);
            drawCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();
        }
    }

    public void redo() {
        if (canRedo) {
            drawnPathList.add(new DrawnPath(redoPath));
            canRedo = false;
            invalidate();
        }
    }

    public void setBrush() {
        canErase = false;
        drawPaint.setColor(paintColor);
        drawPaint.setStrokeWidth(strokeWidth);
    }

    public void setPen() {
        canErase = false;
        drawPaint.setStrokeWidth(10);
        drawPaint.setColor(Color.BLACK);
    }

    public void turnOnErase(){
        canErase = true;
    }


    public void setPaintWidth(float width) {
        drawPaint.setStrokeWidth(width);
    }
}

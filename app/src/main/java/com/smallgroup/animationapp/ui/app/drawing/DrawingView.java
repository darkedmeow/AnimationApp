package com.smallgroup.animationapp.ui.app.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawingView extends View {



    private ArrayList<Bitmap> listBitmaps;
    private ArrayList<Path> paths;

    private int backgroundColor = Color.WHITE;
    private int paintColor = Color.BLUE;
    private int strokeWidth = 20;

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    private boolean erase = false;

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setupDrawing();

    }

    private void init() {
        listBitmaps = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public ArrayList<Bitmap> getListBitmaps() {
        return listBitmaps;
    }

    private void setupDrawing() {

        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColor);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(strokeWidth);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }


    public void clearAndSaveBitmap(){
        //save bitmap
        listBitmaps.add(Bitmap.createBitmap(canvasBitmap));
        //COLOR FON
        canvasBitmap.eraseColor(backgroundColor);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
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
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

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

    public void brush() {
        drawPaint.setColor(paintColor);
        drawPaint.setStrokeWidth(strokeWidth);
    }

    public void pen() {
        drawPaint.setStrokeWidth(10);
        drawPaint.setColor(Color.BLACK);
    }

    public void setErase(boolean isErase) {
        erase = isErase;
        if(erase)
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else
            drawPaint.setXfermode(null);
    }

    public void setPaintWidth(float width) {
        drawPaint.setStrokeWidth(width);
    }
}

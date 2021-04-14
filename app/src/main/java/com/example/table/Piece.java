package com.example.table;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.telephony.RadioAccessSpecifier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Piece extends Drawable {
    private final float RADIUS = 100;

    private Paint color = new Paint();
    private int x, y;
    private int left, top, right, bottom;
    private boolean removed;


    public Piece(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color.setColor(color);
        this.left = x;
        this.top = y;
        this.right = (int) (x + RADIUS);
        this.bottom = (int) (y + RADIUS);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(removed) {
            canvas.drawRoundRect(left, top, right, bottom, RADIUS, RADIUS, color);
        } else {
            canvas.drawCircle(x, y, RADIUS, color);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    //dadea eroare ca nu e abstracta si nu da override
    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    public void setColor(int color){
        this.color.setColor(color);
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
}

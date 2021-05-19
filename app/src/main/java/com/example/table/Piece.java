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
    private final int RADIUS = 50;

    private int player;//0=brown, 1=red, so as to not confuse pieces with the triangle's colors
    private Paint color = new Paint();
    private int x, y;
    private int left, top, right, bottom;
    private boolean removed;


    public Piece(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
        if(player == 1)
            this.color.setColor(Color.rgb(190, 45, 50)); //red
        else
            this.color.setColor(Color.rgb(125, 100, 50)); //brown
        this.left = x - RADIUS;
        this.top = y + RADIUS;
        this.right = x + RADIUS;
        this.bottom = y - RADIUS;
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

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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Piece extends Drawable {
    private final int RADIUS = Triangle.RADIUS;

    //0=red, 1=brown
    private Paint colorRed = new Paint();
    private Paint colorBrown = new Paint();
    private final Paint black = new Paint();
    private final Paint possible = new Paint();
    private int redHit;
    private int brownHit;
    private int redRemoved;
    private int brownRemoved;
    private int screenWidth;
    private int screenHeight;

    private boolean drawRedPossible;
    private boolean drawBrownPossible;

    private final float dist2R = (float) 0.026; //distance between 2 removed pieces <=> height of a drawed removed piece
    private final float xStart = (float) 0.956;
    private final float xEnd = (float) 0.99;
    private final float yRedStart = (float) 0.06;
    private final float yRedEnd = (float) 0.45;
    private final float yBrownStart = (float) 0.55;
    private final float yBrownEnd = (float) 0.94;


    public Piece(int screenWidth, int screenHeight, double cutoutOffset) {
        this.drawRedPossible = false;
        this.drawBrownPossible = false;
        this.possible.setColor(Color.argb(150, 0, 200, 200));
        this.black.setColor(Color.rgb(0, 0, 0));
        this.colorRed.setColor(Color.rgb(190, 45, 50));
        this.colorBrown.setColor(Color.rgb(145, 145, 145));
        this.redHit = 0;
        this.brownHit = 0;
        this.redRemoved = 0;
        this.brownRemoved = 0;
        this.screenWidth = (int)(screenWidth - cutoutOffset);
        this.screenHeight = screenHeight;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(redHit > 0) {
            canvas.drawCircle((float) (screenWidth * 0.5), (float) (screenHeight * 0.5 - RADIUS), RADIUS, colorRed);
            if(redHit > 1) {
                Paint textPaint = new Paint();
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setColor(colorBrown.getColor());
                textPaint.setTextSize((float)(RADIUS));
                canvas.drawText("+" + (redHit - 1), (float) (screenWidth * 0.487), (float) (screenHeight * 0.5 - RADIUS * 0.7), textPaint);
            }
        }
        if(brownHit > 0) {
            //canvas.drawRoundRect((float)(screenWidth * 0.475), (float)(screenHeight * 0.5), (float)(screenWidth * 0.525), (float)(screenHeight * 0.55), RADIUS, RADIUS, colorBrown);
            canvas.drawCircle((float) (screenWidth * 0.5), (float) (screenHeight * 0.5 + RADIUS), RADIUS, colorBrown);
            if(brownHit > 1) {
                Paint textPaint = new Paint();
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setColor(colorRed.getColor());
                textPaint.setTextSize((float)(RADIUS));
                canvas.drawText("+" + (brownHit - 1), (float) (screenWidth * 0.487), (float) (screenHeight * 0.5 + RADIUS * 1.3), textPaint);
            }
        }

        if(redRemoved > 0) {
            //start placing pieces from top to bottom
            for(int i = 0; i < redRemoved; i++) { //draw black border and red filling
                canvas.drawRoundRect((float) (screenWidth * xStart), (float) (screenHeight * (yRedStart + dist2R * i)), (float) (screenWidth * xEnd), (float) (screenHeight * (yRedStart + dist2R * (i + 1))), RADIUS, RADIUS, black);
                canvas.drawRoundRect((float) (screenWidth * xStart + 2), (float) (screenHeight * (yRedStart + dist2R * i) + 2), (float) (screenWidth * xEnd - 2), (float) (screenHeight * (yRedStart + dist2R * (i + 1)) - 2), RADIUS, RADIUS, colorRed);
            }
        }
        if(brownRemoved > 0) {
            //start placing pieces top to bottom
            for(int i = 0; i < brownRemoved; i++) { //draw black border and brown filling
                canvas.drawRoundRect((float) (screenWidth * xStart), (float) (screenHeight * (yBrownStart + dist2R * i)), (float) (screenWidth * xEnd), (float) (screenHeight * (yBrownStart + dist2R * (i + 1))), RADIUS, RADIUS, black);
                canvas.drawRoundRect((float) (screenWidth * xStart + 2), (float) (screenHeight * (yBrownStart + dist2R * i) + 2), (float) (screenWidth * xEnd - 2), (float) (screenHeight * (yBrownStart + dist2R * (i + 1)) - 2), RADIUS, RADIUS, colorBrown);
            }
        }

        if(drawRedPossible) {
            canvas.drawRect((float) (screenWidth * xStart), (float) (screenHeight * yRedStart), (float) (screenWidth * xEnd), (float) (screenHeight * yRedEnd), possible);
        }
        if(drawBrownPossible) {
            canvas.drawRect((float) (screenWidth * xStart), (float) (screenHeight * yBrownStart), (float) (screenWidth * xEnd), (float) (screenHeight * yBrownEnd), possible);
        }
    }

    public void addHitPieceToPlayer(int player) {
        switch (player) {
            case 0:
                this.redHit += 1;
                break;
            case 1:
                this.brownHit += 1;
                break;
            default:
                Log.i("hitPiece", "not added to any player");
                break;
        }
    }

    public void removeHitPieceFromPlayer(int player) {
        switch (player) {
            case 0:
                this.redHit -= 1;
                break;
            case 1:
                this.brownHit -= 1;
                break;
            default:
                Log.i("hitPiece", "not removed from any player");
                break;
        }
    }

    public void incRemovedPiece(int player) {
        switch (player) {
            case 0:
                this.redRemoved += 1;
            case 1:
                this.brownRemoved += 1;
        }
    }

    public boolean isRemovingHitPiece(int x, int y, int player) {
        switch (player) {
            case 0:
                if(screenWidth * 0.5 - RADIUS <= x && x <= screenWidth + RADIUS && screenHeight * 0.5 - 2 * RADIUS <= y && y  <= screenHeight * 0.5)
                    return true;
                break;
            case 1:
                if(screenWidth * 0.5 - RADIUS <= x && x <= screenWidth + RADIUS && screenHeight * 0.5 <= y && y  <= screenHeight * 0.5 + 2 * RADIUS)
                    return true;
                break;
            default:
                Log.i("hitPiece", "not selected");
                break;
        }
        return false;
    }

    public boolean getDrawRedPossible() {
        return drawRedPossible;
    }

    public void setDrawRedPossible(boolean drawRedPossible) {
        this.drawRedPossible = drawRedPossible;
    }

    public boolean getDrawBrownPossible() {
        return drawBrownPossible;
    }

    public void setDrawBrownPossible(boolean drawBrownPossible) {
        this.drawBrownPossible = drawBrownPossible;
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
}

package com.example.table;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Piece extends Drawable implements Serializable {
    private final int RADIUS = Triangle.RADIUS;

    //0=red, 1=brown
    private CustomPaint colorRed = new CustomPaint();
    private CustomPaint colorBrown = new CustomPaint();
    private CustomPaint black = new CustomPaint();
    private CustomPaint possible = new CustomPaint();
    private int redHit;
    private int brownHit;
    private boolean drawMovingRedHit;
    private boolean drawMovingBrownHit;
    private int x, y;
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

    public Piece(Piece piece) {
        this.possible.setColor(Color.argb(150, 0, 200, 200));
        this.black.setColor(Color.rgb(0, 0, 0));
        this.colorRed.setColor(Color.rgb(190, 45, 50));
        this.colorBrown.setColor(Color.rgb(145, 145, 145));
        this.redHit = piece.redHit;
        this.brownHit = piece.brownHit;
        this.drawMovingRedHit = piece.drawMovingRedHit;
        this.drawMovingBrownHit = piece.drawMovingBrownHit;
        this.x = piece.x;
        this.y = piece.y;
        this.redRemoved = piece.redRemoved;
        this.brownRemoved = piece.brownRemoved;
        this.screenWidth = piece.screenWidth;
        this.screenHeight = piece.screenHeight;
        this.drawRedPossible = piece.drawRedPossible;
        this.drawBrownPossible = piece.drawBrownPossible;
    }

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
            canvas.drawCircle((float) (screenWidth * 0.5), (float) (screenHeight * 0.5 + RADIUS), RADIUS, colorRed);
            if(redHit > 1) {
                android.graphics.Paint textPaint = new android.graphics.Paint();
                textPaint.setStyle(android.graphics.Paint.Style.FILL);
                textPaint.setColor(colorBrown.getColor());
                textPaint.setTextSize((float)(RADIUS));
                canvas.drawText("+" + (redHit - 1), (float) (screenWidth * 0.487), (float) (screenHeight * 0.5 + RADIUS * 1.3), textPaint);
            }
        }
        if(brownHit > 0) {
            canvas.drawCircle((float) (screenWidth * 0.5), (float) (screenHeight * 0.5 - RADIUS), RADIUS, colorBrown);
            if(brownHit > 1) {
                android.graphics.Paint textPaint = new android.graphics.Paint();
                textPaint.setStyle(android.graphics.Paint.Style.FILL);
                textPaint.setColor(colorRed.getColor());
                textPaint.setTextSize((float)(RADIUS));
                canvas.drawText("+" + (brownHit - 1), (float) (screenWidth * 0.487), (float) (screenHeight * 0.5 - RADIUS * 0.7), textPaint);
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

        if(drawMovingRedHit) {
            canvas.drawCircle(x, y, RADIUS, colorRed);
        }
        if(drawMovingBrownHit) {
            canvas.drawCircle(x, y, RADIUS, colorBrown);
        }
    }

    public int getRedHit() {
        return redHit;
    }

    public void setRedHit(int redHit) {
        this.redHit = redHit;
    }

    public int getBrownHit() {
        return brownHit;
    }

    public void setBrownHit(int brownHit) {
        this.brownHit = brownHit;
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
                break;
            case 1:
                this.brownRemoved += 1;
                break;
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

    public boolean getDrawMovingRedHit() {
        return drawMovingRedHit;
    }

    public void setDrawMovingRedHit(boolean drawMovingRedHit, int x, int y) {
        this.drawMovingRedHit = drawMovingRedHit;
        this.x = x;
        this.y = y;
    }
    public void setDrawMovingRedHit(boolean drawMovingRedHit) {
        this.drawMovingRedHit = drawMovingRedHit;
        this.x = 0;
        this.y = 0;
    }

    public boolean getDrawMovingBrownHit() {
        return drawMovingBrownHit;
    }

    public void setDrawMovingBrownHit(boolean drawMovingBrownHit, int x, int y) {
        this.drawMovingBrownHit = drawMovingBrownHit;
        this.x = x;
        this.y = y;
    }
    public void setDrawMovingBrownHit(boolean drawMovingBrownHit) {
        this.drawMovingBrownHit = drawMovingBrownHit;
        this.x = 0;
        this.y = 0;
    }

    public void setColors() {
        this.possible.setColor(Color.argb(150, 0, 200, 200));
        this.black.setColor(Color.rgb(0, 0, 0));
        this.colorRed.setColor(Color.rgb(190, 45, 50));
        this.colorBrown.setColor(Color.rgb(145, 145, 145));
    }

    public int getColorRed() {
        return colorRed.getColor();
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

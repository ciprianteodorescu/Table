package com.example.table;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Triangle extends Drawable {
    public static final int TOTAL = 24; //24 triangles on board
    private final int RADIUS = 50;

    private final double dist2Tr = 0.07; //distance between 2 triangles
    private final double xQ1Start = 0.56; //xQ1Start*screenWidth=x axis pos for 6th triangle
    private final double xQ2Start = 0.085;
    private final double xQ3Start = 0.085;
    private final double xQ4Start = 0.56;
    private final double yQ1Start = 0.99; //yQ1Start*screenHeight=y axis pos for 6th triangle
    private final double yQ2Start = 0.99;
    private final double yQ3Start = 0.01;
    private final double yQ4Start = 0.01;

    private int screenWidth, screenHeight;

    private int x, y; //piece center coordinates
    private int pieceColor; //color of pieces on triangle 0=brown, 1=red, -1=empty
    private Paint color = new Paint();
    private int nPieces; //no. of pieces on triangle
    private int trNo; //number of triangle, range [1 to 24]

    public Triangle(int trNo, int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.trNo = trNo;
        switch (trNo){
            case 1:
                this.pieceColor = 1;
                this.nPieces = 2;
                break;
            case 12: case 19:
                this.pieceColor = 1;
                this.nPieces = 5;
                break;
            case 17:
                this.pieceColor = 1;
                this.nPieces = 3;
                break;
            case 6: case 13:
                this.pieceColor = 0;
                this.nPieces = 5;
                break;
            case 8:
                this.pieceColor = 0;
                this.nPieces = 3;
                break;
            case 24:
                this.pieceColor = 0;
                this.nPieces = 2;
                break;
            default:
                this.pieceColor = -1;
                this.nPieces = 0;
                break;
        }
        if(this.pieceColor == 1) //red
            this.color.setColor(Color.rgb(190, 45, 50));
        else if(this.pieceColor == 0) //brown
            this.color.setColor(Color.rgb(125, 100, 50));
    }

    public void setnPieces(int nPieces){
        this.nPieces = nPieces;
    }

    public void setTrNo(int trNo){
        this.trNo = trNo;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        switch (trNo){
            case 1: case 2: case 3: case 4: case 5: case 6: //1st quarter
                for(int i = 1; i <= nPieces; i++){
                    canvas.drawCircle((int)(screenWidth*(xQ1Start+(6-trNo)*dist2Tr)), (int)(screenHeight*yQ1Start-(i-1)*2*RADIUS-RADIUS), RADIUS, color);
                    //Log.i("Triangle", String.valueOf((int)(screenHeight*yQ1Start-i*2*RADIUS)));
                }
                break;
            case 7: case 8: case 9: case 10: case 11: case 12: //2nd quarter
                for(int i = 1; i <= nPieces; i++){
                    canvas.drawCircle((int)(screenWidth*(xQ2Start+(12-trNo)*dist2Tr)), (int)(screenHeight*yQ2Start-(i-1)*2*RADIUS-RADIUS), RADIUS, color);
                }
                break;
            case 13: case 14: case 15: case 16: case 17: case 18: //3rd quarter
                for(int i = 1; i <= nPieces; i++){
                    canvas.drawCircle((int)(screenWidth*(xQ3Start-(13-trNo)*dist2Tr)), (int)(screenHeight*yQ3Start+(i-1)*2*RADIUS+RADIUS), RADIUS, color);
                }
                break;
            case 19: case 20: case 21: case 22: case 23: case 24: //4th quarter
                for(int i = 1; i <= nPieces; i++){
                    canvas.drawCircle((int)(screenWidth*(xQ4Start-(19-trNo)*dist2Tr)), (int)(screenHeight*yQ4Start+(i-1)*2*RADIUS+RADIUS), RADIUS, color);
                }
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}

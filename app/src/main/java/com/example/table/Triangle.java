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

    private int pieceColor; //color of pieces on triangle 0=brown, 1=red, -1=empty
    private Paint color = new Paint();
    private int nPieces; //no. of pieces on triangle
    private int trNo; //number of triangle, range [1 to 24]

    private boolean drawPossibleMoves; //decide if to draw or not possible moves

    public Triangle(int trNo, int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.trNo = trNo;
        switch (trNo){
            case 1:
                this.pieceColor = 0;
                this.nPieces = 2;
                break;
            case 12: case 19:
                this.pieceColor = 0;
                this.nPieces = 5;
                break;
            case 17:
                this.pieceColor = 0;
                this.nPieces = 3;
                break;
            case 6: case 13:
                this.pieceColor = 1;
                this.nPieces = 5;
                break;
            case 8:
                this.pieceColor = 1;
                this.nPieces = 3;
                break;
            case 24:
                this.pieceColor = 1;
                this.nPieces = 2;
                break;
            default:
                this.pieceColor = -1;
                this.nPieces = 0;
                break;
        }
        if(this.pieceColor == 0) //red
            this.color.setColor(Color.rgb(190, 45, 50));
        else if(this.pieceColor == 1) //brown
            this.color.setColor(Color.rgb(145, 145, 145));
    }



    @Override
    public void draw(@NonNull Canvas canvas) {
        switch (trNo){
            case 1: case 2: case 3: case 4: case 5: case 6: //1st quarter
                for(int i = 1; i <= nPieces; i++){
                    canvas.drawCircle((int)(screenWidth*(xQ1Start+(6-trNo)*dist2Tr)), (int)(screenHeight*yQ1Start-(i-1)*2*RADIUS-RADIUS), RADIUS, color);
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
                break;
        }

        if(drawPossibleMoves){
            float[] verts = new float[6];

            int[] colors = new int[3];
            colors[0] = Color.argb(150, 0, 200, 200);
            colors[1] = Color.argb(150, 0, 200, 200);
            colors[2] = Color.argb(150, 0, 200, 200);


            switch (trNo){
                case 1: case 2: case 3: case 4: case 5: case 6: //1st quarter
                    verts[0] = (float) (screenWidth * (xQ1Start + (6 - trNo) * dist2Tr - dist2Tr / 2));
                    verts[1] = (float) (screenHeight * yQ1Start);
                    verts[2] = (float) (screenWidth * (xQ1Start + (6 - trNo) * dist2Tr + dist2Tr / 2));
                    verts[3] = (float) (screenHeight * yQ1Start);
                    verts[4] = (float) (screenWidth * (xQ1Start + (6 - trNo) * dist2Tr));
                    verts[5] = (float) (screenHeight * 0.55);
                    break;
                case 7: case 8: case 9: case 10: case 11: case 12: //2nd quarter
                    verts[0] = (float) (screenWidth * (xQ2Start + (12 - trNo) * dist2Tr - dist2Tr / 2));
                    verts[1] = (float) (screenHeight * yQ2Start);
                    verts[2] = (float) (screenWidth * (xQ2Start + (12 - trNo) * dist2Tr + dist2Tr / 2));
                    verts[3] = (float) (screenHeight * yQ2Start);
                    verts[4] = (float) (screenWidth * (xQ2Start + (12 - trNo) * dist2Tr));
                    verts[5] = (float) (screenHeight * 0.55);
                    break;
                case 13: case 14: case 15: case 16: case 17: case 18: //3rd quarter
                    verts[0] = (float) (screenWidth * (xQ3Start - (13 - trNo) * dist2Tr - dist2Tr / 2));
                    verts[1] = (float) (screenHeight * yQ3Start);
                    verts[2] = (float) (screenWidth * (xQ3Start - (13 - trNo) * dist2Tr + dist2Tr / 2));
                    verts[3] = (float) (screenHeight * yQ3Start);
                    verts[4] = (float) (screenWidth * (xQ3Start - (13 - trNo) * dist2Tr));
                    verts[5] = (float) (screenHeight * 0.45);
                    break;
                case 19: case 20: case 21: case 22: case 23: case 24: //4th quarter
                    verts[0] = (float) (screenWidth * (xQ4Start - (19 - trNo) * dist2Tr - dist2Tr / 2));
                    verts[1] = (float) (screenHeight * yQ4Start);
                    verts[2] = (float) (screenWidth * (xQ4Start - (19 - trNo) * dist2Tr + dist2Tr / 2));
                    verts[3] = (float) (screenHeight * yQ4Start);
                    verts[4] = (float) (screenWidth * (xQ4Start - (19 - trNo) * dist2Tr));
                    verts[5] = (float) (screenHeight * 0.45);
                    break;
            }

            //https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API/Tutorial/Drawing_shapes
            canvas.drawVertices(Canvas.VertexMode.TRIANGLES, verts.length, verts, 0, null, 0, colors, 0, null, 0, 0, new Paint());
        }
    }

    public boolean chooseTriangle(int x, int y) {
        switch (trNo){
            case 1: case 2: case 3: case 4: case 5: case 6: //1st quarter
                if(screenWidth * (xQ1Start + (6 - trNo) * dist2Tr - dist2Tr / 2) <= x && x <= screenWidth * (xQ1Start + (6 - trNo) * dist2Tr + dist2Tr / 2))
                    if(screenHeight * yQ1Start >= y && screenHeight * 0.5 <= y) { //0.5 = half of the screen height
                        //Log.i("Pressed inside Triangle ", String.valueOf(nPieces > 0 && currentPlayer == pieceColor));
                        return nPieces > 0;
                    }
                return false;
            case 7: case 8: case 9: case 10: case 11: case 12: //2nd quarter
                if(screenWidth * (xQ2Start + (12 - trNo) * dist2Tr - dist2Tr / 2) <= x && x <= screenWidth * (xQ2Start + (12 - trNo) * dist2Tr + dist2Tr / 2))
                    if(screenHeight * yQ2Start >= y && screenHeight * 0.5 <= y) { //0.5 = half of the screen height
                        //Log.i("Pressed inside Triangle ", String.valueOf(trNo));
                        return nPieces > 0;
                    }
                return false;
            case 13: case 14: case 15: case 16: case 17: case 18: //3rd quarter
                if(screenWidth * (xQ3Start - (13 - trNo) * dist2Tr - dist2Tr / 2) <= x && x <= screenWidth * (xQ3Start - (13 - trNo) * dist2Tr + dist2Tr / 2))
                    if(screenHeight * yQ3Start <= y && screenHeight * 0.5 >= y) { //0.5 = half of the screen height
                        //Log.i("Pressed inside Triangle ", String.valueOf(trNo));
                        return nPieces > 0;
                    }
                return false;
            case 19: case 20: case 21: case 22: case 23: case 24: //4th quarter
                if(screenWidth * (xQ4Start - (19 - trNo) * dist2Tr - dist2Tr / 2) <= x && x <= screenWidth * (xQ4Start - (19 - trNo) * dist2Tr + dist2Tr / 2))
                    if(screenHeight * yQ4Start <= y && screenHeight * 0.5 >= y) { //0.5 = half of the screen height
                        //Log.i("Pressed inside Triangle ", String.valueOf(trNo));
                        return nPieces > 0;
                    }
                return false;
        }
        return false;
    }



    public int getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(int pieceColor) {
        this.pieceColor = pieceColor;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public int getnPieces() {
        return nPieces;
    }

    public void setnPieces(int nPieces) {
        this.nPieces = nPieces;
    }

    public int getTrNo() {
        return trNo;
    }

    public void setTrNo(int trNo) {
        this.trNo = trNo;
    }

    public boolean getDrawPossibleMoves() {
        return drawPossibleMoves;
    }

    public void setDrawPossibleMoves(boolean drawPossibleMoves) {
        this.drawPossibleMoves = drawPossibleMoves;
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

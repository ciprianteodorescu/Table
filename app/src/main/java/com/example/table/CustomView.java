package com.example.table;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {
    private List<Triangle> triangles = new ArrayList<Triangle>();
    private Piece hitPieces;
    private boolean gameStarted = false;
    public double cutoutOffset;
    public int width;
    public int height;

    private float[] verts = new float[6]; //coordinates of triangle vertices
    private int[] colors = new int[6];

    //https://www.youtube.com/watch?v=sb9OEl4k9Dk
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw triangles here so they're drawn before the pieces
        colors[0] = Color.argb(255, 255, 255, 255);//white
        colors[1] = Color.argb(255, 255, 255, 255);
        colors[2] = Color.argb(255, 255, 255, 255);
        colors[3] = Color.argb(255, 0, 0, 0);//black
        colors[4] = Color.argb(255, 0, 0, 0);
        colors[5] = Color.argb(255, 0, 0, 0);

        for(int trNo = 1; trNo <= Triangle.TOTAL; trNo++ ){
            switch (trNo){
                case 1: case 2: case 3: case 4: case 5: case 6: //1st quarter
                    verts[0] = (float) (width * (Triangle.xQ1Start + (6 - trNo) * Triangle.dist2Tr - Triangle.dist2Tr / 2));
                    verts[1] = (float) (height * Triangle.yQ1Start);
                    verts[2] = (float) (width * (Triangle.xQ1Start + (6 - trNo) * Triangle.dist2Tr + Triangle.dist2Tr / 2));
                    verts[3] = (float) (height * Triangle.yQ1Start);
                    verts[4] = (float) (width * (Triangle.xQ1Start + (6 - trNo) * Triangle.dist2Tr));
                    verts[5] = (float) (height * 0.55);

                    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, verts.length, verts, 0, null, 0, colors, 3*((trNo+1)%2), null, 0, 0, new Paint());
                    break;
                case 7: case 8: case 9: case 10: case 11: case 12: //2nd quarter
                    verts[0] = (float) (width * (Triangle.xQ2Start + (12 - trNo) * Triangle.dist2Tr - Triangle.dist2Tr / 2));
                    verts[1] = (float) (height * Triangle.yQ2Start);
                    verts[2] = (float) (width * (Triangle.xQ2Start + (12 - trNo) * Triangle.dist2Tr + Triangle.dist2Tr / 2));
                    verts[3] = (float) (height * Triangle.yQ2Start);
                    verts[4] = (float) (width * (Triangle.xQ2Start + (12 - trNo) * Triangle.dist2Tr));
                    verts[5] = (float) (height * 0.55);

                    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, verts.length, verts, 0, null, 0, colors, 3*((trNo+1)%2), null, 0, 0, new Paint());
                    break;
                case 13: case 14: case 15: case 16: case 17: case 18: //3rd quarter
                    verts[0] = (float) (width * (Triangle.xQ3Start - (13 - trNo) * Triangle.dist2Tr - Triangle.dist2Tr / 2));
                    verts[1] = (float) (height * Triangle.yQ3Start);
                    verts[2] = (float) (width * (Triangle.xQ3Start - (13 - trNo) * Triangle.dist2Tr + Triangle.dist2Tr / 2));
                    verts[3] = (float) (height * Triangle.yQ3Start);
                    verts[4] = (float) (width * (Triangle.xQ3Start - (13 - trNo) * Triangle.dist2Tr));
                    verts[5] = (float) (height * 0.45);

                    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, verts.length, verts, 0, null, 0, colors, 3*((trNo+1)%2), null, 0, 0, new Paint());
                    break;
                case 19: case 20: case 21: case 22: case 23: case 24: //4th quarter
                    verts[0] = (float) (width * (Triangle.xQ4Start - (19 - trNo) * Triangle.dist2Tr - Triangle.dist2Tr / 2));
                    verts[1] = (float) (height * Triangle.yQ4Start);
                    verts[2] = (float) (width * (Triangle.xQ4Start - (19 - trNo) * Triangle.dist2Tr + Triangle.dist2Tr / 2));
                    verts[3] = (float) (height * Triangle.yQ4Start);
                    verts[4] = (float) (width * (Triangle.xQ4Start - (19 - trNo) * Triangle.dist2Tr));
                    verts[5] = (float) (height * 0.45);

                    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, verts.length, verts, 0, null, 0, colors, 3*((trNo+1)%2), null, 0, 0, new Paint());
                    break;
            }
        }

        if(gameStarted) {
            for (int i = 0; i < Triangle.TOTAL; i++) {
                triangles.get(i).draw(canvas);
            }
            hitPieces.draw(canvas);
        }
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public List<Triangle> initializeTriangles() {
        for(int i = 1; i <= Triangle.TOTAL; i++) {
            this.triangles.add(new Triangle(i, width, height, cutoutOffset));
            //Log.i("screenView (w, h)", "("+width+", "+height+")");
        }
        return this.triangles;
    }

    public Piece initializeHitPieces(){
        this.hitPieces = new Piece(width, height, cutoutOffset);

        return this.hitPieces;
    }

    public Piece getHitPieces() {
        return this.hitPieces;
    }

    public void setHitPieces(Piece hitPieces) {
        this.hitPieces = hitPieces;
    }

    //https://stackoverflow.com/questions/10866551/android-drawing-shapes-programmatically-on-a-view-in-layout
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}

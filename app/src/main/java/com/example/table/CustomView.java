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

    //https://www.youtube.com/watch?v=sb9OEl4k9Dk
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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

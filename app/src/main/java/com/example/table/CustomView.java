package com.example.table;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {
    public List<Piece> pieces = new ArrayList<Piece>();
    public List<Triangle> triangles = new ArrayList<Triangle>();

    public int width;
    public int height;

    //https://www.youtube.com/watch?v=sb9OEl4k9Dk
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Log.i("screenView", "- Drawn");
        for(int i = 0; i < Triangle.TOTAL; i++){
            triangles.get(i).draw(canvas);
        }
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    public void initializeTriangles() {
        for(int i = 1; i <= Triangle.TOTAL; i++) {
            this.triangles.add(new Triangle(i, width, height));
        }
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

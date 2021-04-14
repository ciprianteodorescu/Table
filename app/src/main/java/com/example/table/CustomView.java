package com.example.table;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
    Piece piece1;

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


    //https://www.youtube.com/watch?v=sb9OEl4k9Dk
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        piece1 = new Piece(0, 0, Color.BLUE);
        piece1.draw(canvas);

    }

    public void changeColor() {
        piece1.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        invalidate();
    }

}

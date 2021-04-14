package com.example.table;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    View screenView;
    Piece piece1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide status bar: https://developer.android.com/training/system-ui/status#:~:text=Hide%20the%20Status%20Bar%20on%20Android%204.1%20and%20Higher,-You%20can%20hide&text=%2F%2F%20status%20bar%20is%20hidden%2C%20so%20hide%20that%20too%20if%20necessary.&text=View%20decorView%20%3D%20getWindow().,%2F%2F%20Hide%20the%20status%20bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        screenView = new CustomView(this);
        screenView = findViewById(R.id.screenView);

        screenView.invalidate();
    }

}
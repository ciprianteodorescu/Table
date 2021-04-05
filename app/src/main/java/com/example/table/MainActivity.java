package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView im1, im2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im1 = new ImageView(this);
        im2 = new ImageView(this);

        im1 = (ImageView) findViewById(R.id.imageView);
        im2 = (ImageView) findViewById(R.id.imageView1);
    }
}
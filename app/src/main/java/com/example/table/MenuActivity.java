package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.File;

public class MenuActivity extends AppCompatActivity {

    Button newGameBt;
    Button contGameBt;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //hide status bar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // hide status bar and nav bar after a short delay, or if the user interacts with the middle of the screen
        );

        newGameBt = new Button(this);
        newGameBt = findViewById(R.id.newGameBt);
        contGameBt = new Button(this);
        contGameBt = findViewById(R.id.continueGameBt);

        //if there is a saved game, activate continue game button
        SharedPreferences sharedPreferences = getSharedPreferences("PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("model", "");

        Log.i("jsonM", json);
        model = gson.fromJson(json, Model.class);


        contGameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model != null && !model.changeToRoll) {
                    Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    //TODO: make it transparent
                }
            }
        });

        newGameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                model = null;

                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
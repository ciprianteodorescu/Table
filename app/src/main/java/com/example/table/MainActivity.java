package com.example.table;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CustomView screenView;
    Button  rollButton;
    boolean changeToRoll = true;
    ImageView firstDice;
    ImageView secondDice;

    List<Piece> pieces = new ArrayList<Piece>();
    List<Triangle> triangles = new ArrayList<Triangle>();

    DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide status bar: https://developer.android.com/training/system-ui/status#41
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        screenView = new CustomView(this);
        screenView = findViewById(R.id.screenView);
        //get screen height and width
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenView.width = displayMetrics.widthPixels;
        screenView.height = displayMetrics.heightPixels;

        firstDice = new ImageView(this);
        firstDice = findViewById(R.id.firstDice);
        secondDice = new ImageView(this);
        secondDice = findViewById(R.id.secondDice);
        rollButton = new Button(this);
        rollButton = findViewById(R.id.roll_button);

        screenView.initializeTriangles();


        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeToRoll){
                    //TODO:start game
                    rollButton.setText("roll");
                    changeToRoll = false;
                }
                else{
                    //roll the dice
                    Random r = new Random();
                    int diceVal1 = 1 + r.nextInt(6);//generates random integer in range [0, 6)
                    Log.i("diceVal1 = ", String.valueOf(diceVal1));
                    switch (diceVal1){//change the dice image accordingly
                        case 1:
                            firstDice.setImageResource(R.drawable.dice1);
                            break;
                        case 2:
                            firstDice.setImageResource(R.drawable.dice2);
                            break;
                        case 3:
                            firstDice.setImageResource(R.drawable.dice3);
                            break;
                        case 4:
                            firstDice.setImageResource(R.drawable.dice4);
                            break;
                        case 5:
                            firstDice.setImageResource(R.drawable.dice5);
                            break;
                        case 6:
                            firstDice.setImageResource(R.drawable.dice6);
                            break;
                    }

                    int diceVal2 = 1 + r.nextInt(6);//generates random integer in range [0, 6)
                    switch (diceVal2){//change the dice image accordingly
                        case 1:
                            secondDice.setImageResource(R.drawable.dice1);
                            break;
                        case 2:
                            secondDice.setImageResource(R.drawable.dice2);
                            break;
                        case 3:
                            secondDice.setImageResource(R.drawable.dice3);
                            break;
                        case 4:
                            secondDice.setImageResource(R.drawable.dice4);
                            break;
                        case 5:
                            secondDice.setImageResource(R.drawable.dice5);
                            break;
                        case 6:
                            secondDice.setImageResource(R.drawable.dice6);
                            break;
                    }
                }

            }
        });


    }

}
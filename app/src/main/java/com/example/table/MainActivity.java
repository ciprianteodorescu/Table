package com.example.table;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
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
    ImageView thirdDice;
    ImageView fourthDice;

    //List<Piece> pieces = new ArrayList<Piece>();
    List<Triangle> triangles = new ArrayList<Triangle>();

    List<Player> players = new ArrayList<Player>();
    boolean playerToMove; // false = 0 or true = 1
    Player player; // player to move now
    int diceVal1, diceVal2;

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
        thirdDice = new ImageView(this);
        thirdDice = findViewById(R.id.thirdDice);
        fourthDice = new ImageView(this);
        fourthDice = findViewById(R.id.fourthDice);
        rollButton = new Button(this);
        rollButton = findViewById(R.id.roll_button);


        // TODO: save and restore game on close/open


        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeToRoll){
                    //TODO:start game
                    //puts board in initial position
                    triangles = screenView.initializeTriangles();
                    players.add(new Player(0)); // 0 = red
                    players.add(new Player(1)); // 1 = brown
                    screenView.setGameStarted(true);
                    screenView.invalidate(); // force redraw to show the now drawn pieces
                    playerToMove = true;

                    rollButton.setText("roll");
                    changeToRoll = false;
                }
                else{
                    //roll the dice
                    playerToMove = !playerToMove;
                    player = players.get(!playerToMove ? 0 : 1) ; //playerToMove==false => 0 otherwise => 1
                    Random r = new Random();
                    diceVal1 = 1 + r.nextInt(6);//generates random integer in range [0, 6)
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

                    diceVal2 = 1 + r.nextInt(6);//generates random integer in range [0, 6)
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

                    if(diceVal1 == diceVal2){
                        thirdDice.setImageDrawable(firstDice.getDrawable());
                        fourthDice.setImageDrawable(firstDice.getDrawable());
                        player.setRolledDouble(true);
                    }
                    else{
                        thirdDice.setImageResource(0);
                        fourthDice.setImageResource(0);
                    }

                    //rolled the dice
                    player.setRolledDice(true);
                    Log.i("PlayerColor = ", ""+player.getColor());


                }

            }
        });


    }
    public void calculatePossibleMoves(int pressedTr) {
        if(player.getColor() == 0) {
            if ((pressedTr + diceVal1) < Triangle.TOTAL && (pressedTr + diceVal1) > 0)
                if (triangles.get(pressedTr + diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + diceVal1).getPieceColor() == -1)
                    triangles.get(pressedTr + diceVal1).setDrawPossibleMoves(true);
            if ((pressedTr + diceVal2) < Triangle.TOTAL && (pressedTr + diceVal2) > 0)
                if (triangles.get(pressedTr + diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + diceVal2).getPieceColor() == -1)
                    triangles.get(pressedTr + diceVal2).setDrawPossibleMoves(true);
            if ((pressedTr + diceVal1 + diceVal2) < Triangle.TOTAL && (pressedTr + diceVal1 + diceVal2) > 0)
                if (triangles.get(pressedTr + diceVal1 + diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + diceVal1 + diceVal2).getPieceColor() == -1)
                    triangles.get(pressedTr + diceVal1 + diceVal2).setDrawPossibleMoves(true);
        }
        if(player.getColor() == 1) {
            if ((pressedTr - diceVal1) < Triangle.TOTAL && (pressedTr - diceVal1) > 0)
                if (triangles.get(pressedTr - diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - diceVal1).getPieceColor() == -1)
                    triangles.get(pressedTr - diceVal1).setDrawPossibleMoves(true);
            if ((pressedTr - diceVal2) < Triangle.TOTAL && (pressedTr - diceVal2) > 0)
                if (triangles.get(pressedTr - diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - diceVal2).getPieceColor() == -1)
                    triangles.get(pressedTr - diceVal2).setDrawPossibleMoves(true);
            if ((pressedTr - diceVal1 - diceVal2) < Triangle.TOTAL && (pressedTr - diceVal1 - diceVal2) > 0)
                if (triangles.get(pressedTr - diceVal1 - diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - diceVal1 - diceVal2).getPieceColor() == -1)
                    triangles.get(pressedTr - diceVal1 - diceVal2).setDrawPossibleMoves(true);
        }
    }

    public int chooseTriangle(int x, int y) {
        for(int i = 0; i < Triangle.TOTAL; i++) {
            if(triangles.get(i).getnPieces() > 0 && triangles.get(i).chooseTriangle(x, y))
                return i + 1;
        }
        return 0;
    }

    // https://stackoverflow.com/questions/3476779/how-to-get-the-touch-position-in-android
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.i("t = ", "" + chooseTriangle(x, y));
                chooseTriangle(x, y);
                //TODO: draw possible moves
                if(chooseTriangle(x, y) > 0 && (!playerToMove ? 0 : 1) == triangles.get(chooseTriangle(x, y) - 1).getPieceColor() && player != null) {
                    if(player.getRolledDice())
                        calculatePossibleMoves(chooseTriangle(x, y) - 1);
                }
                screenView.invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                for(int i = 0; i < Triangle.TOTAL; i++) {
                    triangles.get(i).setDrawPossibleMoves(false);
                }
                screenView.invalidate();
                break;
        }
        return false;
    }

    private void getPossibleMoves(int triangleNo) { // need to know which triangle was pressed

    }

}
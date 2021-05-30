package com.example.table;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CustomView screenView;
    Button  rollButton;
    boolean changeToRoll = true;
    boolean gameEnded = false;
    ImageView firstDice;
    ImageView secondDice;
    ImageView thirdDice;
    ImageView fourthDice;
    TextView playerToMoveTV;
    TextView turnEndTV;

    //List<Piece> pieces = new ArrayList<Piece>();
    List<Triangle> triangles = new ArrayList<Triangle>();
    Piece hrPieces; //hit or removed pieces

    List<Player> players = new ArrayList<Player>();
    boolean playerToMove; // false = 0 or true = 1
    Player player; // player to move now
    int diceVal1, diceVal2;

    DisplayMetrics displayMetrics = new DisplayMetrics();

    int pressedTriangleNr; //initially pressed triangle's number, 0<=>none pressed
    int droppedTriangleNr; //number of the triangle the piece is moved to, >0


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide status bar: https://developer.android.com/training/system-ui/status#41
        //https://stackoverflow.com/questions/46027325/auto-hide-status-barandroid
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // hide status bar and nav bar after a short delay, or if the user interacts with the middle of the screen
        );
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        screenView = new CustomView(this);
        screenView = findViewById(R.id.screenView);
        //get screen height and width
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        //Log.i("cutout", ""+getWindowManager().getDefaultDisplay().getCutout().getBoundingRectLeft().width());
        //https://developer.android.com/reference/android/view/DisplayCutout
        try {
            screenView.cutoutOffset = getWindowManager().getDefaultDisplay().getCutout().getBoundingRectLeft().width() + getWindowManager().getDefaultDisplay().getCutout().getBoundingRectRight().width();
        } catch (Exception e) {
            Log.i("cutout", "not present");
        }
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
        playerToMoveTV = new TextView(this);
        playerToMoveTV = findViewById(R.id.playerToMove);
        turnEndTV = new TextView(this);
        turnEndTV = findViewById(R.id.turnEnd);


        // TODO: save and restore game on close/open


        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeToRoll){
                    //start game
                    //puts board in initial position
                    triangles = screenView.initializeTriangles();
                    hrPieces = screenView.initializeHitPieces();
                    players.add(new Player(0)); // 0 = red
                    players.add(new Player(1)); // 1 = brown
                    screenView.setGameStarted(true);
                    screenView.invalidate(); // force redraw to show the now drawn pieces
                    playerToMove = true;

                    playerToMoveTV.setText(R.string.red_to_move);
                    rollButton.setText(R.string.roll);
                    changeToRoll = false;
                }
                else if(!gameEnded){
                    //roll the dice
                    turnEndTV.setText(null);
                    playerToMove = !playerToMove;
                    player = players.get(!playerToMove ? 0 : 1) ; //playerToMove==false => 0 otherwise => 1
                    //Log.i("player", String.valueOf(player.getColor()));
                    playerToMoveTV.setText(!playerToMove ? R.string.red_to_move : R.string.gray_to_move);
                    Random r = new Random();
                    diceVal1 = 1 + r.nextInt(6);//generates random integer in range [0, 6)
                    player.setAvailableMoves(diceVal1);
                    player.setAvailableDice(true, 1);
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
                    player.setAvailableMoves(player.getAvailableMoves() + diceVal2);
                    player.setAvailableDice(true, 2);
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
                        player.setAvailableDice(true, 3);
                        player.setAvailableDice(true, 4);
                        thirdDice.setImageDrawable(firstDice.getDrawable());
                        fourthDice.setImageDrawable(firstDice.getDrawable());
                        player.setAvailableMoves(player.getAvailableMoves() * 2);
                        player.setRolledDouble(true);
                    }
                    else{
                        player.setAvailableDice(false, 3);
                        player.setAvailableDice(false, 4);
                        thirdDice.setImageResource(0);
                        fourthDice.setImageResource(0);
                    }

                    //rolled the dice
                    player.setRolledDice(true);
                    //Log.i("PlayerColor = ", ""+player.getColor());


                }
            }
        });
    }

    public boolean canRemovePieces() {
        if(player.getColor() == 0) { //red ends on quarter 4 <=> triangles outside of [19, 24] must not have red pieces
            for(int i = 0; i < 18; i++) {
                if(triangles.get(i).getPieceColor() == 0 && triangles.get(i).getnPieces() > 0)
                    return false;
            }
        } else if(player.getColor() == 1) { //brown ends on quarter 1 <=> triangles outside of [1, 6] must not have brown pieces
            for(int i = 6; i < Triangle.TOTAL; i++) {
                //Log.i("trNo, nPieces, PieceColor", ""+(i+1)+", "+triangles.get(i).getnPieces() + ", " + triangles.get(i).getPieceColor());
                if(triangles.get(i).getPieceColor() == 1 && triangles.get(i).getnPieces() > 0)
                    return false;
            }
        }
        return true;
    }

    public boolean checkTurnEnd() {
        for(int pressedTr = 0; pressedTr < Triangle.TOTAL; pressedTr++) {
            if (player.getColor() == 0) {
                if (player.getAvailableDice()[0])
                    if ((pressedTr + diceVal1) < Triangle.TOTAL && (pressedTr + diceVal1) >= 0)
                        if (triangles.get(pressedTr + diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + diceVal1).getPieceColor() == -1  || triangles.get(pressedTr + diceVal1).getnPieces() == 1) {
                            return false;
                        }
                if (player.getAvailableDice()[1])
                    if ((pressedTr + diceVal2) < Triangle.TOTAL && (pressedTr + diceVal2) >= 0)
                        if (triangles.get(pressedTr + diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + diceVal2).getPieceColor() == -1 || triangles.get(pressedTr + diceVal2).getnPieces() == 1){
                            return false;
                        }
                if (player.getAvailableDice()[0] && player.getAvailableDice()[1])
                    if ((pressedTr + diceVal1 + diceVal2) < Triangle.TOTAL && (pressedTr + diceVal1 + diceVal2) >= 0)
                        if (triangles.get(pressedTr + diceVal1 + diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + diceVal1 + diceVal2).getPieceColor() == -1 || triangles.get(pressedTr + diceVal1 + diceVal2).getnPieces() == 1){
                            return false;
                        }
                if (player.getRolledDouble()) {
                    if (player.getAvailableDice()[0] && player.getAvailableDice()[1] && player.getAvailableDice()[2] && player.getAvailableDice()[3])
                        if ((pressedTr + 4 * diceVal1 ) < Triangle.TOTAL && (pressedTr + 4 * diceVal1) >= 0)
                            if (triangles.get(pressedTr + 4 * diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + 4 * diceVal1).getPieceColor() == -1 || triangles.get(pressedTr + 4 * diceVal1).getnPieces() == 1){
                                return false;
                            }
                    if (player.getAvailableDice()[0] && player.getAvailableDice()[1] && (player.getAvailableDice()[2] || player.getAvailableDice()[3]))
                        if ((pressedTr + 3 * diceVal1) < Triangle.TOTAL && (pressedTr + 3 * diceVal1) >= 0)
                            if (triangles.get(pressedTr + 3 * diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr + 3 * diceVal1).getPieceColor() == -1 || triangles.get(pressedTr + 3 * diceVal1).getnPieces() == 1){
                                return false;
                            }
                }
            }
            else if (player.getColor() == 1) {
                if (player.getAvailableDice()[0])
                    if ((pressedTr - diceVal1) < Triangle.TOTAL && (pressedTr - diceVal1) >= 0)
                        if (triangles.get(pressedTr - diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - diceVal1).getPieceColor() == -1  || triangles.get(pressedTr - diceVal1).getnPieces() == 1){
                            return false;
                        }
                if (player.getAvailableDice()[1])
                    if ((pressedTr - diceVal2) < Triangle.TOTAL && (pressedTr - diceVal2) >= 0)
                        if (triangles.get(pressedTr - diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - diceVal2).getPieceColor() == -1 || triangles.get(pressedTr - diceVal2).getnPieces() == 1){
                            return false;
                        }
                if (player.getAvailableDice()[0] && player.getAvailableDice()[1])
                    if ((pressedTr - diceVal1 - diceVal2) < Triangle.TOTAL && (pressedTr - diceVal1 - diceVal2) >= 0)
                        if (triangles.get(pressedTr - diceVal1 - diceVal2).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - diceVal1 - diceVal2).getPieceColor() == -1 || triangles.get(pressedTr - diceVal1 - diceVal2).getnPieces() == 1){
                            return false;
                        }
                if (player.getRolledDouble()) {
                    if (player.getAvailableDice()[0] && player.getAvailableDice()[1] && player.getAvailableDice()[2] && player.getAvailableDice()[3])
                        if ((pressedTr - 4 * diceVal1) < Triangle.TOTAL && (pressedTr - 4 * diceVal1) >= 0)
                            if (triangles.get(pressedTr - 4 * diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - 4 * diceVal1).getPieceColor() == -1 || triangles.get(pressedTr - 4 * diceVal1).getnPieces() == 1){
                                return false;
                            }
                    if (player.getAvailableDice()[0] && player.getAvailableDice()[1] && (player.getAvailableDice()[2] || player.getAvailableDice()[3]))
                        if ((pressedTr - 3 * diceVal1) < Triangle.TOTAL && (pressedTr - 3 * diceVal1) >= 0)
                            if (triangles.get(pressedTr - 3 * diceVal1).getPieceColor() == triangles.get(pressedTr).getPieceColor() || triangles.get(pressedTr - 3 * diceVal1).getPieceColor() == -1 || triangles.get(pressedTr - 3 * diceVal1).getnPieces() == 1){
                                return false;
                            }
                }
            }
        }
        return true;
    }

    public void calculatePossibleMoves(int pressedTr) { //pressedTr=triangle index in list
        if(player.getColor() == 0) {
            if (player.getAvailableDice()[0]) {
                if ((pressedTr + diceVal1) < Triangle.TOTAL && (pressedTr + diceVal1) >= 0)
                    if (triangles.get(pressedTr + diceVal1).getPieceColor() == player.getColor() || triangles.get(pressedTr + diceVal1).getPieceColor() == -1 || triangles.get(pressedTr + diceVal1).getnPieces() == 1)
                        triangles.get(pressedTr + diceVal1).setDrawPossibleMoves(true);
                if(pressedTr + diceVal1 >= Triangle.TOTAL && player.getCanRemovePieces()) {
                    hrPieces.setDrawRedPossible(true);
                }
            }
            if(player.getAvailableDice()[1]) {
                if ((pressedTr + diceVal2) < Triangle.TOTAL && (pressedTr + diceVal2) >= 0)
                    if (triangles.get(pressedTr + diceVal2).getPieceColor() == player.getColor() || triangles.get(pressedTr + diceVal2).getPieceColor() == -1 || triangles.get(pressedTr + diceVal2).getnPieces() == 1)
                        triangles.get(pressedTr + diceVal2).setDrawPossibleMoves(true);
                if(pressedTr + diceVal2 >= Triangle.TOTAL && player.getCanRemovePieces()) {
                    hrPieces.setDrawRedPossible(true);
                }
            }
            if(player.getAvailableDice()[0] && player.getAvailableDice()[1]) {
                if ((pressedTr + diceVal1 + diceVal2) < Triangle.TOTAL && (pressedTr + diceVal1 + diceVal2) >= 0)
                    if (triangles.get(pressedTr + diceVal1 + diceVal2).getPieceColor() == player.getColor() || triangles.get(pressedTr + diceVal1 + diceVal2).getPieceColor() == -1 || triangles.get(pressedTr + diceVal1 + diceVal2).getnPieces() == 1)
                        triangles.get(pressedTr + diceVal1 + diceVal2).setDrawPossibleMoves(true);
                if(pressedTr + diceVal1 + diceVal2 >= Triangle.TOTAL && player.getCanRemovePieces()) {
                    hrPieces.setDrawRedPossible(true);
                }
            }
            if (player.getRolledDouble()){
                if(player.getAvailableDice()[0] && player.getAvailableDice()[1] && player.getAvailableDice()[2] && player.getAvailableDice()[3]) {
                    if((pressedTr + 4 * diceVal1) < Triangle.TOTAL && (pressedTr + 4 * diceVal1) >= 0)
                        if (triangles.get(pressedTr + 4 * diceVal1).getPieceColor() == player.getColor() || triangles.get(pressedTr + 4 * diceVal1).getPieceColor() == -1 || triangles.get(pressedTr + 4 * diceVal1).getnPieces() == 1)
                            triangles.get(pressedTr + 4 * diceVal1).setDrawPossibleMoves(true);
                    if(pressedTr + 4 * diceVal1 >= Triangle.TOTAL && player.getCanRemovePieces()) {
                        hrPieces.setDrawRedPossible(true);
                    }
                }
                if(player.getAvailableDice()[0] && player.getAvailableDice()[1] && (player.getAvailableDice()[2] || player.getAvailableDice()[3])) {
                    if((pressedTr + 3 * diceVal1) < Triangle.TOTAL && (pressedTr + 3 * diceVal1) >= 0)
                        if (triangles.get(pressedTr + 3 * diceVal1).getPieceColor() == player.getColor() || triangles.get(pressedTr + 3 * diceVal1).getPieceColor() == -1  || triangles.get(pressedTr + 3 * diceVal1).getnPieces() == 1)
                            triangles.get(pressedTr + 3 * diceVal1).setDrawPossibleMoves(true);
                    if(pressedTr + 3 * diceVal1 >= Triangle.TOTAL && player.getCanRemovePieces()) {
                        hrPieces.setDrawRedPossible(true);
                    }
                }
            }
        }
        if(player.getColor() == 1) {
            if (player.getAvailableDice()[0]) {
                if ((pressedTr - diceVal1) < Triangle.TOTAL && (pressedTr - diceVal1) >= 0)
                    if (triangles.get(pressedTr - diceVal1).getPieceColor() == player.getColor() || triangles.get(pressedTr - diceVal1).getPieceColor() == -1 || triangles.get(pressedTr - diceVal1).getnPieces() == 1)
                        triangles.get(pressedTr - diceVal1).setDrawPossibleMoves(true);
                if(pressedTr - diceVal1 < 0 && player.getCanRemovePieces()) {
                    hrPieces.setDrawBrownPossible(true);
                }
            }
            if(player.getAvailableDice()[1]) {
                if ((pressedTr - diceVal2) < Triangle.TOTAL && (pressedTr - diceVal2) >= 0)
                    if (triangles.get(pressedTr - diceVal2).getPieceColor() == player.getColor() || triangles.get(pressedTr - diceVal2).getPieceColor() == -1 || triangles.get(pressedTr - diceVal2).getnPieces() == 1)
                        triangles.get(pressedTr - diceVal2).setDrawPossibleMoves(true);
                if(pressedTr - diceVal2 < 0 && player.getCanRemovePieces()) {
                    hrPieces.setDrawBrownPossible(true);
                }
            }
            if(player.getAvailableDice()[0] && player.getAvailableDice()[1]) {
                if ((pressedTr - diceVal1 - diceVal2) < Triangle.TOTAL && (pressedTr - diceVal1 - diceVal2) >= 0)
                    if (triangles.get(pressedTr - diceVal1 - diceVal2).getPieceColor() == player.getColor() || triangles.get(pressedTr - diceVal1 - diceVal2).getPieceColor() == -1 || triangles.get(pressedTr - diceVal1 - diceVal2).getnPieces() == 1)
                        triangles.get(pressedTr - diceVal1 - diceVal2).setDrawPossibleMoves(true);
                if(pressedTr - diceVal1 - diceVal2 < 0 && player.getCanRemovePieces()) {
                    hrPieces.setDrawBrownPossible(true);
                }
            }
            if (player.getRolledDouble()){
                if(player.getAvailableDice()[0] && player.getAvailableDice()[1] && player.getAvailableDice()[2] && player.getAvailableDice()[3]) {
                    if((pressedTr - 4 * diceVal1) < Triangle.TOTAL && (pressedTr - 4 * diceVal1) >= 0)
                        if (triangles.get(pressedTr - 4 * diceVal1).getPieceColor() == player.getColor() || triangles.get(pressedTr - 4 * diceVal1).getPieceColor() == -1 || triangles.get(pressedTr - 4 * diceVal1).getnPieces() == 1)
                            triangles.get(pressedTr - 4 * diceVal1).setDrawPossibleMoves(true);
                    if(pressedTr - 4 * diceVal1 < 0 && player.getCanRemovePieces()) {
                        hrPieces.setDrawBrownPossible(true);
                    }
                }
                if(player.getAvailableDice()[0] && player.getAvailableDice()[1] && (player.getAvailableDice()[2] || player.getAvailableDice()[3])) {
                    if((pressedTr - 3 * diceVal1) < Triangle.TOTAL && (pressedTr - 3 * diceVal1) >= 0)
                        if (triangles.get(pressedTr - 3 * diceVal1).getPieceColor() == player.getColor() || triangles.get(pressedTr - 3 * diceVal1).getPieceColor() == -1 || triangles.get(pressedTr - 3 * diceVal1).getnPieces() == 1)
                            triangles.get(pressedTr - 3 * diceVal1).setDrawPossibleMoves(true);
                    if(pressedTr - 3 * diceVal1 < 0 && player.getCanRemovePieces()) {
                        hrPieces.setDrawBrownPossible(true);
                    }
                }
            }
        }
    }

    public int chooseTriangle(int x, int y) {
        for(int i = 0; i < Triangle.TOTAL; i++) {
            if(triangles.get(i).getnPieces() > 0 && triangles.get(i).chooseTriangle(x, y)) {
                return triangles.get(i).getTrNo();
            }
        }
        return 0;
    }

    public boolean isBeingRemoved(int x, int y, int color) {
        //final float dist2R = (float) 0.026; //distance between 2 removed pieces <=> height of a drawed removed piece
        final float xStart = (float) (screenView.width * 0.956);
        final float xEnd = (float) (screenView.width * 0.99);
        final float yRedStart = (float) (screenView.height * 0.06);
        final float yRedEnd = (float) (screenView.height * 0.45);
        final float yBrownStart = (float) (screenView.height * 0.55);
        final float yBrownEnd = (float) (screenView.height * 0.94);


        if(color == 0) {//red
            if(xStart <= x && x <= xEnd && yRedStart <= y && y <= yRedEnd) {
                Log.i("removed", x + " " + y + " " + color);
                return true;
            }
        }
        else if(color == 1) {//brown
            if(xStart <= x && x <= xEnd && yBrownStart <= y && y <= yBrownEnd) {
                Log.i("removed", x + " " + y + " " + color);
                return true;
            }
        }
        return false;
    }

    // https://stackoverflow.com/questions/3476779/how-to-get-the-touch-position-in-android
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(!changeToRoll && player != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //computes and draws possible moves
                    //Log.i("t = ", "" + chooseTriangle(x, y) + ", " + pressedTriangleNr);
                    player.setCanRemovePieces(canRemovePieces());
                    pressedTriangleNr = chooseTriangle(x, y);
                    if (pressedTriangleNr > 0 && (!playerToMove ? 0 : 1) == triangles.get(pressedTriangleNr - 1).getPieceColor()) {
                        if (player.getRolledDice()) {
                            calculatePossibleMoves(pressedTriangleNr - 1);
                            try {
                                triangles.get(pressedTriangleNr - 1).setDrawMovingPiece(true);
                                triangles.get(pressedTriangleNr - 1).setnPieces(triangles.get(pressedTriangleNr - 1).getnPieces() - 1);
                            } catch (Exception e) {
                                Log.i("t = ", "" + pressedTriangleNr + " has no pieces");
                            }
                        }
                    } else if(screenView.width * Triangle.xQ2End <= x && x <= screenView.width * Triangle.xQ1Start) { //pressed hit piece
                        if(screenView.height * 0.5 < y && player.getColor() == 0 && hrPieces.getRedHit() > 0) { //pressed red hit piece (in upper half of screen
                            Log.i("pressed", "red hit piece");
                            pressedTriangleNr = -1;
                            calculatePossibleMoves(-1);
                        }
                        else if(screenView.height * 0.5 > y && player.getColor() == 1 && hrPieces.getBrownHit() > 0) { //pressed brown hit piece (in lower half of screen
                            Log.i("pressed", "brown hit piece");
                            pressedTriangleNr = -2;
                            calculatePossibleMoves(24);
                        }
                    } else {
                        pressedTriangleNr = 0;
                    }
                    //Log.i("t = ", "" + pressedTriangleNr);
                    screenView.invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //draws draggable piece
                    try {
                        triangles.get(pressedTriangleNr - 1).setMoving(x, y);
                    } catch (Exception e) {
                        if(pressedTriangleNr == -1) { //draw red hit piece that is being dragged
                            Log.i("t = ", "" + pressedTriangleNr);
                            hrPieces.setDrawMovingRedHit(true, x, y);
                        }
                        else if (pressedTriangleNr == -2) {
                            Log.i("t = ", "" + pressedTriangleNr);
                            hrPieces.setDrawMovingBrownHit(true, x, y);
                        }
                        else
                            Log.i("t = ", "" + pressedTriangleNr + "had no pieces");
                    }
                    screenView.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    //check if move is valid
                    boolean isValidMove = false;

                    //adds piece on valid triangle and draws it on placed triangle
                    if (pressedTriangleNr > 0) {
                        for (int i = 0; i < Triangle.TOTAL; i++) {
                            if (triangles.get(i).isValidMove(x, y, triangles.get(pressedTriangleNr - 1).getPieceColor())) {
                                droppedTriangleNr = i + 1;
                                if((triangles.get(i).getPieceColor() == 1 || triangles.get(i).getPieceColor() == 0) && triangles.get(i).getPieceColor() != player.getColor() && triangles.get(i).getnPieces() == 1) {
                                    //if there is only one piece belonging to the other player, it can be hit
                                    //add the hit piece to the opponent
                                    //current player = brown => add hit piece to red
                                    //current player = red => add hit piece to brown
                                    hrPieces.addHitPieceToPlayer(players.get(playerToMove ? 0 : 1).getColor());
                                    players.get(playerToMove ? 0 : 1).setCanRemovePieces(false); //opponent can no longer remove pieces
                                } else {
                                    triangles.get(i).setnPieces(triangles.get(i).getnPieces() + 1);
                                }
                                triangles.get(i).setPieceColor(player.getColor());
                                isValidMove = true;
                                break;
                            }
                        }
                        //undraws dragged piece
                        triangles.get(pressedTriangleNr - 1).setMoving(- 2 * Triangle.RADIUS, - 2 * Triangle.RADIUS);
                        triangles.get(pressedTriangleNr - 1).setDrawMovingPiece(false);
                        if(triangles.get(pressedTriangleNr - 1).getnPieces() == 0)
                            triangles.get(pressedTriangleNr - 1).setPieceColor(-1);

                        //removes piece
                        boolean removedPiece = false;
                        if(isBeingRemoved(x, y, player.getColor()) && player.getCanRemovePieces() && !isValidMove) {
                            //isValidMove will be false in this case so we need to remove a die accordingly
                            //find the smallest die >= (25 - pressedTriangleNr) for red
                            //or >= (pressedTriangleNr) for brown

                            int move;
                            if(player.getColor() == 0) //red player
                                move = 25 - pressedTriangleNr;
                            else
                                move = pressedTriangleNr;

                            if(player.getRolledDouble()) {
                                if(player.getAvailableDice()[3] && diceVal1 >= move) { //die 4
                                    removedPiece = true;
                                    hrPieces.incRemovedPiece(player.getColor());
                                    player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                    player.setAvailableMoves(player.getAvailableMoves() - diceVal1);
                                    player.setAvailableDice(false, 4);
                                    fourthDice.setImageResource(0);
                                }
                                else if(player.getAvailableDice()[2] && diceVal1 >= move) { //die 3
                                    removedPiece = true;
                                    hrPieces.incRemovedPiece(player.getColor());
                                    player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                    player.setAvailableMoves(player.getAvailableMoves() - diceVal1);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                }
                                else if(player.getAvailableDice()[3] && 2 * diceVal1 >= move) { //dice 4, 3
                                    removedPiece = true;
                                    hrPieces.incRemovedPiece(player.getColor());
                                    player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                    player.setAvailableMoves(player.getAvailableMoves() - 2 * diceVal1);
                                    player.setAvailableDice(false, 4);
                                    fourthDice.setImageResource(0);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);

                                    player.setRolledDouble(false);
                                }
                                else if(!player.getAvailableDice()[3] && 2 * diceVal1 >= move) { //dice 3, 2
                                    removedPiece = true;
                                    hrPieces.incRemovedPiece(player.getColor());
                                    player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                    player.setAvailableMoves(player.getAvailableMoves() - 2 * diceVal1);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                    player.setAvailableDice(false, 2);
                                    secondDice.setImageResource(0);

                                    player.setRolledDouble(false);
                                }
                                else if(player.getAvailableDice()[3] && 3 * diceVal1 >= move) { //dice 4, 3, 2
                                    removedPiece = true;
                                    hrPieces.incRemovedPiece(player.getColor());
                                    player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                    player.setAvailableMoves(player.getAvailableMoves() - 3 * diceVal1);
                                    player.setAvailableDice(false, 4);
                                    fourthDice.setImageResource(0);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                    player.setAvailableDice(false, 2);
                                    secondDice.setImageResource(0);

                                    player.setRolledDouble(false);
                                }
                                else if(player.getAvailableDice()[3] && 3 * diceVal1 >= move) { //dice 3, 2, 1
                                    removedPiece = true;
                                    hrPieces.incRemovedPiece(player.getColor());
                                    player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                    player.setAvailableMoves(player.getAvailableMoves() - 3 * diceVal1);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                    player.setAvailableDice(false, 2);
                                    secondDice.setImageResource(0);
                                    player.setAvailableDice(false, 1);
                                    firstDice.setImageResource(0);

                                    player.setRolledDouble(false);
                                }
                                else if(player.getAvailableDice()[3] && player.getAvailableDice()[2] && 4 * diceVal1 >= move) { //dice 4, 3, 2, 1
                                    removedPiece = true;
                                    hrPieces.incRemovedPiece(player.getColor());
                                    player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                    player.setAvailableMoves(player.getAvailableMoves() - 4 * diceVal1);
                                    player.setAvailableDice(false, 4);
                                    fourthDice.setImageResource(0);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                    player.setAvailableDice(false, 2);
                                    secondDice.setImageResource(0);
                                    player.setAvailableDice(false, 1);
                                    firstDice.setImageResource(0);

                                    player.setRolledDouble(false);
                                }
                            }
                            else if(player.getAvailableDice()[0] && diceVal1 >= move) { //die 1
                                removedPiece = true;
                                hrPieces.incRemovedPiece(player.getColor());
                                player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                player.setAvailableMoves(player.getAvailableMoves() - diceVal1);
                                player.setAvailableDice(false, 1);
                                firstDice.setImageResource(0);
                            }
                            else if(player.getAvailableDice()[1] && diceVal2 >= move) { //die 2
                                removedPiece = true;
                                hrPieces.incRemovedPiece(player.getColor());
                                player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                player.setAvailableMoves(player.getAvailableMoves() - diceVal2);
                                player.setAvailableDice(false, 2);
                                secondDice.setImageResource(0);
                            }
                            else if(player.getAvailableDice()[0] && player.getAvailableDice()[1] && diceVal1 + diceVal2 >= move) { //die 1, 2
                                removedPiece = true;
                                hrPieces.incRemovedPiece(player.getColor());
                                player.setPiecesOnBoard(player.getPiecesOnBoard() - 1);

                                player.setAvailableMoves(player.getAvailableMoves() - diceVal1 - diceVal2);
                                player.setAvailableDice(false, 1);
                                firstDice.setImageResource(0);
                                player.setAvailableDice(false, 2);
                                secondDice.setImageResource(0);
                            }

                            //end game
                            if(player.getPiecesOnBoard() == 0){
                                new AlertDialog.Builder(this) //https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
                                        .setTitle(R.string.congratulations)
                                        .setMessage(player.getColor() == 0 ? R.string.red_won : R.string.brown_won)
                                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //restart activity
                                        //https://stackoverflow.com/questions/3053761/reload-activity-in-android
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //exit
                                        finishAndRemoveTask();
                                    }
                                })
                                .show();
                                gameEnded = true;
                            }
                        }

                        //draws dragged piece back to its initial triangle
                        if (!removedPiece && !isValidMove && (player.getColor() == triangles.get(pressedTriangleNr - 1).getPieceColor() || triangles.get(pressedTriangleNr - 1).getPieceColor() == -1) && (!checkTurnEnd() || player.getAvailableMoves() != 0)) {
                            triangles.get(pressedTriangleNr - 1).setnPieces(triangles.get(pressedTriangleNr - 1).getnPieces() + 1);
                            triangles.get(pressedTriangleNr - 1).setPieceColor(player.getColor());
                        }
                    }
                    else if(pressedTriangleNr == -1) {
                        //undraw dragged hit piece
                        hrPieces.setDrawMovingRedHit(false);
                        for (int i = 0; i < Triangle.TOTAL; i++) {
                            if (triangles.get(i).isValidMove(x, y, player.getColor())) {
                                droppedTriangleNr = i + 1;
                                if((triangles.get(i).getPieceColor() == 1 || triangles.get(i).getPieceColor() == 0) && triangles.get(i).getPieceColor() != player.getColor() && triangles.get(i).getnPieces() == 1) {
                                    //if there is only one piece belonging to the other player, it can be hit
                                    //add the hit piece to the opponent
                                    //current player = brown => add hit piece to red
                                    //current player = red => add hit piece to brown
                                    hrPieces.addHitPieceToPlayer(players.get(playerToMove ? 0 : 1).getColor());
                                    players.get(playerToMove ? 0 : 1).setCanRemovePieces(false); //opponent can no longer remove pieces
                                } else {
                                    triangles.get(i).setnPieces(triangles.get(i).getnPieces() + 1);
                                }
                                triangles.get(i).setPieceColor(player.getColor());
                                pressedTriangleNr = 0;
                                isValidMove = true;
                                break;
                            }
                        }
                        if(isValidMove) {
                            hrPieces.removeHitPieceFromPlayer(player.getColor());
                        }
                    }
                    else if(pressedTriangleNr == -2) {
                        //undraw dragged hit piece
                        hrPieces.setDrawMovingBrownHit(false);
                        for (int i = 0; i < Triangle.TOTAL; i++) {
                            if (triangles.get(i).isValidMove(x, y, player.getColor())) {
                                droppedTriangleNr = i + 1;
                                if((triangles.get(i).getPieceColor() == 1 || triangles.get(i).getPieceColor() == 0) && triangles.get(i).getPieceColor() != player.getColor() && triangles.get(i).getnPieces() == 1) {
                                    //if there is only one piece belonging to the other player, it can be hit
                                    //add the hit piece to the opponent
                                    //current player = brown => add hit piece to red
                                    //current player = red => add hit piece to brown
                                    hrPieces.addHitPieceToPlayer(players.get(playerToMove ? 0 : 1).getColor());
                                    players.get(playerToMove ? 0 : 1).setCanRemovePieces(false); //opponent can no longer remove pieces
                                } else {
                                    triangles.get(i).setnPieces(triangles.get(i).getnPieces() + 1);
                                }
                                triangles.get(i).setPieceColor(player.getColor());
                                pressedTriangleNr = 25;
                                isValidMove = true;
                                break;
                            }
                        }
                        if(isValidMove) {
                            droppedTriangleNr = chooseTriangle(x, y);
                            hrPieces.removeHitPieceFromPlayer(player.getColor());
                        }
                    }

                    //remove die based on move
                    if(isValidMove) {
                        if(player.getRolledDouble()){
                            if(Math.abs(droppedTriangleNr - pressedTriangleNr) == diceVal1) {
                                player.setAvailableMoves(player.getAvailableMoves() - diceVal1);
                                if(player.getAvailableDice()[3]) {
                                    player.setAvailableDice(false, 4);
                                    fourthDice.setImageResource(0);
                                }
                                else if(player.getAvailableDice()[2]) {
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                    if (!player.getAvailableDice()[3]) //if the other double die(4-1=index 3) was used too, return to usual non-double state
                                        player.setRolledDouble(false);
                                }
                            }
                            else if(Math.abs(droppedTriangleNr - pressedTriangleNr) == 2 * diceVal1) {
                                player.setAvailableMoves(player.getAvailableMoves() - 2 * diceVal1);
                                if(player.getAvailableDice()[3] && player.getAvailableDice()[2]) { //dice 3, 4
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                    player.setAvailableDice(false, 4);
                                    fourthDice.setImageResource(0);
                                } else { //dice 2, 3
                                    player.setAvailableDice(false, 2);
                                    secondDice.setImageResource(0);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                }
                                player.setRolledDouble(false);
                            }
                            else if(Math.abs(droppedTriangleNr - pressedTriangleNr) == 3 * diceVal1) {
                                player.setAvailableMoves(player.getAvailableMoves() - 3 * diceVal1);
                                if(player.getAvailableDice()[3] && player.getAvailableDice()[2]) { //dice 2, 3, 4
                                    player.setAvailableDice(false, 2);
                                    secondDice.setImageResource(0);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                    player.setAvailableDice(false, 4);
                                    fourthDice.setImageResource(0);
                                } else { //dice 1, 2, 3
                                    player.setAvailableDice(false, 1);
                                    firstDice.setImageResource(0);
                                    player.setAvailableDice(false, 2);
                                    secondDice.setImageResource(0);
                                    player.setAvailableDice(false, 3);
                                    thirdDice.setImageResource(0);
                                }
                                player.setRolledDouble(false);
                            }
                            else if(Math.abs(droppedTriangleNr - pressedTriangleNr) == 4 * diceVal1) {
                                player.setAvailableMoves(player.getAvailableMoves() - 4 * diceVal1);
                                player.setAvailableDice(false,1);
                                firstDice.setImageResource(0);
                                player.setAvailableDice(false,2);
                                secondDice.setImageResource(0);
                                player.setAvailableDice(false,3);
                                thirdDice.setImageResource(0);
                                player.setAvailableDice(false,4);
                                fourthDice.setImageResource(0);
                                player.setRolledDouble(false);
                            }
                        }
                        else if(Math.abs(droppedTriangleNr - pressedTriangleNr) == diceVal1) {
                            player.setAvailableMoves(player.getAvailableMoves() - diceVal1);
                            if (player.getAvailableMoves() <= 2 * diceVal1 && player.getRolledDouble())
                                player.setRolledDouble(false);
                            if (player.getAvailableDice()[0]) {
                                player.setAvailableDice(false, 1);
                                firstDice.setImageResource(0);
                            } else {
                                player.setAvailableDice(false, 2);
                                secondDice.setImageResource(0);
                            }
                        } else if(Math.abs(droppedTriangleNr - pressedTriangleNr) == diceVal2) {
                            player.setAvailableMoves(player.getAvailableMoves() - diceVal2);
                            player.setAvailableDice(false,2);
                            secondDice.setImageResource(0);
                        } else if(Math.abs(droppedTriangleNr - pressedTriangleNr) == (diceVal1 + diceVal2)) {
                            player.setAvailableMoves(player.getAvailableMoves() - diceVal1 - diceVal2);
                            player.setAvailableDice(false,1);
                            firstDice.setImageResource(0);
                            player.setAvailableDice(false,2);
                            secondDice.setImageResource(0);
                        }
                    }

                    if(player.getAvailableMoves() == 0 || checkTurnEnd()) {
                        turnEndTV.setText(R.string.turn_end);
                        player.setAvailableMoves(0);
                        player.setRolledDouble(false);
                        player.setRolledDice(false);
                    }

                    droppedTriangleNr = 0;
                    pressedTriangleNr = 0;

                    //undraws all possible moves
                    for (int i = 0; i < Triangle.TOTAL; i++) {
                        triangles.get(i).setDrawPossibleMoves(false);
                    }
                    hrPieces.setDrawRedPossible(false);
                    hrPieces.setDrawBrownPossible(false);
                    screenView.invalidate();
                    break;
            }
        }
        return false;
    }


}
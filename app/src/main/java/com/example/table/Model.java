package com.example.table;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {

    boolean changeToRoll;
    boolean gameEnded;

    List<Triangle> triangles;
    Piece hrPieces; //hit or removed pieces

    List<Player> players;
    boolean playerToMove; // false = 0 or true = 1
    Player player; // player to move now
    int diceVal1, diceVal2;


    public Model(boolean changeToRoll, boolean gameEnded, List<Triangle> triangles, Piece hrPieces, List<Player> players, boolean playerToMove,
                 Player player, int diceVal1, int diceVal2) {
        this.changeToRoll = changeToRoll;
        this.gameEnded = gameEnded;
        Log.i("modelConstructor", "" + triangles.size());
        this.triangles = new ArrayList<>();
        for(int i = 0; i < triangles.size(); i++) {
            this.triangles.add(new Triangle(triangles.get(i)));
        }
        this.hrPieces = new Piece(hrPieces);
        this.players = new ArrayList<>();
        for(int i = 0; i < players.size(); i++) {
            this.players.add(new Player(players.get(i)));
        }
        this.playerToMove = playerToMove;
        this.player = player;
        this.diceVal1 = diceVal1;
        this.diceVal2 = diceVal2;
    }
}

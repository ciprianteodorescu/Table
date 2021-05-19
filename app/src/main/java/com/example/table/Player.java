package com.example.table;

public class Player {

    private int color; //0=red, 1=brown
    private int piecesOnBoard;
    private int hitPieces;
    private boolean rolledDice;
    private boolean rolledDouble;


    public Player(int color) {
        this.color = color;
        this.piecesOnBoard = 15;
        this.hitPieces = 0;
        this.rolledDice = false;
        this.rolledDouble = false;
    }

    public void setPlayer(Player player) {
        this.piecesOnBoard = player.piecesOnBoard;
        this.hitPieces = player.hitPieces;
    }

    public void setColor(int color) {//this is not needed
        this.color = color;
    }

    public void setPiecesOnBoard(int piecesOnBoard) {
        this.piecesOnBoard = piecesOnBoard;
    }

    public void setHitPieces(int hitPieces) {
        this.hitPieces = hitPieces;
    }

    public void setRolledDice(boolean rolledDice) {
        this.rolledDice = rolledDice;
    }

    public void setRolledDouble(boolean rolledValue) {
        this.rolledDouble = rolledValue;
    }

    public int getColor() {
        return color;
    }

    public int getPiecesOnBoard() {
        return piecesOnBoard;
    }

    public int getHitPieces() {
        return hitPieces;
    }

    public boolean getRolledDice() {
        return rolledDice;
    }

    public boolean getRolledDouble() {
        return rolledDouble;
    }


}

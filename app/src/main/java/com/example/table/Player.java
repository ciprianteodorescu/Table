package com.example.table;

public class Player {

    private int color; //0=red, 1=brown
    private int piecesOnBoard;
    private int hitPieces;
    private int availableMoves;
    private boolean[] availableDice = new boolean[4];
    private boolean rolledDice;
    private boolean rolledDouble;
    private boolean canRemovePieces;


    public Player(int color) {
        this.color = color;
        this.piecesOnBoard = 15;
        this.hitPieces = 0;
        this.availableMoves = 0;
        this.availableDice[0] = false;
        this.availableDice[1] = false;
        this.availableDice[2] = false;
        this.availableDice[3] = false;
        this.rolledDice = false;
        this.rolledDouble = false;
        this.canRemovePieces = false;
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

    public int getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(int availableMoves) {
        this.availableMoves = availableMoves;
    }

    public boolean[] getAvailableDice() {
        return availableDice;
    }

    public boolean isTurnFinished() {
        return (!availableDice[0] && !availableDice[1] && !availableDice[2] && !availableDice[3]);
    }

    public void setAvailableDice(boolean[] availableDice) {
        this.availableDice = availableDice;
    }

    public void setAvailableDice(boolean availableDie, int index) {
        this.availableDice[index - 1] = availableDie;
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

    public boolean getCanRemovePieces() {
        return canRemovePieces;
    }

    public void setCanRemovePieces(boolean canRemovePieces) {
        this.canRemovePieces = canRemovePieces;
    }
}

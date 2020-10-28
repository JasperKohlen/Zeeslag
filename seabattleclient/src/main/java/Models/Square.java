package Models;

import enums.SquareState;

public class Square {
    private SquareState squareState;
    private int x;
    private int y;
    private boolean containsShip;

    public boolean isContainsShip() {
        return containsShip;
    }

    public Square(SquareState squareState, int x, int y, boolean containsShip) {
        this.squareState = squareState;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSquareState(SquareState squareState) {
        this.squareState = squareState;
    }

    public SquareState getSquareState(){
        return this.squareState;
    }
}

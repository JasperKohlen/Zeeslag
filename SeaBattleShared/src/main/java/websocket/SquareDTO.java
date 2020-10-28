package websocket;

import enums.SquareState;

public class SquareDTO {

    private SquareState tileState;
    private int x;
    private int y;
    private boolean containsShip;

    public SquareDTO(SquareState tileState, int x, int y, boolean containsShip) {
        this.tileState = tileState;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTileState(SquareState tileState) {
        this.tileState = tileState;
    }

    public SquareState getTileState(){
        return this.tileState;
    }
}

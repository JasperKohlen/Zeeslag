package Models;

import enums.ShipType;
import enums.SquareState;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private ShipType type;
    private int x;
    private int y;
    private boolean horizontal;
    private int count = 0;
    private int length = 0;
    private List<Square> squares;
    private boolean isPlaced;

    public ShipType getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public int getLength() {
        return length;
    }

    public boolean getIsPlaced() { return isPlaced; }

    public ShipType getShipType() { return type; }

    public void setIsPlaced(boolean isPlaced, int x, int y, boolean horizontal) {
        this.isPlaced = isPlaced;
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public Ship(ShipType type) {
        this.type = type;
        this.squares = new ArrayList<Square>();
    }

    public Ship(ShipType type, int bowX, int bowY, boolean horizontal) {
        this.type = type;
        this.x = bowX;
        this.y = bowY;
        this.horizontal = horizontal;
    }

    public void destroyCoordinate(int x, int y){
        count++;
        if(count == length){
            destroyShip();
            return;
        }
        for(Square s : squares){
            if(s.getX() == x && s.getY() == y){
                s.setSquareState(SquareState.SHOTHIT);
            }
        }
    }

    private void destroyShip(){
        for(Square s: squares){
            s.setSquareState(SquareState.SHIPSUNK);
        }
    }

    public boolean shipIsDestroyed(){
        return squares.get(0).getSquareState() == SquareState.SHIPSUNK;
    }

    public void addSquare(Square square){
        this.squares.add(square);
        length++;
    }

    public void removeFromBoard(int x, int y){
        squares.clear();
        setIsPlaced(false, x, y, false);
    }

    public void addSquares() {
        if(horizontal) {
            for(int i = 0; i < this.getShipType().length; i++) {
                Square s = new Square(SquareState.SHIP, this.x + i, this.y, true);
                this.squares.add(s);
            }
        } else {
            for(int i = 0; i < this.getShipType().length; i++) {
                Square s = new Square(SquareState.SHIP, this.x + i, this.y, true);
                this.squares.add(s);
            }
        }

    }
}

package Models;

import enums.ShipType;
import enums.SquareState;

import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    private Square[][] squares;
    private List<Ship> ships;

    public Square[][] getSquares() {
        return squares;
    }

    public PlayingField() {
        squares = new Square[10][10];
        this.ships = new ArrayList<>();
        createSquares();
    }

    private void createSquares(){
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                squares[x][y] = new Square(SquareState.WATER, x, y, false);
            }
        }
    }

    public boolean shipIsAllowedOnCoordinates(int x, int y, boolean horizontal, ShipType shipType){
        if(coordinatesOutsideGrid(x, y, horizontal,shipLength(shipType))){
            return false;
        }
        else if(!squaresAvailable(x, y,horizontal, shipLength(shipType))){
            return false;
        }

        return true;
    }

    private boolean coordinatesOutsideGrid(int x, int y, boolean horizontal, int shipLength){
        if(horizontal){
            if(x + shipLength <= 9){
                return false;
            }
        }
        else if((y+ shipLength) <= 9){
            return false;
        }

        return true;
    }

    private int shipLength(ShipType shipType){
        if(shipType == ShipType.AIRCRAFTCARRIER){
            return 5;
        }
        else if(shipType == ShipType.BATTLESHIP){
            return 4;
        }
        else if(shipType == ShipType.MINESWEEPER){
            return 2;
        }
        return 3;
    }

    private boolean squaresAvailable(int x, int y, boolean horizontal, int shipLength){
        if(horizontal){
            for(int i = x; i < x + shipLength; i++){
                if(squares[i][y].getSquareState() == SquareState.SHIP) {
                    return false;
                }
            }
        }
        else {
            for(int i = y; i < y + shipLength; i++)
                if(squares[x][i].getSquareState() == SquareState.SHIP) {
                    return false;
                }
        }

        return true;
    }


    public void addShip(Ship s) {
        if(s.isHorizontal()){
            for(int i = s.getX(); i < s.getX() + shipLength(s.getShipType()); i++){
                squares[i][s.getY()].setSquareState(SquareState.SHIP);
                s.addSquare(squares[i][s.getY()]);
            }
        }
        else {
            for(int i = s.getY(); i < s.getY() + shipLength(s.getShipType()); i++){
                squares[s.getX()][i].setSquareState(SquareState.SHIP);
                s.addSquare(squares[s.getX()][i]);
            }

        }

        this.ships.add(s);
    }

    public void removeAllShips() {
        ships.clear();
        createSquares();
    }

    public void removeShip(Ship s) {
        if(s.isHorizontal()){
            for(int i = s.getX(); i < s.getX() + shipLength(s.getShipType()); i++){
                squares[i][s.getY()].setSquareState(SquareState.WATER);
            }
        }
        else {
            for(int i = s.getY(); i < s.getY() + shipLength(s.getShipType()); i++){
                squares[s.getX()][i].setSquareState(SquareState.WATER);
            }

        }

        this.ships.remove(s);
    }

    public SquareState getSquareState(int x, int y){
        return squares[x][y].getSquareState();
    }

    public SquareState receiveShot(int x, int y){
        if(squares[x][y].getSquareState() == SquareState.SHIP){
            squares[x][y].setSquareState(SquareState.SHOTHIT);

            for(Ship s : ships){
                for(Square square : s.getSquares()){
                    if(square.getX() == x && square.getY() == y){
                        if(shipIsSunk(s)){
                            changeShipToSunkenState(s);
                            if(allShipsSunk()) {
                                squares[x][y].setSquareState(SquareState.ALLSUNK);
                            }
                        }
                    }
                }
            }
        }
        else if(squares[x][y].getSquareState() == SquareState.WATER){
            squares[x][y].setSquareState(SquareState.SHOTMISSED);
        }

        return squares[x][y].getSquareState();
    }

    private boolean shipIsSunk(Ship s){
        for(Square square : s.getSquares()){
            if(square.getSquareState() == SquareState.SHIP){
                return false;
            }
        }

        return true;
    }

    private boolean allShipsSunk() {
        for(Ship s: ships) {
            for(Square square : s.getSquares()){
                if(square.getSquareState() == SquareState.SHIP){
                    return false;
                }
            }
        }
        return true;
    }

    private void changeShipToSunkenState(Ship s){
        for(Square square : s.getSquares()){
            square.setSquareState(SquareState.SHIPSUNK);
        }
    }

    public void setSquare(int posX, int posY, SquareState newSquareState) {
        squares[posX][posY].setSquareState(newSquareState);
    }
}

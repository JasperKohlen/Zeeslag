package Models;

import enums.ShipType;
import enums.ShotType;
import enums.SquareState;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerNr;
    private String name;
    private List<Ship> shipList;
    private PlayingField playfield;
    private PlayingField opponentPlayfield;
    private boolean isCPU;
    private boolean isReady;

    public boolean isCPU() {
        return isCPU;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public Player(String name, boolean isCPU) {
        this.name = name;
        this.shipList = new ArrayList<>();
        this.playfield = new PlayingField();
        this.opponentPlayfield = new PlayingField();
        this.isCPU = isCPU;

        createShips();

        if(isCPU){
            placeShipsAutomatically();
        }
    }

    public SquareState getOpponentTile(int x, int y) {
        return opponentPlayfield.getSquareState(x, y);
    }

    // create ship foreach shiptype
    private void createShips(){
        for (ShipType s : ShipType.values()) {
            Ship ship = new Ship(s);
            shipList.add(ship);
        }
    }

    // random place ships
    public void placeShipsAutomatically(){
        for (Ship s: shipList) {
            while(!s.getIsPlaced()){
                int randomX = (int) (Math.random() * 10);
                int randomY = (int) (Math.random() * 10);
                boolean horizontal = false;
                if((Math.round( Math.random())) == 0){
                    horizontal = true;
                }

                if(playfield.shipIsAllowedOnCoordinates(randomX, randomY, horizontal, s.getShipType())){
                    s.setIsPlaced(true, randomX, randomY, horizontal);
                    playfield.addShip(s);
                }
            }
        }
    }

    public Square[][] getSquares(){
        return playfield.getSquares();
    }

    public String getName() {
        return name;
    }


    public void removeAllShips() {
        shipList.clear();
        createShips();
        playfield.removeAllShips();
    }

    public boolean placeShip(ShipType shipType, int bowX, int bowY, boolean horizontal) {
        for(Ship s : shipList){
            if(s.getShipType() == shipType){
                if(s.getIsPlaced()) {
                    return false;
                }
                if(playfield.shipIsAllowedOnCoordinates(bowX, bowY,horizontal,shipType)){
                    s.setIsPlaced(true, bowX, bowY,horizontal);
                    playfield.addShip(s);
                    return true;
                }
            }
        }

        return false;
    }

    public void removeShip(int bowX, int bowY){
        for(Ship s: shipList)
            if(s.getX() == bowX && s.getY() == bowY){
                playfield.removeShip(s);
                s.setIsPlaced(false, 0, 0,  false);
            }
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean notifyWhenReadyCheck() {
        for(Ship s : shipList){
            if (!s.getIsPlaced()) {
                return false;
            }
        }
        isReady = true;
        return true;
    }


    public SquareState receiveShot(int posX, int posY) {
        return playfield.receiveShot(posX, posY);
    }

    public ShotType setOpponentSquare(int posX, int posY, SquareState newSquareState) {
        opponentPlayfield.setSquare(posX, posY, newSquareState);
        switch (newSquareState){
            case SHIPSUNK:
                return getShotType();
            case SHOTHIT:
                return ShotType.HIT;
            case ALLSUNK:
                return ShotType.ALLSUNK;
            default:
                return ShotType.MISSED;
        }
    }

    public Square[][] getOpponentSquares() {
        return opponentPlayfield.getSquares();
    }


    private ShotType getShotType() {
        for(Ship ship: shipList){
            if(!ship.shipIsDestroyed()){
                break;
            }
            else return ShotType.ALLSUNK;
        }
        return ShotType.SUNK;
    }
}

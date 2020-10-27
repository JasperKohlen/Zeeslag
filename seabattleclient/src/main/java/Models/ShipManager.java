package Models;

import enums.ShipType;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class ShipManager {
    List<Ship> allShips = new ArrayList<>();

    public List<Ship> getShips() {
        return allShips;
    }

    public void addShip(Ship ship) {
        this.allShips.add(ship);
    }

    public Ship removeShip(Position pos) {
        for (Ship s : getShips()) {
            for (Position p : s.getPositions()) {
                if (p.getX() == pos.getX() && p.getY() == pos.getY()) {
                    allShips.remove(s);
                    return s;
                }
            }
        }
        return null;
    }

    public void removeAllShips(){
        allShips.removeAll(allShips);
    }

    public boolean checkIfOverlap(int x, int y) {
        for (Ship ship: allShips) {
            for (Position pos: ship.getPositions()) {
                if(pos.getX() == x && pos.getY() == y){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfShipOverlap(Ship ship) {
        for (Position pos: ship.getPositions()) {
            for (Ship ship2: allShips) {
                for (Position pos2: ship2.getPositions()) {
                    if(pos.getX() == pos2.getX() && pos.getY() == pos2.getY()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isInGrid(Ship ship) {
        for(Position pos: ship.getPositions()) {
            if(pos.getX() > 9 || pos.getY() > 9) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfExists(ShipType type) {
        for (Ship ship : allShips) {
            if (ship.getShipType() == type) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfAllShipsHaveBeenPlaced() {
        if(getShips().size() == 5) {
            return true;
        }
        return false;
    }


    public int getPlayerNumber(Player player) {
        return player.playerNr;
    }
}
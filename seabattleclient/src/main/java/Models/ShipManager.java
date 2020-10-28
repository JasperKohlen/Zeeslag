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

    public Ship removeShip(Square pos) {
        for (Ship s : getShips()) {
            for (Square square : s.getSquares()) {
                if (square.getX() == pos.getX() && square.getY() == pos.getY()) {
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
            for (Square square: ship.getSquares()) {
                if(square.getX() == x && square.getY() == y){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfShipOverlap(Ship ship) {
        for (Square square: ship.getSquares()) {
            for (Ship ship2: allShips) {
                for (Square s: ship2.getSquares()) {
                    if(square.getX() == s.getX() && square.getY() == s.getY()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isInGrid(Ship ship) {
        for(Square s: ship.getSquares()) {
            if(s.getX() > 9 || s.getY() > 9) {
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
}
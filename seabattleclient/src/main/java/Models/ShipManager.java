package Models;

import seabattlegui.ShipType;

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

    public void removeShip(Ship ship){
        for (Ship s : getShips()) {
            if (s.getShipType() == ship.getShipType()) {
                getShips().remove(s);
            }
        }
    }

    public boolean checkIfOverlap(int x, int y){
        for (Ship ship: allShips) {
            for (Position pos: ship.getPositions()) {
                if(pos.getX() == x && pos.getY() == y){
                    return true;
                }
            }
        }
        return false;
    }
}
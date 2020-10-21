package seabattleai;

import Models.Ship;
import seabattlegui.ShipType;

import java.util.List;

public interface IStrategy {
    public List<Ship> placeShips();

    public Ship placeShipRandomly(ShipType shipType);

    public List<Ship> placeShipsRandomly();
}

package Models;

import seabattlegui.ShipType;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private ShipType shipType;
    private int xBow;
    private int yBow;
    private boolean horizontal;
    private List<Position> positions;

    public Ship(ShipType _shipType, int X, int Y, boolean _horizontal) {
        shipType = _shipType;
        xBow = X;
        yBow = Y;
        horizontal = _horizontal;
        positions = new ArrayList<>();
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public int getxBow() {
        return xBow;
    }

    public void setxBow(int xBow) {
        this.xBow = xBow;
    }

    public int getyBow() {
        return yBow;
    }

    public void setyBow(int yBow) {
        this.yBow = yBow;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void addPositions(Position pos) {
        positions.add(pos);
    }
}

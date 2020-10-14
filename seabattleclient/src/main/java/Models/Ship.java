package Models;

import seabattlegui.ShipType;

public class Ship {
    private ShipType shipType;
    private int xBow;
    private int yBow;
    private boolean horizontal;

    public Ship(ShipType _shipType, int X, int Y, boolean _horizontal) {
        shipType = _shipType;
        xBow = X;
        yBow = Y;
        horizontal = _horizontal;
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
}

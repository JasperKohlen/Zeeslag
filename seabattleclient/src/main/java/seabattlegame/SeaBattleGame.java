/*
 * Sea Battle Start project.
 */
package seabattlegame;

import Models.Player;
import Models.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seabattleai.IStrategy;
import seabattleai.SimpleStrategy;
import seabattlegui.ISeaBattleGUI;
import seabattlegui.ShipType;
import seabattlegui.SquareState;

import java.util.List;

/**
 * The Sea Battle game. To be implemented.
 *
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame {

    private static final Logger log = LoggerFactory.getLogger(SeaBattleGame.class);
    ISeaBattleGUI _application;

    @Override
    public void registerPlayer(String name, String password, ISeaBattleGUI application, boolean singlePlayerMode) {
        //TODO: get Player from Server (integer) and check if name is unique, check if only one player is there (server)
        if (name == null || password == null) {
            throw new IllegalArgumentException();
        } else {
            int playerNr = 0;
            this._application = application;
            _application.setPlayerNumber(playerNr, name);
        }
    }

    @Override
    public void placeShipsAutomatically(int playerNr) {
        IStrategy strategy = new SimpleStrategy();
        List<Ship> ships = strategy.placeShips();
        for (Ship ship: ships){
            placeShip(playerNr, ship.getShipType(), ship.getxBow(), ship.getyBow(), ship.isHorizontal());
        }
    }

    @Override
    public void placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal) {
        switch(shipType) {
            case AIRCRAFTCARRIER:
                if(horizontal) {
                    for(int i = 0; i < 5; i++) {
                        _application.showSquarePlayer(playerNr, bowX + i, bowY, SquareState.SHIP);
                    }
                } else {
                    for(int i = 0; i < 5; i++) {
                        _application.showSquarePlayer(playerNr, bowX, bowY + i, SquareState.SHIP);
                    }
                }
                break;
            case BATTLESHIP:
                if(horizontal) {
                    for(int i = 0; i < 4; i++) {
                        _application.showSquarePlayer(playerNr, bowX + i, bowY, SquareState.SHIP);
                    }
                } else {
                    for(int i = 0; i < 4; i++) {
                        _application.showSquarePlayer(playerNr, bowX, bowY + i, SquareState.SHIP);
                    }
                }
                break;
            case CRUISER:
                case SUBMARINE:
                if(horizontal) {
                    for(int i = 0; i < 3; i++) {
                        _application.showSquarePlayer(playerNr, bowX + i, bowY, SquareState.SHIP);
                    }
                } else {
                    for(int i = 0; i < 3; i++) {
                        _application.showSquarePlayer(playerNr, bowX, bowY + i, SquareState.SHIP);
                    }
                }
                break;
            case MINESWEEPER:
                if(horizontal) {
                    for(int i = 0; i < 2; i++) {
                        _application.showSquarePlayer(playerNr, bowX + i, bowY, SquareState.SHIP);
                    }
                } else {
                    for(int i = 0; i < 2; i++) {
                        _application.showSquarePlayer(playerNr, bowX, bowY + i, SquareState.SHIP);
                    }
                }
                break;
        }
    }

    @Override
    public void removeShip(int playerNr, int posX, int posY) {
        throw new UnsupportedOperationException("Method removeShip() not implemented.");
    }

    @Override
    public void removeAllShips(int playerNr) {
        throw new UnsupportedOperationException("Method removeAllShips() not implemented.");
    }

    @Override
    public void notifyWhenReady(int playerNr) {
        throw new UnsupportedOperationException("Method notifyWhenReady() not implemented.");
    }

    @Override
    public void fireShot(int playerNr, int posX, int posY) {
        throw new UnsupportedOperationException("Method fireShot() not implemented.");
    }

    @Override
    public void startNewGame(int playerNr) {
        throw new UnsupportedOperationException("Method startNewGame() not implemented.");
    }
}

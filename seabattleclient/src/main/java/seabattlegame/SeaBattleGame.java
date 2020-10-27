/*
 * Sea Battle Start project.
 */
package seabattlegame;

import Models.Player;
import Models.Position;
import Models.Ship;
import Models.ShipManager;
import enums.ShipType;
import enums.SquareState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seabattleai.IStrategy;
import seabattleai.SimpleStrategy;
import seabattlegui.ISeaBattleGUI;

import java.util.List;

/**
 * The Sea Battle game. To be implemented.
 *
 * @author Nico Kuijpers
 */
public class SeaBattleGame implements ISeaBattleGame {

    private static final Logger log = LoggerFactory.getLogger(SeaBattleGame.class);
    ISeaBattleGUI _application;
    ShipManager manager = new ShipManager();
    boolean singlePlayer;
    Player player;
    Player opponent;

    @Override
    public void registerPlayer(String name, String password, ISeaBattleGUI application, boolean singlePlayerMode) {
        //TODO: get Player from Server (integer) and check if name is unique, check if only one player is there (server)
        if (name == null || password == null) {
            throw new IllegalArgumentException();
        } else if(singlePlayerMode){
            int playerNr = 0;
            this._application = application;
            _application.setPlayerNumber(playerNr, name);
            singlePlayer = singlePlayerMode;
            opponent = new Player(-1, "opponent", "notarobot");
        } else {

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
        Ship ship = new Ship(shipType, bowX, bowY, horizontal);
        Position pos1;
        ship.addPositions();
        if (!manager.checkIfExists(ship.getShipType()) && !manager.checkIfShipOverlap(ship) && manager.isInGrid(ship)) {
            if (horizontal) {
                for (int i = 0; i < shipType.length; i++) {
                    pos1 = new Position(bowX + i, bowY);
                    _application.showSquarePlayer(playerNr, pos1.getX(), pos1.getY(), SquareState.SHIP);
                }
            } else {
                for (int i = 0; i < shipType.length; i++) {
                    pos1 = new Position(bowX, bowY + i);
                    _application.showSquarePlayer(playerNr, pos1.getX(), pos1.getY(), SquareState.SHIP);
                }
            }
            manager.addShip(ship);
        }
    }

    @Override
    public void removeShip(int playerNr, int posX, int posY) {
        Position selectedPos = new Position(posX, posY);
        Ship ship = manager.removeShip(selectedPos);
        if (ship != null) {
            for (int i = 0; i < ship.getShipType().length; i++) {
                for (Position pos: ship.getPositions()) {
                    _application.showSquarePlayer(playerNr, pos.getX(), pos.getY(), SquareState.WATER);
                }
            }
        }
    }

    @Override
    public void removeAllShips(int playerNr) {
        for (Ship s: manager.getShips()) {
            for (Position pos: s.getPositions()) {
                _application.showSquarePlayer(playerNr, pos.getX(), pos.getY(), SquareState.WATER);
            }
        }
        manager.removeAllShips();
    }

    @Override
    //TODO: kijken of tweede speler klaar is
    public boolean notifyWhenReady(int playerNr) {
        if(manager.checkIfAllShipsHaveBeenPlaced() && singlePlayer) {
            ShipManager opponentShips = new ShipManager();
            placeShipsAutomatically(opponent.getPlayerNr());
            return true;
        } else if(!singlePlayer && manager.checkIfAllShipsHaveBeenPlaced()) {
            checkIfOpponentReady(playerNr);
            return true;
        } else {
            _application.showErrorMessage(playerNr, "Not all ships have been placed!");
            return false;
        }
    }

    @Override
    public void fireShot(int playerNr, int posX, int posY) {
        manager.checkIfOverlap(posX, posY);
    }

    @Override
    public void startNewGame(int playerNr) {
        throw new UnsupportedOperationException("Method startNewGame() not implemented.");
    }

    @Override
    public boolean checkIfOnSquare(int x, int y) {
        return manager.checkIfOverlap(x, y);
    }

    @Override
    //TODO:
    public boolean checkIfOpponentReady(int playerNr) {
//        int opponentNr = getOpponent(playerNr);
//        if(checkIfReady(opponentNr)) {
//            return true;
//        }
//        return false;
        return true;
    }

    public void getOpponent(int playerNr) {

    }

}

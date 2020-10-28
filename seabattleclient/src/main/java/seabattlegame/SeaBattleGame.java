/*
 * Sea Battle Start project.
 */
package seabattlegame;

import Models.*;
import enums.ShipType;
import enums.ShotType;
import enums.SquareState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restfulclient.SeaBattleREST;
import seabattleai.IStrategy;
import seabattleai.SimpleStrategy;
import seabattlegui.ISeaBattleGUI;
import websocket.CommunicatorWebSocketDTO;
import websocketclient.WebSocketClientEventHandler;

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
    private WebSocketClientEventHandler webSocketClient;
    public SeaBattleGame(WebSocketClientEventHandler webSocketClient){
        this.webSocketClient = webSocketClient;
    }

    @Override
    public void registerPlayer(String name, String password, ISeaBattleGUI application, boolean singlePlayerMode) {
        SeaBattleREST restClient = new SeaBattleREST();
        if(restClient.register(name, password)){
            webSocketClient.register(new CommunicatorWebSocketDTO(name, password, singlePlayerMode));
        }
    }

    @Override
    public void placeShipsAutomatically(int playerNr) {
        IStrategy strategy = new SimpleStrategy();
        List<Ship> ships = strategy.placeShips();
        for (Ship ship: ships){
            placeShip(playerNr, ship.getShipType(), ship.getX(), ship.getY(), ship.isHorizontal());
        }
    }

    @Override
    public void placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal) {
        Ship ship = new Ship(shipType, bowX, bowY, horizontal);
        Square square;
        ship.addSquares();
        if (!manager.checkIfExists(ship.getShipType()) && !manager.checkIfShipOverlap(ship) && manager.isInGrid(ship)) {
            if (horizontal) {
                for (int i = 0; i < shipType.length; i++) {
                    square = new Square(SquareState.SHIP, bowX + i, bowY, true);
                    _application.showSquarePlayer(playerNr, square.getX(), square.getY(), SquareState.SHIP);
                }
            } else {
                for (int i = 0; i < shipType.length; i++) {
                    square = new Square(SquareState.SHIP, bowX, bowY + i, true);
                    _application.showSquarePlayer(playerNr, square.getX(), square.getY(), SquareState.SHIP);
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
                for (Square s: ship.getSquares()) {
                    _application.showSquarePlayer(playerNr, s.getX(), s.getY(), SquareState.WATER);
                }
            }
        }
    }

    @Override
    public void removeAllShips(int playerNr) {
        for (Ship s: manager.getShips()) {
            for (Square square: s.getSquares()) {
                _application.showSquarePlayer(playerNr, square.getX(), square.getY(), SquareState.WATER);
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
    public ShotType fireShot(int playerNr, int posX, int posY) {
        manager.checkIfOverlap(posX, posY);
        return ShotType.MISSED;
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

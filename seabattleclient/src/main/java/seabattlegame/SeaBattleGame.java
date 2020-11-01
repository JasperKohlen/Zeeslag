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

    private static final Logger log = LoggerFactory.getLogger(SeaBattleGame.class);;
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
        CommunicatorWebSocketDTO dto = new CommunicatorWebSocketDTO(playerNr);
        webSocketClient.placeShipsAuto(dto);
    }

    @Override
    public void placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal) {
        CommunicatorWebSocketDTO dto = new CommunicatorWebSocketDTO(playerNr, shipType, bowX, bowY, horizontal);
        webSocketClient.placeShip(dto);
    }

    @Override
    public void removeShip(int playerNr, int posX, int posY) {
        CommunicatorWebSocketDTO dto = new CommunicatorWebSocketDTO(playerNr, posX, posY);
        webSocketClient.removeShip(dto);
    }

    @Override
    public void removeAllShips(int playerNr) {
        CommunicatorWebSocketDTO dto = new CommunicatorWebSocketDTO(playerNr);
        webSocketClient.removeAllShips(dto);
    }

    @Override
    public void notifyWhenReady(int playerNr) {
        CommunicatorWebSocketDTO dto = new CommunicatorWebSocketDTO(playerNr);
        webSocketClient.notifyWhenReady(dto);
    }

    @Override
    public void fireShot(int playerNr, int posX, int posY) {
        CommunicatorWebSocketDTO dto = new CommunicatorWebSocketDTO(playerNr, posX, posY);
        webSocketClient.fireShot(dto);

//        ShotType shotType = game.fireShot(playerNr, posX, posY);
//        updateOpponentField(playerNr, posX, posY);
//        game.getPlayer(0).getApplication().playerFiresShot(0, shotType);
//
//        boolean shotAllowed = false;
//
//
//        if(game.isSinglePlayerMode()){
//            while(!shotAllowed){
//                int randomX = (int) (Math.random() * 10);
//                int randomY = (int) (Math.random() * 10);
//                if(game.cpuShotAllowed(randomX, randomY)){
//                    game.cpuShoots(randomX, randomY);
//                    showShotByCPU(randomX, randomY);
//                    shotAllowed = true;
//        }
//      }
    }

    @Override
    public void startNewGame(int playerNr) {
        CommunicatorWebSocketDTO dto = new CommunicatorWebSocketDTO(playerNr);
        webSocketClient.newGame(dto);
    }

    @Override
    public boolean checkIfOnSquare(int x, int y) {
//        return manager.checkIfOverlap(x, y);
        return true;
    }


}

package websocketclient;

import seabattlegui.SeaBattleApplication;
import websocket.CommunicatorWebSocketDTO;
import websocket.CommunicatorWebSocketMessage;
import websocket.CommunicatorWebSocketMessageOperation;

public class WebSocketClientEventHandler {
    private CommunicatorWebSocketClient communicatorWebSocketClient;

    public WebSocketClientEventHandler(SeaBattleApplication application) {
        communicatorWebSocketClient = CommunicatorWebSocketClient.getInstance(application);
    }

    public void register(CommunicatorWebSocketDTO dto){
        CommunicatorWebSocketMessage message = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.REGISTERACCOUNT, dto);
        communicatorWebSocketClient.start();
        communicatorWebSocketClient.sendMessageToServer(message);
    }

    public void placeShipsAuto(CommunicatorWebSocketDTO dto){
        CommunicatorWebSocketMessage message = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.PLACESHIPSAUTO, dto);
        communicatorWebSocketClient.sendMessageToServer(message);
    }

    public void placeShip(CommunicatorWebSocketDTO dto) {
        CommunicatorWebSocketMessage message = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.PLACESHIP, dto);
        communicatorWebSocketClient.sendMessageToServer(message);
    }

    public void removeShip(CommunicatorWebSocketDTO dto) {
        CommunicatorWebSocketMessage message = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.REMOVESHIP, dto);
        communicatorWebSocketClient.sendMessageToServer(message);
    }

    public void removeAllShips(CommunicatorWebSocketDTO dto) {
        CommunicatorWebSocketMessage message = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.REMOVEALLSHIPS, dto);
        communicatorWebSocketClient.sendMessageToServer(message);
    }

    public void notifyWhenReady(CommunicatorWebSocketDTO dto) {
        CommunicatorWebSocketMessage message = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.NOTIFYWHENREADY, dto);
        communicatorWebSocketClient.sendMessageToServer(message);
    }

    public void fireShot(CommunicatorWebSocketDTO dto) {
        CommunicatorWebSocketMessage message = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.FIRESHOT, dto);
        communicatorWebSocketClient.sendMessageToServer(message);
    }
}

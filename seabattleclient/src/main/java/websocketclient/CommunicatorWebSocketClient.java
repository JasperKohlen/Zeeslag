package websocketclient;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import seabattlegui.SeaBattleApplication;
import websocket.CommunicatorWebSocketDTO;
import websocket.CommunicatorWebSocketMessage;
import websocket.SquareDTO;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CommunicatorWebSocketClient extends Communicator{
    private SeaBattleApplication seaBattleApplication;
    private static CommunicatorWebSocketClient instance = null;
    private final String uri = "ws://localhost:8095/websocketserver/";
    private Session session;
    private String message;
    private Gson gson = null;
    boolean isRunning = false;

    public CommunicatorWebSocketClient(SeaBattleApplication seaBattleApplication) {
        this.seaBattleApplication = seaBattleApplication;
        gson = new Gson();
    }

    public static CommunicatorWebSocketClient getInstance(SeaBattleApplication application){
        if(instance == null){
            System.out.println("[WebSocket Client create singleton instance]");
            instance = new CommunicatorWebSocketClient(application);
        }
        return instance;
    }

    @OnOpen
    public void onWebSocketConnect(Session session){
        System.out.println("[WebSocket Client open session] " + session.getRequestURI());
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session){
        this.message = message;
        System.out.println("[WebSocket Client message received] " + message);
        processMessage(message);
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        System.out.println("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason){
        System.out.print("[WebSocket Client close session] " + session.getRequestURI());
        System.out.println(" for reason " + reason);
        session = null;
    }

    @Override
    public void start() {
        System.out.println("[WebSocket Client start connection]");
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    @Override
    public void stop() {
        System.out.println("[WebSocket Client stop]");
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }


    public void sendMessageToServer(CommunicatorWebSocketMessage message) {
        String jsonMessage = gson.toJson(message);
        // Use asynchronous communication
        session.getAsyncRemote().sendText(jsonMessage);
    }

    private void startClient() {
        System.out.println("[WebSocket Client start]");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            // do something useful eventually
            ex.printStackTrace();
        }
    }

    private void stopClient(){
        System.out.println("[WebSocket Client stop]");
        try {
            session.close();

        } catch (IOException ex){
            // do something useful eventually
            ex.printStackTrace();
        }
    }

    private void processMessage(String jsonMessage) {
        // Parse incoming message
        CommunicatorWebSocketMessage wsMessage;
        try {
            wsMessage = gson.fromJson(jsonMessage, CommunicatorWebSocketMessage.class);
        }
        catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket Client ERROR: cannot parse Json message " + jsonMessage);
            return;
        }
        // Obtain content from message
        CommunicatorWebSocketDTO dto = wsMessage.getDto();

        switch (wsMessage.getOperation()){
            case REGISTERACCOUNTRESPONSESINGLE:
                seaBattleApplication.setPlayerNumber(wsMessage.getDto().getPlayerNr(), wsMessage.getDto().getUsername());
                seaBattleApplication.setOpponentName(0, "cpu");
                break;
            case SHOWPLAYERFIELD:
                SquareDTO[][] tiles = dto.getTiles();
                for(int x  = 0; x < 10; x++){
                    for(int y  = 0; y < 10; y++){
                        seaBattleApplication.showSquarePlayer(seaBattleApplication.getPlayerNr(), x,y,tiles[x][y].getTileState());
                    }
                }
                break;
            case SHOWOPPONENTPLAYERFIELD:
                SquareDTO[][] opponentTiles = dto.getTiles();
                for(int x  = 0; x < 10; x++){
                    for(int y  = 0; y < 10; y++){
                        seaBattleApplication.showSquareOpponent(seaBattleApplication.getPlayerNr(), x,y,opponentTiles[x][y].getTileState());
                    }
                }
                break;
            case STARTGAME:
                seaBattleApplication.notifyStartGame(dto.getPlayerNr());
                break;
            case SHOTFIRERESPONSE:
                seaBattleApplication.playerFiresShot(dto.getPlayerNr(), dto.getShotType());
                break;
            case SHOTRECEIVE:
                seaBattleApplication.opponentFiresShot(dto.getPlayerNr(), dto.getShotType());
                break;
            case SETPLAYER:
                seaBattleApplication.setPlayerNumber(dto.getPlayerNr(),dto.getUsername());
                break;
            case SETPLAYEROPPONENT:
                seaBattleApplication.setOpponentName(dto.getPlayerNr(), dto.getUsername());
                break;
            case PLAYERREADY:
                seaBattleApplication.showReadyMessage("Wait for other player");
                break;
            case STARTGAMEMULTIPLAYER:
                seaBattleApplication.notifyStartGameMultiplayer(dto.getPlayerNr(), dto.getStartingPlayer());
                break;
            case PLAYERNOTREADY:
                seaBattleApplication.notifyNotReady();
                break;
            case SWITCHTURN:
                seaBattleApplication.switchTurn();
                break;
        }

    }
}

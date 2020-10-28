package WebSocket;

import Models.Game;
import Models.ShipManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import websocket.CommunicatorWebSocketDTO;
import websocket.CommunicatorWebSocketMessage;
import websocket.CommunicatorWebSocketMessageOperation;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value="/websocketserver")
public class CommunicatorWebsocket {
    private static final List<Session> sessions = new ArrayList<>();
    private static final List<Game> games = new ArrayList<>();
    private Gson gson = new Gson();
    ServerLogicHandler logicHandler = new ServerLogicHandler( this);

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("[WebSocket Connected] SessionID: " + session.getId());
        String message = String.format("[New client with client side session ID]: %s", session.getId());
        sessions.add(session);
        System.out.println("[#sessions]: " + sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Received] : " + message);
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Socket Closed]: " + reason);
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }

    private void handleMessageFromClient(String jsonMessage, Session session) {
        Gson gson = new Gson();
        CommunicatorWebSocketMessage wbMessage = null;
        try {
            wbMessage = gson.fromJson(jsonMessage, CommunicatorWebSocketMessage.class);
        }
        catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket ERROR: cannot parse Json message " + jsonMessage);
            return;
        }

        // Operation defined in message
        CommunicatorWebSocketMessageOperation operation;
        operation = wbMessage.getOperation();

        // Process message based on operation
        CommunicatorWebSocketDTO dto = wbMessage.getDto();
        Game game = findGame(session);

        if(game == null && dto.isSingleplayerMode()){
            game = new Game();
            games.add(game);
            game.connectToGameSinglePlayer(session);
        }

        if(game == null && !dto.isSingleplayerMode()){
            game = connectToGame(session);
        }

        if(game == null){
            gameNotFoundError(session);
            return;
        }

        if (operation != null) {
            switch (operation) {
                case REGISTERACCOUNT:
                    logicHandler.registerAccount(game, dto, session);
                    break;
                case PLACESHIPSAUTO:
                    logicHandler.placeShipsAuto(game, dto.getPlayerNr(), session);
                    break;
                case REMOVESHIP:
                    logicHandler.removeShip(game, dto, session);
                    break;
                case PLACESHIP:
                    logicHandler.placeShip(game, dto, session);
                    break;
                case REMOVEALLSHIPS:
                    logicHandler.removeAllShips(game, dto.getPlayerNr(), session);
                    break;
                case FIRESHOT:
                    logicHandler.fireShot(game, dto, session);
                    break;
                case NOTIFYWHENREADY:
                    logicHandler.notifyWhenReady(game, dto.getPlayerNr(), session);
                    break;
                default:
                    System.out.println("[WebSocket ERROR: cannot process Json message " + jsonMessage);
                    break;
            }
        }
    }

    public Game connectToGame(Session session){
        for(Game g : games){
            if(!g.isSinglePlayerMode()){
                if(g.connectToGame(session)){
                    if(g.gameIsFull()){
                        return g;
                    }
                    else{
                        return g;
                    }
                }
            }
        }

        Game game = new Game();
        game.connectToGame(session);
        games.add(game);
        return game;
    }



    public Game findGame(Session session){
        for(Game game : games){
            for(Session s : game.getSessions()){
                if(session == s){
                    return game;
                }
            }
        }
        return null;
    }

    public void gameNotFoundError(Session s){
        CommunicatorWebSocketMessage communicatorWebSocketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.GAMENOTFOUNDERROR);
        String message = gson.toJson(communicatorWebSocketMessage, CommunicatorWebSocketMessage.class);
        s.getAsyncRemote().sendText(message);
    }
}

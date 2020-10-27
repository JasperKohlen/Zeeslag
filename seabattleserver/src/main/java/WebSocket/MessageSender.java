package WebSocket;

import Models.Game;
import Models.Player;
import Models.ShipManager;
import com.google.gson.Gson;
import enums.ShotType;
import websocket.CommunicatorWebSocketDTO;
import websocket.CommunicatorWebSocketMessage;
import websocket.CommunicatorWebSocketMessageOperation;
import websocket.TileDTO;

import javax.websocket.Session;

public class MessageSender {
    Gson gson = new Gson();

    public void notifyUserWaitForOtherPlayer(Session sess){
        CommunicatorWebSocketMessage communicatorWebSocketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.WAITFOROTHERPLAYER);
        String message = gson.toJson(communicatorWebSocketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void notifySinglePlayerUser(Session sess, ShipManager shipManager, Player player){
        CommunicatorWebSocketDTO socketDTO = new CommunicatorWebSocketDTO(shipManager.getPlayerNumber(player), player.getName());
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.REGISTERACCOUNTRESPONSESINGLE, socketDTO);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void startGameSinglePlayer(Session sess, int playerNr ){
        CommunicatorWebSocketDTO communicatorWebSocketDTO = new CommunicatorWebSocketDTO(playerNr);
        CommunicatorWebSocketMessage communicatorWebSocketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.STARTGAMESINGLEPLAYER, communicatorWebSocketDTO);
        String message = gson.toJson(communicatorWebSocketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void setPlayerOpponent(Session sess, int playerNr, String username){
        CommunicatorWebSocketDTO communicatorWebSocketDTO = new CommunicatorWebSocketDTO(playerNr, username);
        CommunicatorWebSocketMessage communicatorWebSocketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.SETPLAYEROPPONENT, communicatorWebSocketDTO);
        String message = gson.toJson(communicatorWebSocketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void setPlayer(Session sess, int playerNr, String username){
        CommunicatorWebSocketDTO communicatorWebSocketDTO = new CommunicatorWebSocketDTO(playerNr, username);
        CommunicatorWebSocketMessage communicatorWebSocketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.SETPLAYER, communicatorWebSocketDTO);
        String message = gson.toJson(communicatorWebSocketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void startMultiplayerGame(Session sess, int playerNr, int startingPlayer){
        CommunicatorWebSocketDTO communicatorWebSocketDTO = new CommunicatorWebSocketDTO(playerNr, startingPlayer);
        CommunicatorWebSocketMessage communicatorWebSocketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.STARTGAMEMULTIPLAYER, communicatorWebSocketDTO);
        String message = gson.toJson(communicatorWebSocketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void showPlayerField(Session sess, TileDTO[][] tileDTO, int playerNr) {
        CommunicatorWebSocketDTO socketDTO = new CommunicatorWebSocketDTO(playerNr, tileDTO);
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.SHOWPLAYERFIELD, socketDTO);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void showOpponentField(Session sess, TileDTO[][] tileDTO, int playerNr){
        CommunicatorWebSocketDTO socketDTO = new CommunicatorWebSocketDTO(playerNr, tileDTO);
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.SHOWOPPONENTPLAYERFIELD, socketDTO);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void sendPlayerReady(Session sess, int playerNr) {
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.PLAYERREADY);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void sendPlayerNotReady(Session sess, int playerNr){
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.PLAYERNOTREADY);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        sess.getAsyncRemote().sendText(message);
    }

    public void showEnemyShot(int playerNr, ShotType shotType, Session session){
        int correctPlayerNr;
        if(playerNr == 1){
            correctPlayerNr = 0;
        }else{
            correctPlayerNr = 1;
        }

        CommunicatorWebSocketDTO communicatorWebSocketDTO = new CommunicatorWebSocketDTO(correctPlayerNr, shotType);
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.SHOTRECEIVE, communicatorWebSocketDTO);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        session.getAsyncRemote().sendText(message);
    }

    public void showShotType(int playerNr, ShotType shotType, Session session){
        CommunicatorWebSocketDTO communicatorWebSocketDTO = new CommunicatorWebSocketDTO(playerNr, shotType);
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.SHOTFIRERESPONSE, communicatorWebSocketDTO);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        session.getAsyncRemote().sendText(message);
    }

    public void switchTurn(Session session) {
        CommunicatorWebSocketMessage socketMessage = new CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.SWITCHTURN);
        String message = gson.toJson(socketMessage, CommunicatorWebSocketMessage.class);
        session.getAsyncRemote().sendText(message);
    }
}

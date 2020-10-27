package websocketclient;

import com.google.gson.Gson;
import seabattlegui.SeaBattleApplication;

import javax.websocket.Session;

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

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}

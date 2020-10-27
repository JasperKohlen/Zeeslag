package Models;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> playerList = new ArrayList<>();
    public List<Player> getPlayerList() {
        return playerList;
    }
    private boolean singlePlayerMode;
    private List<Session> sessions = new ArrayList<>();

    public Game() {
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public int decideStartingPlayer(){
        int startingPlayer = 1;
        if((Math.round( Math.random())) == 0){
            startingPlayer = 0;
        }

        return startingPlayer;
    }

    public boolean isSinglePlayerMode() {
        return singlePlayerMode;
    }

    public void connectToGameSinglePlayer(Session session) {
        this.sessions.add(session);
        singlePlayerMode = true;
    }

    public boolean connectToGame(Session session){
        if(gameIsFull()){
            return false;
        }

        sessions.add(session);
        return true;
    }

    public boolean gameIsFull(){
        return sessions.size() == 2;
    }

    public Player getOpponent(int playerNr){
        if(playerNr == 0){
            for(Player p : playerList){
                if(p.getPlayerNr() != 0){
                    return p;
                }
            }
        }

        else{
            for(Player p : playerList){
                if(p.getPlayerNr() != 1){
                    return p;
                }
            }
        }

        return null;
    }
}

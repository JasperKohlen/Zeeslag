package Models;

import enums.ShipType;
import enums.ShotType;
import enums.SquareState;

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

    public boolean placeShip(int playerNr, ShipType shipType, int bowX, int bowY, boolean horizontal){
        return playerList.get(playerNr).placeShip(shipType, bowX, bowY, horizontal);
    }

    public void removeShip(int playerNr, int posX, int posY){
        if(playerNr == -1 || posX == -1 || posY == -1){
            throw new IllegalArgumentException();
        }
        else{
            playerList.get(playerNr).removeShip(posX, posY);
        }

    }

    public Square[][] removeAllShips(int playerNr){
        playerList.get(playerNr).removeAllShips();
        return playerList.get(playerNr).getSquares();
    }

    public boolean notifyWhenReady(int playerNr){
        return playerList.get(playerNr).notifyWhenReadyCheck();
    }

    public ShotType fireShot(int playerNr, int posX, int posY){
        if(playerNr == -1 || posX == -1 || posY == -1){
            throw new IllegalArgumentException();
        }
        else {

            SquareState newSquareState = null;

            if (playerList.get(playerNr).getOpponentTile(posX, posY) == SquareState.WATER) {
                if (playerNr == 0) {
                    newSquareState = playerList.get(1).receiveShot(posX, posY);
                } else {
                    newSquareState = playerList.get(0).receiveShot(posX, posY);
                }
            }

            return playerList.get(playerNr).setOpponentSquare(posX, posY, newSquareState);
        }
    }

    public void placeShipsAutomatically(int playerNr){
        playerList.get(playerNr).placeShipsAutomatically();
        playerList.get(playerNr).getSquares();
    }


    public boolean isSinglePlayerMode() {
        return singlePlayerMode;
    }

    public void registerPlayer(Player player, boolean singlePlayerMode){
        if(playerList.size() == 2 || player.getName() == null || player.getName() == ""){
            throw new IllegalArgumentException();
        }


        playerList.add(player);

        if (singlePlayerMode) {
            this.singlePlayerMode = true;
            createCPU();
        } else {
            this.singlePlayerMode = false;
        }
    }

    public int getPlayerNumber(Player player){
        return playerList.indexOf(player);
    }

    public Player getPlayer(int playerNr) {
        return playerList.get(playerNr);
    }

    public void createCPU(){
        Player player = new Player("CPU", true);
        playerList.add(player);
        notifyWhenReady(playerList.indexOf(player));
    }

    public boolean otherPlayerReady(int playerNr) {
        if(playerNr == 0){
            return playerList.get(1).isReady();
        }
        else {
            return playerList.get(0).isReady();
        }
    }

    public boolean cpuShotAllowed(int x, int y) {
        return playerList.get(1).getOpponentTile(x, y) == SquareState.WATER;
    }

    public ShotType cpuShoots(int x, int y) {
        SquareState squareState = playerList.get(0).receiveShot(x, y);
        return playerList.get(1).setOpponentSquare(x, y, squareState);
    }

    public boolean bothPlayersReady() {
        if(playerList.size() != 2) {
            return false;
        }

        for(Player p : playerList){
            if(!p.isReady()){
                return false;
            }
        }

        return true;
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

    public void connectToGameSinglePlayer(Session session) {
        this.sessions.add(session);
        singlePlayerMode = true;
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

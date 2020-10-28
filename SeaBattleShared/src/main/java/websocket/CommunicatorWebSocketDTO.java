package websocket;

import enums.ShipType;
import enums.ShotType;

public class CommunicatorWebSocketDTO {
    private int playerNr;
    private String username;
    private String password;
    private boolean singleplayerMode;
    private ShipType shipType;
    private int posX;
    private int posY;
    private boolean horizontal;
    private int startingPlayer;



    private SquareDTO[][] tiles;
    private ShotType shotType;

    public CommunicatorWebSocketDTO(int playerNr, ShotType shotType) {
        this.playerNr = playerNr;
        this.shotType = shotType;
    }

    public CommunicatorWebSocketDTO(int playerNr, int startingPlayer) {
        this.startingPlayer = startingPlayer;
        this.playerNr = playerNr;
    }

    public CommunicatorWebSocketDTO(int playerNr, SquareDTO[][] squareDTO) {
        this.playerNr = playerNr;
        this.tiles = squareDTO;
    }

    public SquareDTO[][] getTiles() {
        return tiles;
    }

    public CommunicatorWebSocketDTO() {

    }

    public CommunicatorWebSocketDTO(String username, String password, boolean singleplayerMode) {
        this.username = username;
        this.password = password;
        this.singleplayerMode = singleplayerMode;
    }

    public CommunicatorWebSocketDTO(SquareDTO[][] tiles) {
        this.tiles = tiles;
    }

    public CommunicatorWebSocketDTO(int playerNr) {
        this.playerNr = playerNr;
    }

    public CommunicatorWebSocketDTO(int playerNr, ShipType shipType, int posX, int posY, boolean horizontal) {
        this.playerNr = playerNr;
        this.shipType = shipType;
        this.posX = posX;
        this.posY = posY;
        this.horizontal = horizontal;
    }

    public CommunicatorWebSocketDTO(int playerNr, int posX, int posY) {
        this.playerNr = playerNr;
        this.posX = posX;
        this.posY = posY;
    }

    public CommunicatorWebSocketDTO(int playerNr, String username) {
        this.playerNr = playerNr;
        this.username = username;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public String getPassword() {
        return password;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSingleplayerMode() {
        return singleplayerMode;
    }

    public void setSingleplayerMode(boolean singleplayerMode) {
        this.singleplayerMode = singleplayerMode;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public ShotType getShotType() { return shotType;  }

}
package WebSocket;

import Models.Game;
import Models.Player;
import Models.ShipManager;
import enums.ShotType;
import seabattlegame.SeaBattleGame;
import websocket.CommunicatorWebSocketDTO;
import websocket.TileDTO;
import websocketclient.Communicator;

import javax.websocket.Session;

public class ServerLogicHandler {
    private CommunicatorWebsocket communicatorWebSocket;
    MessageSender messageSender = new MessageSender();

    public ServerLogicHandler(CommunicatorWebsocket communicatorWebSocket){
        this.communicatorWebSocket = communicatorWebSocket;
    }

//    public void registerAccount(ShipManager shipManager, CommunicatorWebSocketDTO dto, Session session){
//        if(dto.getUsername() == null || dto.getPassword() == null || dto.getUsername().equals("") || dto.getPassword().equals("")){
//            throw new IllegalArgumentException();
//        }
//        else{
//            Player player = new Player(dto.getPlayerNr(), dto.getUsername(), dto.getPassword());
//            shipManager.registerPlayer(player, dto.isSingleplayerMode());
//            int playerNr = game.getPlayerNumber(player);
//            player.setPlayerNr(playerNr);
//            messageSender.setPlayer(session, playerNr, dto.getUsername());
//
//            if(dto.isSingleplayerMode()){
//                messageSender.notifySinglePlayerUser(session,game, player);
//                return;
//            }
//            else{
//                if(game.gameIsFull()){
//                    for(Player p : game.getPlayerList()){
//                        if(p != player){
//                            for(Session s : game.getSessions()){
//                                if(s != session){
//                                    messageSender.setPlayerOpponent(s, game.getOpponent(playerNr).getPlayerNr(), dto.getUsername());
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                    for(Player p : game.getPlayerList()) {
//                        if (p == player) {
//                            for (Session s : game.getSessions()) {
//                                if (s == session) {
//                                    messageSender.setPlayerOpponent(s, playerNr, game.getOpponent(playerNr).getName());
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void placeShipsAuto(SeaBattleGame game, int playerNr, Session session){
        game.placeShipsAutomatically(playerNr);
        sendPlayerField(game, playerNr,session);
    }

    public void removeShip(SeaBattleGame game, CommunicatorWebSocketDTO dto, Session session) {
        game.removeShip(dto.getPlayerNr(), dto.getPosX(),dto.getPosY());
        sendPlayerField(game, dto.getPlayerNr(), session);
    }

    public void placeShip(SeaBattleGame game, CommunicatorWebSocketDTO dto, Session session) {
        game.placeShip(dto.getPlayerNr(), dto.getShipType(),dto.getPosX(),dto.getPosY(),dto.isHorizontal());
        sendPlayerField(game, dto.getPlayerNr(), session);
    }

    public void removeAllShips(SeaBattleGame game, int playerNr, Session session) {
        game.removeAllShips(playerNr);
        sendPlayerField(game, playerNr, session);
    }

    public void botShoots(SeaBattleGame game, Session session){
        boolean shotAllowed = false;
        while (!shotAllowed) {
            int randomX = (int) (Math.random() * 10);
            int randomY = (int) (Math.random() * 10);
            if (game.cpuShotAllowed(randomX, randomY)) {
                ShotType shotType = game.cpuShoots(randomX, randomY);
                messageSender.showEnemyShot(0, shotType, session);
                shotAllowed = true;
            }
        }
    }

//    public void fireShot(SeaBattleGame game, CommunicatorWebSocketDTO dto, Session session) {
//        ShotType shottype = game.fireShot(dto.getPlayerNr(), dto.getPosX(),dto.getPosY());
//
//        sendOpponentPlayerField(game, dto.getPlayerNr(), session);
//        messageSender.showShotType(dto.getPlayerNr(),shottype,session);
//        messageSender.switchTurn(session);
//
//        if(game.isSinglePlayerMode()) {
//            botShoots(game, session);
//            sendPlayerField(game, dto.getPlayerNr(), session);
//            sendOpponentPlayerField(game, dto.getPlayerNr(), session);
//        }
//        else{
//            // update other player
//            updateOtherPlayerAfterFireShot(game, session, shottype, dto);
//        }
//    }

    private void updateOtherPlayerAfterFireShot(Game game, Session session, ShotType shottype, CommunicatorWebSocketDTO dto){
        for(Session s: game.getSessions()){
            if(s.getId() != session.getId()){
                int correctPlayerNr;
                if(dto.getPlayerNr() == 1){
                    correctPlayerNr = 1;
                }else{
                    correctPlayerNr = 0;
                }

                sendPlayerField(game,correctPlayerNr,session);
                messageSender.switchTurn(s);
            }
        }
    }

    public void notifyWhenReady(SeaBattleGame game, int playerNr, Session session) {
        if(!game.notifyWhenReady(playerNr)){
            messageSender.sendPlayerNotReady(session, playerNr);
            return;
        }

        game.getPlayer(playerNr).setReady(true);

        if(game.isSinglePlayerMode()){
            messageSender.startGameSinglePlayer(session, playerNr);
            return;
        }

        if(game.bothPlayersReady()){
            int startingPlayer = game.decideStartingPlayer();

            // Startgame for this player
            for(Session s : game.getSessions()){
                if(s == session){
                    for(Player p : game.getPlayerList()){
                        if(p.getPlayerNr() == playerNr){
                            messageSender.startMultiplayerGame(session, playerNr, startingPlayer);
                            break;
                        }
                    }
                }
                // Startgame for other player
                else if(s != session){
                    for(Player p : game.getPlayerList()){
                        if(p.getPlayerNr() != playerNr){
                            messageSender.startMultiplayerGame(s, p.getPlayerNr(), startingPlayer);
                            break;
                        }
                    }
                }
            }
        }

        messageSender.sendPlayerReady(session, playerNr);
    }

    public void sendPlayerField(SeaBattleGame game, int playerNr, Session sess){
        Tile[][] tiles = game.getPlayer(playerNr).getTiles();
        TileDTO[][] tileDTO = makePlayerFieldDTO(tiles);
        messageSender.showPlayerField(sess, tileDTO, playerNr);
    }

    public void sendOpponentPlayerField(SeaBattleGame game, int playerNr, Session sess){
        Tile[][] tiles = game.getPlayer(playerNr).getOpponentTiles();
        TileDTO[][] tileDTO = makePlayerFieldDTO(tiles);
        messageSender.showOpponentField(sess, tileDTO, playerNr);
    }

    public TileDTO[][] makePlayerFieldDTO(Tile[][] tiles){
        TileDTO[][] tileDTO = new TileDTO[10][10];
        for(int x  = 0; x < 10; x++){
            for(int y  = 0; y < 10; y++){
                Tile tile = tiles[x][y];
                tileDTO[x][y] = new TileDTO(tile.getTileState(), tile.getX(), tile.getY(), tile.isContainsShip());
            }
        }
        return tileDTO;
    }
}

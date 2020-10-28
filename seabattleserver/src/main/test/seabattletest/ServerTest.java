package seabattletest;

import Models.Game;
import Models.Player;
import Models.Square;
import enums.ShipType;
import enums.SquareState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test()
    void testRegisterPlayerNameNull() {
        Player player = new Player(null, false);
        assertThrows(IllegalArgumentException.class, () -> game.registerPlayer(player, true));
    }

    @Test()
    void testRegisterPlayerNameEmpty() {
        Player player = new Player("", false);
        assertThrows(IllegalArgumentException.class, () -> game.registerPlayer(player, true));
    }

    @Test() // expected=IllegalArgumentException.class
    void testRegisterPlayerNumberOfPlayerExceedsSinglePlayerMode(){
        Player player = new Player("henk", true);
        Player player2 = new Player("henk", true);
        game.registerPlayer(player, true);

        assertThrows(IllegalArgumentException.class, () -> game.registerPlayer(player2, true));
    }

    @Test()
    public void testPlaceShipsAutomatically() {
        // Register player in single-player mode
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);

        // Place ships automatically
        int playerNr = game.getPlayerNumber(player);
        game.placeShipsAutomatically(playerNr);

        // Count number of squares where ships are placed in player's application
        int expectedResult = 5 + 4 + 3 + 3 + 2;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(playerNr).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        assertEquals(expectedResult,actualResult, "Wrong number of squares where ships are placed");
    }

    @Test()
    void removeShipPlayerNull(){
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);
        game.placeShip(1, ShipType.AIRCRAFTCARRIER, 1, 1, true);
        assertThrows(IllegalArgumentException.class, () -> game.removeShip(-1, 1,1));
    }

    @Test()
    void removeShipXNull(){
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);
        game.placeShip(1, ShipType.AIRCRAFTCARRIER, 1, 1, true);
        assertThrows(IllegalArgumentException.class, () -> game.removeShip(1, -1,1));
    }

    @Test()
    void removeShipYIncorrect(){
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);
        game.placeShip(1, ShipType.AIRCRAFTCARRIER, 1, 1, true);
        assertThrows(IllegalArgumentException.class, () -> game.removeShip(1, 1,-1));
    }

    @Test()
    void removeAllShips(){
        // Register player in single-player mode
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);

        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 1, 1, true );
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 4, 4, true );
        game.removeAllShips(0);

        int expectedResult = 100;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.WATER){
                    actualResult++;
                }
            }
        }

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void fireShotIncorrectXcoordinates(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);

        game.placeShipsAutomatically(0);
        assertThrows(IllegalArgumentException.class, () -> game.fireShot(1,-1,1));
    }

    @Test
    void fireShotIncorrectYcoordinates(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);

        game.placeShipsAutomatically(0);
        assertThrows(IllegalArgumentException.class, () -> game.fireShot(1,1,-1));
    }

    @Test
    void placeShipSuccess(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER,0, 0,true);
        Square[][] squares = game.getPlayer(0).getSquares();
        assertEquals(SquareState.SHIP, squares[0][0].getSquareState());
    }

    @Test
    void shipPlacedOutsideGrid(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        assertEquals(false, game.getPlayer(0).placeShip(ShipType.AIRCRAFTCARRIER, 9, 9,true));
    }

    @Test
    void shipPlacedOnOtherShip(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.getPlayer(0).placeShip(ShipType.AIRCRAFTCARRIER, 0, 0,true);
        assertEquals(false, game.getPlayer(0).placeShip(ShipType.BATTLESHIP, 0, 0,true));
    }

    @Test
    void notifyWhenReadyWhenNotAllShipsArePlaced(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        assertEquals(false, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void fireShotSuccessfully(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShipsAutomatically(0);
        game.fireShot(0,1,1);
        SquareState squareState = game.getPlayer(0).getOpponentTile(1,1);
        assertNotEquals(SquareState.WATER, squareState);
    }
}

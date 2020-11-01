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

    @Test
    void testRegisterPlayerNameNull() {
        Player player = new Player(null, false);
        assertThrows(IllegalArgumentException.class, () -> game.registerPlayer(player, true));
    }

    @Test
    void testRegisterPlayerNameEmpty() {
        Player player = new Player("", false);
        assertThrows(IllegalArgumentException.class, () -> game.registerPlayer(player, true));
    }

    @Test
    void testRegisterPlayerNumberOfPlayerExceedsSinglePlayerMode(){
        Player player = new Player("henk", true);
        Player player2 = new Player("henk", true);
        game.registerPlayer(player, true);

        assertThrows(IllegalArgumentException.class, () -> game.registerPlayer(player2, true));
    }

    @Test
    void testRegisterPlayerCorrectly() {
        Player player = new Player("henk", true);
        assertDoesNotThrow(() -> game.registerPlayer(player, true));
    }

    @Test
    public void testPlaceShipsAutomatically() {
        // Register player in single-player mode
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);

        // Place ships automatically
        int playerNr = game.getPlayerNumber(player);
        game.placeShipsAutomatically(playerNr);

        // Count number of squares where ships are placed in player's application
        int expectedResult = ShipType.AIRCRAFTCARRIER.length + ShipType.BATTLESHIP.length + ShipType.CRUISER.length + ShipType.SUBMARINE.length + ShipType.MINESWEEPER.length;
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

    @Test
    void testPlaceAircraftCarrierCorrectly() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true);

        int expectedResult = ShipType.AIRCRAFTCARRIER.length;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        for(int i = 0; i < ShipType.AIRCRAFTCARRIER.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceBattleShipCorrectly() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.BATTLESHIP, 0, 0, true);

        int expectedResult = ShipType.BATTLESHIP.length;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        for(int i = 0; i < ShipType.BATTLESHIP.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceCruiserCorrectly() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.CRUISER, 0, 0, true);

        int expectedResult = ShipType.CRUISER.length;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        for(int i = 0; i < ShipType.CRUISER.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceSubmarineCorrectly() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.SUBMARINE, 0, 0, true);

        int expectedResult = ShipType.SUBMARINE.length;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        for(int i = 0; i < ShipType.SUBMARINE.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceMineSweeperCorrectly() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.MINESWEEPER, 0, 0, true);

        int expectedResult = ShipType.MINESWEEPER.length;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        for(int i = 0; i < ShipType.MINESWEEPER.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceMultipleShipsCorrectly() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.MINESWEEPER, 0, 0, true);
        game.placeShip(0, ShipType.CRUISER, 5, 5, true);

        int expectedResult = ShipType.MINESWEEPER.length + ShipType.CRUISER.length;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        for(int i = 0; i < ShipType.MINESWEEPER.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        for(int i = 5; i < ShipType.CRUISER.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceShip_ShipTypeAlreadyPlaced() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.MINESWEEPER, 0, 0, true);
        game.placeShip(0, ShipType.MINESWEEPER, 5, 5, true);

        int expectedResult = ShipType.MINESWEEPER.length;
        int actualResult = 0;

        Square[][] squares = game.getPlayer(0).getSquares();
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++)
            {
                if(squares[x][y].getSquareState() == SquareState.SHIP){
                    actualResult++;
                }
            }
        }

        for(int i = 0; i < ShipType.MINESWEEPER.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        for(int i = 5; i < ShipType.MINESWEEPER.length; i ++) {
            assertEquals(SquareState.SHIP, squares[i][0].getSquareState());
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testRemoveShipPlayerNull(){
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);
        game.placeShip(1, ShipType.AIRCRAFTCARRIER, 1, 1, true);
        assertThrows(IllegalArgumentException.class, () -> game.removeShip(-1, 1,1));
    }

    @Test
    void testRemoveShipXNull(){
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);
        game.placeShip(1, ShipType.AIRCRAFTCARRIER, 1, 1, true);
        assertThrows(IllegalArgumentException.class, () -> game.removeShip(1, -1,1));
    }

    @Test
    void testRemoveShipYIncorrect(){
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);
        game.placeShip(1, ShipType.AIRCRAFTCARRIER, 1, 1, true);
        assertThrows(IllegalArgumentException.class, () -> game.removeShip(1, 1,-1));
    }

    @Test
    void testRemoveShipCorrectly() {
        Player player = new Player("Henk", false);
        game.registerPlayer(player,true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true);
        assertDoesNotThrow(() -> game.removeShip(0, 0, 0));
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
    void testRemoveAllShipsWorksCorrectly(){
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
    void testRemoveAllShipsWorksCorrectly_ShipsPlacedAutomatically(){
        // Register player in single-player mode
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);
        game.placeShipsAutomatically(0);
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
    void testRemoveAllShipsNoShipsPlaced() {
        Player player = new Player("Henk", true);
        game.registerPlayer(player,true);

        assertDoesNotThrow(() ->
                game.removeAllShips(0)
        );
    }

    @Test
    void testFireShotIncorrectXcoordinates(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);

        game.placeShipsAutomatically(0);
        assertThrows(IllegalArgumentException.class, () -> game.fireShot(1,-1,1));
    }

    @Test
    void testFireShotIncorrectYcoordinates(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);

        game.placeShipsAutomatically(0);
        assertThrows(IllegalArgumentException.class, () -> game.fireShot(1,1,-1));
    }

    @Test
    void testShipPlacedOutsideGrid(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        assertEquals(false, game.getPlayer(0).placeShip(ShipType.AIRCRAFTCARRIER, 9, 9,true));
    }

    @Test
    void testShipPlacedOnOtherShip(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.getPlayer(0).placeShip(ShipType.AIRCRAFTCARRIER, 0, 0,true);
        assertEquals(false, game.getPlayer(0).placeShip(ShipType.BATTLESHIP, 0, 0,true));
    }

    @Test
    void testNotifyWhenReadyAutomaticallyPlacedShips() {
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShipsAutomatically(0);
        assertEquals(true, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void testNotifyWhenReadyAllShipsPlaced() {
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true);
        game.placeShip(0, ShipType.BATTLESHIP, 1, 0, true);
        game.placeShip(0, ShipType.CRUISER, 2, 0, true);
        game.placeShip(0, ShipType.SUBMARINE, 3, 0, true);
        game.placeShip(0, ShipType.MINESWEEPER, 4, 0, true);
        assertEquals(false, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void testNotifyWhenReadyWhenNotAllShipsArePlaced(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        assertEquals(false, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void testNotifyWhenReadyWhenOneShipIsPlaced(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true);
        assertEquals(false, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void testNotifyWhenReadyWhenTwoShipsArePlaced(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true);
        game.placeShip(0, ShipType.BATTLESHIP, 1, 0, true);
        assertEquals(false, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void testNotifyWhenReadyWhenThreeShipsArePlaced(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true);
        game.placeShip(0, ShipType.BATTLESHIP, 1, 0, true);
        game.placeShip(0, ShipType.CRUISER, 2, 0, true);
        assertEquals(false, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void testNotifyWhenReadyWhenFourShipsArePlaced(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShip(0, ShipType.AIRCRAFTCARRIER, 0, 0, true);
        game.placeShip(0, ShipType.BATTLESHIP, 1, 0, true);
        game.placeShip(0, ShipType.CRUISER, 2, 0, true);
        game.placeShip(0, ShipType.SUBMARINE, 3, 0, true);
        assertEquals(false, game.getPlayer(0).notifyWhenReadyCheck());
    }

    @Test
    void testFireShotMissesRegistersCorrectly(){
        Player player = new Player("henk", false);
        game.registerPlayer(player, true);
        game.placeShipsAutomatically(0);
        game.fireShot(0,1,1);
        SquareState squareState = game.getPlayer(0).getOpponentTile(1,1);
        assertNotEquals(SquareState.WATER, squareState);
    }
}

package seabattleunittests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seabattlegame.ISeaBattleGame;
import seabattlegame.SeaBattleGame;
import seabattlegui.ShipType;
import seabattlegui.SquareState;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for Sea Battle game.
 * @author Nico Kuijpers
 */
class SeaBattleGameTest {
    
    private ISeaBattleGame game;
    private MockSeaBattleApplication applicationPlayer;
    private MockSeaBattleApplication applicationOpponent;
    
    SeaBattleGameTest() {
    }

    @BeforeEach
    void setUp() {
        
        // Create the Sea Battle game
        game = new SeaBattleGame();
        
        // Create mock Sea Battle GUI for player
        applicationPlayer = new MockSeaBattleApplication();
        
        // Create mock Sea Battle GUI for opponent
        applicationOpponent = new MockSeaBattleApplication();
    }
    
    @AfterEach
    void tearDown() {
    }

    /**
     * Example test for method registerPlayerName(). 
     * Test whether an IllegalArgumentException is thrown when parameter 
     * name is null.
     * @author Nico Kuijpers
     */
    @Test() // expected=IllegalArgumentException.class
    void testRegisterPlayerNameNull() {

        // Register player with parameter name null in single-player mode
        String password = "password";

        assertThrows(IllegalArgumentException.class, () ->
                game.registerPlayer(null, password, applicationPlayer, true)
        );
    }

    @Test()
    void testRegisterPlayerPasswordNull() {
        assertThrows(IllegalArgumentException.class, () ->
                game.registerPlayer("Henk", null, applicationPlayer, true)
        );
    }

    @Test
    void testRegisterPlayerCorrectly() {
        assertDoesNotThrow(() -> game.registerPlayer("Henk", "password", applicationPlayer, true));
    }

    @Test
    void testRegisterPlayerDuplicateName() {
        String name = "Henk";
        String password = "password";

        game.registerPlayer(name, password, applicationPlayer, true);
        assertThrows(IllegalArgumentException.class, () ->
                game.registerPlayer(name, password, applicationPlayer, true)
        );
    }
    
    /**
     * Example test for method placeShipsAutomatically().
     * Test whether the correct number of squares contain a ship in the
     * ocean area of the player's application.
     */
    @Test
    void testPlaceShipsAutomatically() {
        
        // Register player in single-player mode
        game.registerPlayer("Some Name", "Some Password", applicationPlayer, true);
        
        // Place ships automatically
        int playerNr = applicationPlayer.getPlayerNumber();
        game.placeShipsAutomatically(playerNr);
        
        // Count number of squares where ships are placed in player's application
        int expectedResult = 5 + 4 + 3 + 3 + 2;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);
        assertEquals(expectedResult,actualResult, "Wrong number of squares where ships are placed");
    }

    @Test
    void testPlaceAircraftCarrierCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.AIRCRAFTCARRIER, 0,0,true);

        int expectedResult = 5;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceBattleShipCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.BATTLESHIP, 0,0,true);

        int expectedResult = 4;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceCruiserCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.CRUISER, 0,0,true);

        int expectedResult = 3;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceSubmarineCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);

        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.SUBMARINE, 0,0,true);

        int expectedResult = 3;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceMineSweeperCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);

        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.MINESWEEPER, 0,0,true);

        int expectedResult = 2;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceMultipleShipsCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.MINESWEEPER, 0,0,true);
        game.placeShip(playerNr, ShipType.SUBMARINE, 6,5,true);

        int expectedResult = 2 + 3;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testPlaceShipOverlap() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.MINESWEEPER, 0,0,true);

        assertThrows(IllegalArgumentException.class, () ->
                game.placeShip(playerNr, ShipType.SUBMARINE, 0,0,true)
        );
    }

    @Test
    void testPlaceShipOutsideGridPane() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        assertThrows(IllegalArgumentException.class, () ->
                game.placeShip(playerNr, ShipType.SUBMARINE,15,0,true)
        );
    }

    @Test
    void testRemoveShipCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.AIRCRAFTCARRIER, 0,0,true);
        game.removeShip(playerNr, 0, 0);

        int expectedResult = 0;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testRemoveShipWrongLocation() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        assertThrows(IllegalArgumentException.class, () ->
                game.removeShip(playerNr,0,0)
        );
    }

    @Test
    void testRemoveAllShipsTwoShipsCorrectly() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.MINESWEEPER, 0,0,true);
        game.placeShip(playerNr, ShipType.SUBMARINE, 9,5,true);
        game.removeAllShips(playerNr);

        int expectedResult = 0;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testRemoveAllShips_ShipsPlacedAutomatically() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShipsAutomatically(playerNr);
        game.removeAllShips(playerNr);

        int expectedResult = 0;
        int actualResult = applicationPlayer.numberSquaresPlayerWithSquareState(SquareState.SHIP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testRemoveAllShipsNoShipsPlaced() {
        game.registerPlayer("Henk", "ventilating", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        assertThrows(IllegalArgumentException.class, () ->
                game.removeAllShips(playerNr)
        );
    }

    //TODO
    @Test
    void testNotifyWhenReadyAutomaticallyPlacedShips() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShipsAutomatically(playerNr);

        assertDoesNotThrow(() ->
                game.notifyWhenReady(playerNr)
        );
    }

    //TODO
    @Test
    void testNotifyWhenReadyFiveShipsPlacedPasses() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.AIRCRAFTCARRIER, 0, 0, false);
        game.placeShip(playerNr, ShipType.BATTLESHIP, 1, 0, false);
        game.placeShip(playerNr, ShipType.CRUISER, 2, 0, false);
        game.placeShip(playerNr, ShipType.SUBMARINE, 3, 0, false);
        game.placeShip(playerNr, ShipType.MINESWEEPER, 4, 0, false);

        assertDoesNotThrow(() ->
                game.notifyWhenReady(playerNr)
        );
    }

    //TODO
    @Test
    void testNotifyWhenReadyNoShipsPlacedThrowsException() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        assertThrows(IllegalArgumentException.class, () ->
                game.notifyWhenReady(playerNr)
        );
    }

    //TODO
    @Test
    void testNotifyWhenReadyOneShipPlacedThrowsException(){
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.AIRCRAFTCARRIER, 0, 0, false);

        assertThrows(IllegalArgumentException.class, () ->
                game.notifyWhenReady(playerNr)
        );
    }

    //TODO
    @Test
    void testNotifyWhenReadyTwoShipsPlacedThrowsException() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.AIRCRAFTCARRIER, 0, 0, false);
        game.placeShip(playerNr, ShipType.BATTLESHIP, 1, 0, false);

        assertThrows(IllegalArgumentException.class, () ->
                game.notifyWhenReady(playerNr)
        );
    }

    //TODO
    @Test
    void testNotifyWhenReadyThreeShipsPlacedThrowsException() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.AIRCRAFTCARRIER, 0, 0, false);
        game.placeShip(playerNr, ShipType.BATTLESHIP, 1, 0, false);
        game.placeShip(playerNr, ShipType.CRUISER, 2, 0, false);

        assertThrows(IllegalArgumentException.class, () ->
                game.notifyWhenReady(playerNr)
        );
    }

    //TODO
    @Test
    void testNotifyWhenReadyFourShipsPlacedThrowsException() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.placeShip(playerNr, ShipType.AIRCRAFTCARRIER, 0, 0, false);
        game.placeShip(playerNr, ShipType.BATTLESHIP, 1, 0, false);
        game.placeShip(playerNr, ShipType.CRUISER, 2, 0, false);
        game.placeShip(playerNr, ShipType.SUBMARINE, 3, 0, false);

        assertThrows(IllegalArgumentException.class, () ->
                game.notifyWhenReady(playerNr)
        );
    }

    //TODO
    @Test
    void testFireShotMissesRegistersCorrectly() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, false);
        game.registerPlayer("Opponent", "Password", applicationOpponent, false);
        int playerNr = applicationPlayer.getPlayerNumber();
        int opponentPlayerNr = applicationOpponent.getPlayerNumber();

        game.placeShip(opponentPlayerNr, ShipType.AIRCRAFTCARRIER, 0, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.BATTLESHIP, 1, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.CRUISER, 2, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.SUBMARINE, 3, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.MINESWEEPER, 3, 0, false);

        game.placeShipsAutomatically(playerNr);

        game.notifyWhenReady(playerNr);
        game.notifyWhenReady(opponentPlayerNr);

        game.fireShot(opponentPlayerNr, 8, 8);

        int expectedResult = 1;
        int actualResult = applicationOpponent.numberSquaresPlayerWithSquareState(SquareState.SHOTMISSED);

        assertEquals(expectedResult, actualResult);
    }

    //TODO
    //Not sure if the different methods are going to be implemented this way
    @Test
    void testFireShotHitsRegistersCorrectly() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, false);
        game.registerPlayer("Opponent", "Password", applicationOpponent, false);
        int playerNr = applicationPlayer.getPlayerNumber();
        int opponentPlayerNr = applicationOpponent.getPlayerNumber();

        game.placeShip(opponentPlayerNr, ShipType.AIRCRAFTCARRIER, 0, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.BATTLESHIP, 1, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.CRUISER, 2, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.SUBMARINE, 3, 0, false);
        game.placeShip(opponentPlayerNr, ShipType.MINESWEEPER, 3, 0, false);

        game.placeShipsAutomatically(playerNr);

        game.notifyWhenReady(playerNr);
        game.notifyWhenReady(opponentPlayerNr);

        game.fireShot(opponentPlayerNr, 0, 0);

        int expectedResult = 1;
        int actualResult = applicationOpponent.numberSquaresPlayerWithSquareState(SquareState.SHOTHIT);

        assertEquals(expectedResult, actualResult);
    }

    //TODO
    //Not sure how to test
    @Test
    void testGameStartWorksCorrectly() {
        game.registerPlayer("Henk", "ventilation", applicationPlayer, true);
        int playerNr = applicationPlayer.getPlayerNumber();

        game.startNewGame(playerNr);
    }
}
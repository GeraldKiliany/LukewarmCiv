package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class TestTranscription {
   // private Game game;
    private Game alphaGame;
    private TranscriptionDecorator game;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;




    @Before
    public void setUp() {
        //Variability that allows to switch game type
        System.setOut(new PrintStream(outContent));
        alphaGame = new GameImpl(new AlphaCivFactory());
        //Update with every function call to keep game without transcription up to date
        //decoratorGame = new TranscriptionDecorator(alphaGame, true);
        game = new TranscriptionDecorator(alphaGame);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void printGetWinner(){
        Player winningPlayer = game.getWinner();
        assertEquals(winningPlayer + " is the winner.", outContent.toString());
    }

    @Test
    public void printMoveUnit(){
        Position from = new Position(3,2);
        Position to = new Position(3,3);
        game.moveUnit(from,to);
        assertEquals(game.getPlayerInTurn() + "successfully moved unit from " + from +  " to " + to, outContent.toString());
    }

    @Test
    public void printEndOfTurn(){
        Player turnEnder = game.getPlayerInTurn();
        game.endOfTurn();
        assertEquals(turnEnder + "ended their turn. ", outContent.toString());

    }

    @Test
    public void printChangeWorkforceFocusAtOneOne(){
        Position p = new Position(1,1);
        String balance = GameConstants.foodFocus;
        game.changeWorkForceFocusInCityAt(p,balance);
        assertEquals(game.getPlayerInTurn() + " changed workforce focus in city at " + p + " to " + balance, outContent.toString());
    }

    @Test
    public void printNewProductionAtOneOne(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.LEGION);
        Position p = new Position(1,1);
        String unitType = GameConstants.LEGION;
        assertEquals(game.getPlayerInTurn() + " changed production in city at " + p + " to " + unitType, outContent.toString());
    }


    @Test
    public void printPerformingUnitActionAtThreeTwo(){
        game.performUnitActionAt(new Position(3,2));
        Position p = new Position(3,2);
        assertEquals(game.getPlayerInTurn() + " performed unit action at " + p, outContent.toString());
    }


    @Test
    public void turnTranscriptionOffThenOn(){

        game.setTranscription(false);
        assertEquals(false,game.transcriptionOn());
        game.performUnitActionAt(new Position(3,2));
        Position p = new Position(3,2);
        assertEquals("",outContent.toString());

        game.setTranscription(true);
        assertEquals(true,game.transcriptionOn());
        game.performUnitActionAt(new Position(3,2));
        //Position p = new Position(3,2);
        assertEquals(game.getPlayerInTurn() + " performed unit action at " + p, outContent.toString());
    }
}

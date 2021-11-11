package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class TestTranscription {
    private Game game;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;




    @Before
    public void setUp() {
        //Variability that allows to switch game type
        System.setOut(new PrintStream(outContent));
        Game alphaGame = new GameImpl(new AlphaCivFactory());
        game = new TranscriptionDecorator(alphaGame);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
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
        assertEquals(game.getPlayerInTurn() + " changed workforce focus in city at " + p.getRow() + " " + p.getColumn() + " to " + balance, outContent.toString());
    }

    @Test
    public void printNewProductionAtOneOne(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.LEGION);
        Position p = new Position(1,1);
        String unitType = GameConstants.LEGION;
        assertEquals(game.getPlayerInTurn() + " changed production in city at " + p.getRow() + " " + p.getColumn() + " to " + unitType, outContent.toString());
    }


    @Test
    public void printPerformingUnitActionAtThreeTwo(){
        game.performUnitActionAt(new Position(3,2));
        Position p = new Position(3,2);
        assertEquals(game.getPlayerInTurn() + " performed unit action at " + p.getRow() + " " + p.getColumn(), outContent.toString());
    }


}

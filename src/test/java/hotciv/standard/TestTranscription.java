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
    public void printNewProduction(){
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

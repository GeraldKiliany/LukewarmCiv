package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGammaCiv {
    private Game game;

    /** Fixture for GammaCiv testing. */
    @Before
    public void setUp() {
        MapStrategy mapStrategy = new AlphaMapStrategy();
        AgingStrategy agingStrategy = new AlphaCivAgingStrategy();
        WinnerStrategy winnerStrategy = new AlphaCivWinnerStrategy();
        UnitActionStrategy unitActionStrategy = new GammaCivUnitActionStrategy();
        StartCitiesStrategy startCitiesStrategy = new AlphaStartCitiesStrategy();
        game = new GameImpl(mapStrategy,winnerStrategy, agingStrategy, unitActionStrategy, startCitiesStrategy);
    }

    @Test
    public void GammaCivKnowsArcherAtTwoZero() {
        Position twoZero = new Position(2, 0);
        game.performUnitActionAt(twoZero);
    }
}

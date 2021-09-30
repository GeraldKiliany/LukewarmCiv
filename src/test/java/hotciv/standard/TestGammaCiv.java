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
    public void DoesNothingIfThereIsNoUnit() {
        Position fiveZero = new Position(5, 0);
        game.performUnitActionAt(fiveZero);
    }

    @Test
    public void SettlerAtFourThreeRemovesItselfFromWorld() {
        Position fourThree = new Position(4, 3);
        assertThat(game.getUnitAt(fourThree), is(notNullValue()));
        game.performUnitActionAt(fourThree);
        assertThat(game.getUnitAt(fourThree), is(nullValue()));
    }

    @Test
    public void SettlerBuildsCityAtPosition() {
        Position fourThree = new Position(4, 3);
        assertThat(game.getCityAt(fourThree), is(nullValue()));
        game.performUnitActionAt(fourThree);
        assertThat(game.getCityAt(fourThree), is(notNullValue()));
    }

    @Test
    public void NewCityAndSettlerHaveSameOwner() {
        Position fourThree = new Position(4, 3);
        Player settlerOwner = game.getUnitAt(fourThree).getOwner();
        game.performUnitActionAt(fourThree);
        assertThat(game.getCityAt(fourThree).getOwner(), is(settlerOwner));
    }

    @Test
    public void NewCityHasPopulationSizeOne() {
        Position fourThree = new Position(4, 3);
        game.performUnitActionAt(fourThree);
        assertThat(game.getCityAt(fourThree).getSize(), is(1));
    }
}

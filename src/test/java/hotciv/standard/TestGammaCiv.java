package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGammaCiv {
    private Game game;

    /** Fixture for GammaCiv testing. */
    /*@Before
    public void setUp() {
        MapStrategy mapStrategy = new AlphaCivMapStrategy();
        AgingStrategy agingStrategy = new AlphaCivAgingStrategy();
        WinnerStrategy winnerStrategy = new AlphaCivWinnerStrategy();
        UnitActionStrategy unitActionStrategy = new GammaCivUnitActionStrategy();
        StartCitiesStrategy startCitiesStrategy = new AlphaCivStartCitiesStrategy();
        StartUnitsStrategy startUnitsStrategy = new AlphaCivStartUnitsStrategy();
        game = new GameImpl(mapStrategy,winnerStrategy, agingStrategy, unitActionStrategy, startCitiesStrategy, startUnitsStrategy);
    }*/

    @Before
    public void setUp() {
        game = new GameImpl( new GammaCivFactory() );
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
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.SETTLER));
        game.performUnitActionAt(fourThree);
        assertThat(game.getUnitAt(fourThree), is(nullValue()));
    }

    @Test
    public void SettlerBuildsCityAtFourThree() {
        Position fourThree = new Position(4, 3);
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.SETTLER));
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

    @Test
    public void ArcherFortifiesDefenseDoublesAndMovementZero() {
        Position twoZero = new Position(2, 0);
        assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(twoZero).getDefensiveStrength(), is(3));
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(1));
        game.performUnitActionAt(twoZero);
        assertThat(game.getUnitAt(twoZero).getDefensiveStrength(), is(2 * 3));
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(0));
    }

    @Test
    public void ArcherDeFortifiesGetsOriginalDefenseAndMovement() {
        Position twoZero = new Position(2, 0);
        assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(twoZero).getDefensiveStrength(), is(3));
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(1));

        game.performUnitActionAt(twoZero);
        assertThat(game.getUnitAt(twoZero).getDefensiveStrength(), is(2 * 3));
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(0));

        game.performUnitActionAt(twoZero);
        assertThat(game.getUnitAt(twoZero).getDefensiveStrength(), is(2 * 3 / 2));
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(1));
    }

    @Test
    public void FortifiedArcherCannotMove() {
        Position twoZero = new Position(2, 0);
        Position threeZero = new Position(3, 0);

        assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(1));

        game.performUnitActionAt(twoZero);
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(0));

        boolean didMove = game.moveUnit(twoZero, threeZero);
        assertThat(didMove, is(Boolean.FALSE));
        assertThat(game.getUnitAt(twoZero), is(notNullValue())); //from position still has archer
        assertThat(game.getUnitAt(threeZero), is(nullValue()));  //to position is empty
    }

    @Test
    public void DeFortifiedArcherCanMoveAgain() {
        Position twoZero = new Position(2, 0);
        Position threeZero = new Position(3, 0);

        assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(1));

        game.performUnitActionAt(twoZero);
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(0));
        game.performUnitActionAt(twoZero);
        assertThat(game.getUnitAt(twoZero).getMoveCount(), is(1));

        boolean didMove = game.moveUnit(twoZero, threeZero);
        assertThat(didMove, is(Boolean.TRUE));
        assertThat(game.getUnitAt(twoZero), is(nullValue()));      //from position is empty
        assertThat(game.getUnitAt(threeZero), is(notNullValue())); //to position now has archer
    }
}

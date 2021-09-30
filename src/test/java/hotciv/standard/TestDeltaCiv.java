package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestDeltaCiv {

    private Game game;

    @Before
    public void setUp() {
        MapStrategy DeltaMapStrat = new DeltaMapStrategy();
        StartCitiesStrategy DeltaStartCitiesStrategy = new DeltaStartCitiesStrategy();
        game = new GameImpl(DeltaMapStrat,null,null,null,DeltaStartCitiesStrategy);
    }


    @Test
    public void tileAtZeroFiveShouldBeMountains() {
        Position origin = new Position(0, 5);
        assertThat(game.getTileAt(origin).getTypeString(), is(GameConstants.MOUNTAINS));

    }
    @Test
    public void tileAtFiveSixShouldBePlains(){
        Position fiveSix = new Position(5, 6);
        assertThat(game.getTileAt(fiveSix).getTypeString(), is(GameConstants.PLAINS));
    }
    @Test
    public void tileAtTwelveOneShouldBeOcean(){
        Position twelveOne = new Position(12,1);
        assertThat(game.getTileAt(twelveOne).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void redCityAtEightTwelve(){
        Position eightTwelve = new Position(8,12);
        assertThat(game.getCityAt(eightTwelve).getOwner(), is(Player.RED));


    }

    @Test
    public void blueCityAtFourFive(){
        Position fourFive = new Position(4,5);
        assertThat(game.getCityAt(fourFive).getOwner(), is(Player.BLUE));
    }
}

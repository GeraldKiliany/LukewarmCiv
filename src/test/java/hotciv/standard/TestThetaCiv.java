package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestThetaCiv {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl( new ThetaCivFactory() );
    }

    @Test
    public void CitiesCanChangeProductionToUFO(){
        assertThat(game.getCityAt(new Position(1,1)).getProduction(), is(GameConstants.ARCHER));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.UFO);
        assertThat(game.getCityAt(new Position(1,1)).getProduction(), is(GameConstants.UFO));
    }

    @Test
    public void UFOIsPlaced(){
        game.changeProductionInCityAt(new Position(1,1),GameConstants.UFO);
        game.advanceTurns(21);
        assertThat(game.getUnitAt(new Position(1,1)).getTypeString(), is(GameConstants.UFO));
    }

}

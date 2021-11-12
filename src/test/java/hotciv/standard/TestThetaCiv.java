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

    @Test
    public void UFOCanMove2TilesInOneRound(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.UFO);
        game.advanceTurns(23);
        assertTrue(game.moveUnit(new Position(1,1), new Position(1,2)));
        assertTrue(game.moveUnit(new Position(1,2), new Position(1,3)));
        assertThat(game.getUnitAt(new Position(1,3)).getTypeString(), is (GameConstants.UFO));
    }

    @Test
    public void UFOCanMoveOverOceans(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.UFO);
        assertThat(game.getTileAt(new Position(1,0)).getTypeString(), is (GameConstants.OCEANS));
        game.advanceTurns(23);
        assertTrue(game.moveUnit(new Position(1,1), new Position(1,0)));
        assertThat(game.getUnitAt(new Position(1,0)).getTypeString(), is (GameConstants.UFO));
    }

    @Test
    public void UFOCanMoveOverMountains(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.UFO);
        assertThat(game.getTileAt(new Position(2,6)).getTypeString(), is (GameConstants.MOUNTAINS));
        game.advanceTurns(23);
        assertTrue(game.moveUnit(new Position(1,1), new Position(1,2)));
        assertTrue(game.moveUnit(new Position(1,2), new Position(2,2)));
        game.advanceTurns(2);
        assertTrue(game.moveUnit(new Position(2,2), new Position(2,3)));
        assertTrue(game.moveUnit(new Position(2,3), new Position(2,4)));
        game.advanceTurns(2);
        assertTrue(game.moveUnit(new Position(2,4), new Position(2,5)));
        assertTrue(game.moveUnit(new Position(2,5), new Position(2,6)));
        assertThat(game.getUnitAt(new Position(2,6)).getTypeString(), is (GameConstants.UFO));
    }

    @Test
    public void UFOCanFlyOverCitiesWithoutConquering(){
        Position fourone = new Position(4,1);
        Position fiveone = new Position(5,1);
        Position oneone = new Position(1, 1);
        Position threeone = new Position(3, 1);
        game.changeProductionInCityAt(oneone, GameConstants.UFO);
        game.changeProductionInCityAt(fourone, GameConstants.UFO);
        game.advanceTurns(23);
        game.moveUnit(fourone,fiveone );
        game.moveUnit(oneone, threeone);
        game.advanceTurns(2);
        game.moveUnit(threeone, fourone);
        assertThat(game.getCityAt(fourone).getOwner(), is (Player.BLUE));
    }

    @Test
    public void UFODecreasesCitySizeByOne(){
        Position fourone = new Position(4,1);
        Position fiveone = new Position(5,1);
        Position oneone = new Position(1, 1);
        Position twoone = new Position(2, 1);
        Position threeone = new Position(3, 1);
        game.changeProductionInCityAt(oneone, GameConstants.UFO);
        game.changeProductionInCityAt(fourone, GameConstants.UFO);
        game.advanceTurns(23);
        game.moveUnit(fourone,fiveone );
        game.moveUnit(oneone, twoone);
        game.moveUnit(twoone, threeone);
        game.advanceTurns(2);
        int citySize = game.getCityAt(fourone).getSize();
        game.moveUnit(threeone, fourone);
        game.performUnitActionAt(fourone);
        if (citySize == 1)
            assertNull(game.getCityAt(fourone));
        else
            assertThat(game.getCityAt(fourone).getSize(), is (citySize-1));
    }

    @Test
    public void UFOTurnsForestsToPlains(){
        Position oneone = new Position(1, 1);
        Position fourone = new Position(4, 1);
        game.changeProductionInCityAt(oneone, GameConstants.UFO);
        game.changeProductionInCityAt(fourone, GameConstants.UFO);
        game.advanceTurns(23);
        assertTrue(game.moveUnit(new Position(1,1), new Position(1,2)));
        assertTrue(game.moveUnit(new Position(1,2), new Position(1,3)));
        game.advanceTurns(2);
        assertTrue(game.moveUnit(new Position(1,3), new Position(1,4)));
        assertTrue(game.moveUnit(new Position(1,4), new Position(1,5)));
        game.advanceTurns(2);
        assertTrue(game.moveUnit(new Position(1,5), new Position(1,6)));
        assertTrue(game.moveUnit(new Position(1,6), new Position(1,7)));
        game.advanceTurns(2);
        assertTrue(game.moveUnit(new Position(1,7), new Position(1,8)));
        assertTrue(game.moveUnit(new Position(1,8), new Position(1,9)));
        assertThat(game.getTileAt(new Position(1,9)).getTypeString(), is(GameConstants.FOREST));
        game.performUnitActionAt(new Position(1, 9));
        assertThat(game.getTileAt(new Position(1,9)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void UFOHasDefenseStrengthOf8(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.UFO);
        game.advanceTurns(23);
        assertThat(game.getUnitAt(new Position(1,1)).getDefensiveStrength(), is (8));
    }

    @Test
    public void UFOHasAttackingStrengthOf1(){
        game.changeProductionInCityAt(new Position(1,1), GameConstants.UFO);
        game.advanceTurns(23);
        assertThat(game.getUnitAt(new Position(1,1)).getAttackingStrength(), is (1));
    }

    @Test
    public void redSettlerUnableToMoveToMountainTile() {
        Position zeroFour = new Position(0,4);
        Position zeroFive = new Position(0,5);
        game.placeUnitManually(zeroFour, GameConstants.SETTLER, Player.RED);
        assertThat(game.getTileAt(zeroFive).getTypeString(), is(GameConstants.MOUNTAINS));

        boolean didMove = game.moveUnit(zeroFour, zeroFive);
        assertThat(didMove, is(false));
        assertThat(game.getUnitAt(zeroFour).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(zeroFive), is(nullValue()));
    }

    @Test
    public void redSettlerUnableToMoveToOceanTile() {
        Position zeroThree = new Position(0,3);
        Position zeroTwo = new Position(0,2);
        game.placeUnitManually(zeroThree, GameConstants.SETTLER, Player.RED);
        assertThat(game.getTileAt(zeroTwo).getTypeString(), is(GameConstants.OCEANS));

        boolean didMove = game.moveUnit(zeroThree, zeroTwo);
        assertThat(didMove, is(false));
        assertThat(game.getUnitAt(zeroThree).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(zeroTwo), is(nullValue()));
    }
}

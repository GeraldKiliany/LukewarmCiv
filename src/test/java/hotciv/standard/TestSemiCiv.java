package hotciv.standard;

import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestSemiCiv {


    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl( new SemiCivFactory() );
    }

//Test Aging - Like BetaCiv
    @Test
    public void shouldAge100BetweenBefore100BC(){
        assertThat(game.getAge(),is(-4000));
        game.advanceTurns(2);
        assertThat(game.getAge(),is(-4000+100));
        game.advanceTurns(2);
        assertThat(game.getAge(),is(-4000+200));
        game.advanceTurns(2);
        assertThat(game.getAge(),is(-4000+300));
        game.advanceTurns(2);
        assertThat(game.getAge(),is(-4000+400));
}

    @Test
    public void shouldAg50BetweenAfter50ADBefore1750(){
        while(game.getAge() != 1)
            game.advanceTurns(1);
        game.advanceTurns(2);
        assertThat(game.getAge(), is(50));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(50+50));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(50+50+50));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(50+50+50+50));
    }

    @Test
    public void shouldAg25BetweenAfter1750ADBefore1900(){
        while(game.getAge() != 1750)
            game.advanceTurns(1);
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1750+25));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1750+25+25));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1750+25+25+25));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1750+25+25+25+25));
    }

    @Test
    public void gameAgeSequenceBirthOfChrist(){
        while(game.getAge() < -200)
            game.advanceTurns(1);
        game.advanceTurns(2);
        assertThat(game.getAge(), is(-100));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(-1));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(50));
    }

    @Test
    public void gameShouldAge5After1900Before1970(){
        while(game.getAge() != 1900)
            game.advanceTurns(1);
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1900+5));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1900+5+5));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1900+5+5+5));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1900+5+5+5+5));

    }

    @Test
    public void gameShouldAge1After1970(){
        while(game.getAge() != 1970)
            game.advanceTurns(1);
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1970+1));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1970+1+1));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1970+1+1+1));
        game.advanceTurns(2);
        assertThat(game.getAge(), is(1970+1+1+1+1));
    }

    //Test Unit Actions - like GammaCiv
    @Test
    public void DoesNothingIfThereIsNoUnit() {
        Position fiveZero = new Position(5, 0);
        game.performUnitActionAt(fiveZero);
    }

    @Test
    public void SettlerAtFiveFiveRemovesItselfFromWorld() {
        Position fiveFive = new Position(5, 5);
        assertThat(game.getUnitAt(fiveFive), is(notNullValue()));
        assertThat(game.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));
        game.performUnitActionAt(fiveFive);
        assertThat(game.getUnitAt(fiveFive), is(nullValue()));
    }

    @Test
    public void SettlerBuildsCityAtFourThree() {
        Position fiveFive = new Position(5, 5);
        assertThat(game.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(fiveFive), is(nullValue()));
        game.performUnitActionAt(fiveFive);
        assertThat(game.getCityAt(fiveFive), is(notNullValue()));
    }

    @Test
    public void NewCityAndSettlerHaveSameOwner() {
        Position fiveFive = new Position(5, 5);
        Player settlerOwner = game.getUnitAt(fiveFive).getOwner();
        game.performUnitActionAt(fiveFive);
        assertThat(game.getCityAt(fiveFive).getOwner(), is(settlerOwner));
    }

    @Test
    public void NewCityHasPopulationSizeOne() {
        Position fiveFive = new Position(5, 5);
        game.performUnitActionAt(fiveFive);
        assertThat(game.getCityAt(fiveFive).getSize(), is(1));
    }

    @Test
    public void ArcherFortifiesDefenseDoublesAndMovementZero() {
        Position threeEight = new Position(3, 8);
        assertThat(game.getUnitAt(threeEight).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(threeEight).getDefensiveStrength(), is(3));
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(1));
        game.performUnitActionAt(threeEight);
        assertThat(game.getUnitAt(threeEight).getDefensiveStrength(), is(2 * 3));
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(0));
    }

    @Test
    public void ArcherDeFortifiesGetsOriginalDefenseAndMovement() {
        Position threeEight = new Position(3, 8);
        assertThat(game.getUnitAt(threeEight).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(threeEight).getDefensiveStrength(), is(3));
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(1));

        game.performUnitActionAt(threeEight);
        assertThat(game.getUnitAt(threeEight).getDefensiveStrength(), is(2 * 3));
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(0));

        game.performUnitActionAt(threeEight);
        assertThat(game.getUnitAt(threeEight).getDefensiveStrength(), is(2 * 3 / 2));
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(1));
    }

    @Test
    public void FortifiedArcherCannotMove() {
        Position threeEight = new Position(3, 8);
        Position fourEight = new Position(4, 8);

        assertThat(game.getUnitAt(threeEight).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(1));

        game.performUnitActionAt(threeEight);
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(0));

        boolean didMove = game.moveUnit(threeEight, fourEight);
        assertThat(didMove, is(Boolean.FALSE));
        assertThat(game.getUnitAt(threeEight), is(notNullValue())); //from position still has archer
        assertThat(game.getUnitAt(fourEight), is(nullValue()));  //to position is empty
    }

    @Test
    public void DeFortifiedArcherCanMoveAgain() {
        Position threeEight = new Position(3, 8);
        Position fourEight = new Position(4, 8);

        assertThat(game.getUnitAt(threeEight).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(1));

        game.performUnitActionAt(threeEight);
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(0));
        game.performUnitActionAt(threeEight);
        assertThat(game.getUnitAt(threeEight).getMoveCount(), is(1));

        boolean didMove = game.moveUnit(threeEight, fourEight);
        assertThat(didMove, is(Boolean.TRUE));
        assertThat(game.getUnitAt(threeEight), is(nullValue()));      //from position is empty
        assertThat(game.getUnitAt(fourEight), is(notNullValue())); //to position now has archer
    }

    //Test Map configuration is same as DeltaCiv
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

    //Test Winner is Declared like EpsilonCiv
    @Test  //- Attack outcome will be random unless use testStub
    public void redWinsThreeAttacksAndBecomesWinner() {
        Game stubGame = new GameImpl(new SemiCivFactoryStub());
        assertThat(stubGame.getWinner(), is(nullValue()));

        //red wins first attack
        stubGame.placeUnitManually(new Position(8, 8), GameConstants.LEGION, Player.RED);
        stubGame.placeUnitManually(new Position(8, 7), GameConstants.SETTLER, Player.BLUE);
        stubGame.moveUnit(new Position(8, 8), new Position(8, 7));
        assertThat(stubGame.getUnitAt(new Position(8, 7)).getOwner(), is(Player.RED));

        assertThat(stubGame.getWinner(), is(nullValue()));

        //red wins second attack
        stubGame.placeUnitManually(new Position(8, 8), GameConstants.SETTLER, Player.BLUE);
        stubGame.moveUnit(new Position(8, 7), new Position(8, 8));
        assertThat(stubGame.getUnitAt(new Position(8, 8)).getOwner(), is(Player.RED));

        assertThat(stubGame.getWinner(), is(nullValue()));

        //red wins third attack
        stubGame.placeUnitManually(new Position(8, 7), GameConstants.SETTLER, Player.BLUE);
        stubGame.moveUnit(new Position(8, 8), new Position(8, 7));
        assertThat(stubGame.getUnitAt(new Position(8, 7)).getOwner(), is(Player.RED));

        //red is winner
        assertThat(stubGame.getWinner(), is(Player.RED));
    }


    //Test Attacking Algorithm is like EpsilonCiv
    @Test
    public void legionAttackingStrengthIs4() {
        Position fourFour = new Position(4, 4);
        Position fiveFive = new Position(5, 5);
        Position fiveFour = new Position(5, 4);

        assertThat(game.getUnitAt(fourFour).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));

        game.moveUnit(fourFour, fiveFour);
        assertThat(game.getUnitAt(fiveFour).getTypeString(), is(GameConstants.LEGION));
        TestEpsilonCivAttackingStub testStrategy = new TestEpsilonCivAttackingStub();
        testStrategy.setCurrentAttack(game, fiveFour, fiveFive);

        assertThat(testStrategy.getAttackerTerrain(), is(1));
        assertThat(testStrategy.getAttackerSupport(), is(0));

        assertThat(testStrategy.getAttackerStrength(), is((4 + 0) * 1));


    }

    @Test
    public void settlerDefendingStrengthIs6() {
        Position fourFour = new Position(4, 4);
        Position fiveFive = new Position(5, 5);
        Position fiveFour = new Position(5, 4);

        assertThat(game.getUnitAt(fourFour).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));

        game.moveUnit(fourFour, fiveFour);
        assertThat(game.getUnitAt(fiveFour).getTypeString(), is(GameConstants.LEGION));
        TestEpsilonCivAttackingStub testStrategy = new TestEpsilonCivAttackingStub();
        testStrategy.setCurrentAttack(game, fiveFour, fiveFive);
        assertThat(testStrategy.getDefenderTerrain(), is(2));
        assertThat(testStrategy.getDefenderSupport(), is(0));
        assertThat(testStrategy.getDefenderStrength(), is((3 + 0) * 2));
    }


    @Test
    public void legionAttackingSettlerLosesWith1RollsAndTerrainFactor() {
        Game stubGame = new GameImpl(new SemiCivFactoryStub());
        Position fourFour = new Position(4, 4);
        Position fiveFive = new Position(5, 5);
        Position fiveFour = new Position(5, 4);
        assertThat(stubGame.getUnitAt(fourFour).getTypeString(), is(GameConstants.LEGION));
        assertThat(stubGame.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));
        stubGame.moveUnit(fourFour, fiveFour);
        assertThat(stubGame.getUnitAt(fiveFour).getTypeString(), is(GameConstants.LEGION));
        stubGame.moveUnit(fiveFour, fiveFive);
        assertThat(stubGame.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));
        assertThat(stubGame.getUnitAt(fiveFour), is(nullValue()));


    }
    //City Workforce and Population Increase are same as AlphaCiv which do nothing
    @Test
    public void citiesAreAlwaysPopulationOne(){
        assertThat(game.getCityAt(new Position(8,12)).getSize(), is(1));
        assertThat(game.getCityAt(new Position(4,5)).getSize(), is(1));
    }

}

//Test Stubs for Epsilon Attacking to remove randomness for tests
class TestSemiCivAttackingStub implements AttackingStrategy{
    int attackerSupport;
    int defenderSupport;

    int attackerTerrain;
    int defenderTerrain;
    int attackerStrength;
    int defenderStrength;
    Game gameVar = null;
    Position fromVar = null;
    Position toVar = null;

    int die2 = 1;
    public boolean attack(Game game, Position from, Position to) {

        attackerSupport = Utility2.getFriendlySupport(game,from,game.getUnitAt(from).getOwner());
        defenderSupport = Utility2.getFriendlySupport(game,to,game.getUnitAt(to).getOwner());
        attackerTerrain = Utility2.getTerrainFactor(game,from);
        defenderTerrain = Utility2.getTerrainFactor(game,to);

        Unit attacker = game.getUnitAt(from);
        Unit defender = game.getUnitAt(to);

        attackerStrength = (attacker.getAttackingStrength()+attackerSupport)*attackerTerrain;
        defenderStrength = (defender.getDefensiveStrength()+defenderSupport)*defenderTerrain;

        boolean outcome = (attackerStrength*rollDie1() > (defenderStrength*rollDie2()));
        return outcome;
    }


    public void setCurrentAttack(Game game, Position from, Position to){this.gameVar = game; this.fromVar = from; this.toVar = to; }
    public int getAttackerSupport(){ return  Utility2.getFriendlySupport(gameVar,fromVar,gameVar.getUnitAt(fromVar).getOwner()); }
    public int getDefenderSupport(){
        return Utility2.getFriendlySupport(gameVar,toVar,gameVar.getUnitAt(toVar).getOwner());
    }


    public int getAttackerTerrain() { return Utility2.getTerrainFactor(gameVar,fromVar); }
    public int getDefenderTerrain() { return Utility2.getTerrainFactor(gameVar,toVar);}
    public int getAttackerStrength() {
        Unit attacker = gameVar.getUnitAt(fromVar);
        return ((attacker.getAttackingStrength()+getAttackerSupport())*getAttackerTerrain()); }
    public int getDefenderStrength() {
        Unit defender = gameVar.getUnitAt(toVar);
        return ((defender.getDefensiveStrength()+getDefenderSupport())*getDefenderTerrain()); }


    //remove randomness by making die return constant value
    public int rollDie1(){ return 1;}
    public int rollDie2(){return die2;}
    public void setDie2(){die2 = 2;}
}

class SemiCivFactoryStub implements GameFactory  {
 //   boolean useTestStub;
 //   public EpsilonCivFactoryStub(boolean useTestStub) {this.useTestStub = useTestStub;}
 //   public EpsilonCivFactoryStub() {this.useTestStub = true;}
 //   public boolean getUseTestStub(){return useTestStub;}
    public MapStrategy createMapStrategy() { return new DeltaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return new EpsilonCivWinnerStrategy(); }
    public AgingStrategy createAgingStrategy() { return new BetaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new GammaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new DeltaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new DeltaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy(){ return new TestSemiCivAttackingStub(); }
    public String factoryType() { return "SemiCivFactory"; }
}

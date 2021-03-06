package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/** Skeleton class for AlphaCiv test cases

 Updated Oct 2015 for using Hamcrest matchers

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Department of Computer Science
 Aarhus University

 Please visit http://www.baerbak.com/ for further information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
public class TestEpsilonCiv {
    private Game game;

    /**
     * Fixture for EpsilonCiv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl(new EpsilonCivFactoryStub());
    }

    @Test
    public void legionAttackingStrengthIs4() {
        Position threeTwo = new Position(3, 2);
        Position fourThree = new Position(4, 3);
        Position threeThree = new Position(3, 3);

        assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.SETTLER));

        game.moveUnit(threeTwo, threeThree);
        assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));
        TestEpsilonCivAttackingStub testStrategy = new TestEpsilonCivAttackingStub();
        testStrategy.setCurrentAttack(game, threeThree, fourThree);

        assertThat(testStrategy.getAttackerTerrain(), is(1));
        assertThat(testStrategy.getAttackerSupport(), is(0));

        assertThat(testStrategy.getAttackerStrength(), is((4 + 0) * 1));


    }

    @Test
    public void settlerDefendingStrengthIs3() {
        Position threeTwo = new Position(3, 2);
        Position fourThree = new Position(4, 3);
        Position threeThree = new Position(3, 3);

        assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.SETTLER));

        game.moveUnit(threeTwo, threeThree);
        assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));
        TestEpsilonCivAttackingStub testStrategy = new TestEpsilonCivAttackingStub();
        testStrategy.setCurrentAttack(game, threeThree, fourThree);
        assertThat(testStrategy.getDefenderTerrain(), is(1));
        assertThat(testStrategy.getDefenderSupport(), is(0));
        assertThat(testStrategy.getDefenderStrength(), is((3 + 0) * 1));
    }


    @Test
    public void legionAttackingSettlerWinsWith1Rolls() {
        Position threeTwo = new Position(3, 2);
        Position fourThree = new Position(4, 3);
        Position threeThree = new Position(3, 3);
        assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.SETTLER));
        game.moveUnit(threeTwo, threeThree);
        game.advanceTurns(2);
        assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));
        game.moveUnit(threeThree, fourThree);
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(threeThree), is(nullValue()));


    }

   /* @Test
    public void legionAttackingSettlerFailsWithSettlerRoll2() {
        Position threeTwo = new Position(3, 2);
        Position fourThree = new Position(4, 3);
        Position threeThree = new Position(3, 3);
        assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.SETTLER));
        game.moveUnit(threeTwo, threeThree);
        assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));

        TestEpsilonCivAttackingStub testStrategy = new TestEpsilonCivAttackingStub();
        testStrategy.setCurrentAttack(game, threeThree, fourThree);

        game.moveUnit(threeThree, fourThree);
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(threeThree), is(nullValue()));


    }*/

    @Test
    public void redWinsThreeAttacksAndBecomesWinner() {
        assertThat(game.getWinner(), is(nullValue()));

        //red wins first attack
        game.placeUnitManually(new Position(8, 8), GameConstants.LEGION, Player.RED);
        game.placeUnitManually(new Position(8, 7), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8, 8), new Position(8, 7));
        assertThat(game.getUnitAt(new Position(8, 7)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        //red wins second attack
        game.placeUnitManually(new Position(8, 6), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8, 7), new Position(8, 6));
        assertThat(game.getUnitAt(new Position(8, 6)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        //red wins third attack
        game.placeUnitManually(new Position(8, 5), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8, 6), new Position(8, 5));
        assertThat(game.getUnitAt(new Position(8, 5)).getOwner(), is(Player.RED));

        //red is winner
        assertThat(game.getWinner(), is(Player.RED));
    }

}

class TestEpsilonCivAttackingStub implements AttackingStrategy{
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

    class EpsilonCivFactoryStub implements GameFactory  {
  //  boolean useTestStub;
  //  public EpsilonCivFactoryStub(boolean useTestStub) {this.useTestStub = useTestStub;}
  //  public EpsilonCivFactoryStub() {this.useTestStub = true;}
  //  public boolean getUseTestStub(){return useTestStub;}
    public MapStrategy createMapStrategy() { return new AlphaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return new EpsilonCivWinnerStrategy(); }
    public AgingStrategy createAgingStrategy() { return new AlphaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new AlphaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new AlphaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy(){ return new TestEpsilonCivAttackingStub(); }
        public String factoryType() { return "EpsilonCivFactory"; }
}

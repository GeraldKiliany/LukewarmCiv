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

    /** Fixture for BetaCiv testing. */
    /*@Before
    public void setUp() {
        MapStrategy mapStrategy = new AlphaCivMapStrategy();
        AgingStrategy agingStrategy = new BetaCivAgingStrategy();
        WinnerStrategy winnerStrategy = new BetaCivWinnerStrategy();
        UnitActionStrategy unitActionStrategy = new AlphaCivUnitActionStrategy();
        StartCitiesStrategy startCitiesStrategy = new AlphaCivStartCitiesStrategy();
        StartUnitsStrategy startUnitsStrategy = new AlphaCivStartUnitsStrategy();
        game = new GameImpl(mapStrategy, winnerStrategy, agingStrategy, unitActionStrategy,startCitiesStrategy,startUnitsStrategy);
    }*/

    @Before
    public void setUp() {
        game = new GameImpl( new EpsilonCivFactory() );
    }

    @Test
    public void legionAttackingStrengthIs4(){
        Position threeTwo = new Position(3, 2);
        Position fourThree = new Position(4, 3);
        Position threeThree = new Position(3,3);

        assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fourThree).getTypeString(),is(GameConstants.SETTLER));

        game.moveUnit(threeTwo,threeThree);
        assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));
        TestEpsilonCivAttackingStub testStrategy = new TestEpsilonCivAttackingStub();
        testStrategy.setCurrentAttack(game,threeThree,fourThree);

        assertThat(testStrategy.getAttackerTerrain(), is(1));
        assertThat(testStrategy.getAttackerSupport(), is(0));

        assertThat(testStrategy.getAttackerStrength(), is((4+0)*1));


    }
    @Test
    public void settlerDefendingStrengthIs3(){
        Position threeTwo = new Position(3, 2);
        Position fourThree = new Position(4, 3);
        Position threeThree = new Position(3,3);

        assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fourThree).getTypeString(),is(GameConstants.SETTLER));

        game.moveUnit(threeTwo,threeThree);
        assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));
        TestEpsilonCivAttackingStub testStrategy = new TestEpsilonCivAttackingStub();
        testStrategy.setCurrentAttack(game,threeThree,fourThree);
        assertThat(testStrategy.getDefenderTerrain(), is(1));
        assertThat(testStrategy.getDefenderSupport(), is(0));
        assertThat(testStrategy.getDefenderStrength(), is((3+0)*1));
    }


    @Test
    public void legionAttackingSettlerWinsWith1Rolls(){
        Position threeTwo = new Position(3, 2);
        Position fourThree = new Position(4, 3);
        Position threeThree = new Position(3,3);
        assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(fourThree).getTypeString(),is(GameConstants.SETTLER));
        game.moveUnit(threeTwo,threeThree);
        assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));
        game.moveUnit(threeThree,fourThree);
        assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(threeThree), is(nullValue()));




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


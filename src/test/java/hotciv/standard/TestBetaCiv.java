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
public class TestBetaCiv {
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
        game = new GameImpl( new BetaCivFactory() );
    }

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

    @Test
    public void noWinnerWhenCitiesAreOwnedByBothPlayers(){
        assertNull(game.getWinner());
    }

    @Test
    public void redUnitConquersDefenselessBlueCity() {
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));

        game.moveUnit(new Position(4,3), new Position(4,2));
        game.advanceTurns(2);
        game.moveUnit(new Position(4,2), new Position(4,1));

        //red unit moves into blue city, blue city becomes red city
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));
    }

    @Test
    public void redUnitDefeatsBlueUnitAndConquersBlueCity() {
        game.advanceTurns(6);
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.BLUE));

        game.moveUnit(new Position(4,3), new Position(4,2));
        game.advanceTurns(2);
        game.moveUnit(new Position(4,2), new Position(4,1));

        //red unit defeats blue unit, blue city becomes red city
        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));
    }

    @Test
    public void redUnitConquersAllCitiesAndBecomesWinner() {
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));

        assertThat(game.getWinner(), is(nullValue()));

        game.moveUnit(new Position(4,3), new Position(4,2));
        game.advanceTurns(2);
        game.moveUnit(new Position(4,2), new Position(4,1));

        assertThat(game.getWinner(), is(Player.RED));
    }
}
package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/** Skeleton class for ZetaCiv test cases

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
public class TestZetaCiv {
    private Game game;

    /** Fixture for ZetaCiv testing. */
    @Before
    public void setUp() {
        game = new GameImpl( new ZetaCivFactory() );
    }

    @Test
    public void redUnitConquersAllCitiesAndBecomesWinnerBefore20Rounds() {
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));

        assertThat(game.getWinner(), is(nullValue()));

        game.moveUnit(new Position(4,3), new Position(4,2));
        game.moveUnit(new Position(4,2), new Position(4,1));

        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void redUnitConquersAllCitiesButDoesNotBecomeWinnerAfter20Rounds() {
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(4,1)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));

        game.advanceTurns(2*20 + 2);
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.BLUE));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));

        assertThat(game.getWinner(), is(nullValue()));

        //conquering
        game.moveUnit(new Position(4,3), new Position(4,2));
        game.moveUnit(new Position(4,2), new Position(4,1));
        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));
    }

    /*@Test
    public void redUnitWinsThreeAttacksAfter20RoundsAndBecomesWinner() {
        game.advanceTurns(2*20 + 2);
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.BLUE));



        assertThat(game.getWinner(), is(nullValue()));

        assertThat(game.getWinner(), is(Player.RED));
    }*/
}

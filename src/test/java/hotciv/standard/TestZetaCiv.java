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
    public void redConquersAllCitiesBefore20RoundsBecomesWinner() {
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));

        assertThat(game.getWinner(), is(nullValue()));

        game.moveUnit(new Position(4,3), new Position(4,2));
        game.advanceTurns(2);
        game.moveUnit(new Position(4,2), new Position(4,1));

        assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void redConquersAllCitiesAfter20RoundsDoesNotBecomeWinner() {
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

    @Test
    public void redWinsThreeAttacksBefore20RoundsDoesNotBecomeWinner() {
        //red wins first attack (before 20)
        game.placeUnitManually(new Position(8,8), GameConstants.ARCHER, Player.RED);
        game.placeUnitManually(new Position(8,7), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,8), new Position(8,7));
        assertThat(game.getUnitAt(new Position(8,7)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        //red wins second attack (before 20)
        game.placeUnitManually(new Position(8,6), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,7), new Position(8,6));
        assertThat(game.getUnitAt(new Position(8,6)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        //red wins third attack (before 20)
        game.placeUnitManually(new Position(8,5), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,6), new Position(8,5));
        assertThat(game.getUnitAt(new Position(8,5)).getOwner(), is(Player.RED));

        //red is not winner
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void redWinsThreeAttacksAfter20RoundsBecomesWinner() {
        game.advanceTurns(2*20 + 2);
        assertThat(game.getWinner(), is(nullValue()));

        //red wins first attack (after 20)
        game.placeUnitManually(new Position(8,8), GameConstants.ARCHER, Player.RED);
        game.placeUnitManually(new Position(8,7), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,8), new Position(8,7));
        assertThat(game.getUnitAt(new Position(8,7)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        //red wins second attack (after 20)
        game.placeUnitManually(new Position(8,6), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,7), new Position(8,6));
        assertThat(game.getUnitAt(new Position(8,6)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        //red wins third attack (after 20)
        game.placeUnitManually(new Position(8,5), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,6), new Position(8,5));
        assertThat(game.getUnitAt(new Position(8,5)).getOwner(), is(Player.RED));

        //red is winner
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void redWinsTwoAttacksBefore20RoundsOneAttackAfter20RoundsDoesNotBecomeWinner() {
        game.advanceTurns(2*10);
        assertThat(game.getWinner(), is(nullValue()));

        //red wins first attack
        game.placeUnitManually(new Position(8,8), GameConstants.ARCHER, Player.RED);
        game.placeUnitManually(new Position(8,7), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,8), new Position(8,7));
        assertThat(game.getUnitAt(new Position(8,7)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        //red wins second attack
        game.placeUnitManually(new Position(8,6), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,7), new Position(8,6));
        assertThat(game.getUnitAt(new Position(8,6)).getOwner(), is(Player.RED));

        assertThat(game.getWinner(), is(nullValue()));

        game.advanceTurns(2*10 + 2);

        //red wins third attack
        game.placeUnitManually(new Position(8,5), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(8,6), new Position(8,5));
        assertThat(game.getUnitAt(new Position(8,5)).getOwner(), is(Player.RED));

        //red is not winner (only one attack won after 20 rounds)
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void blueWinsThreeAttacksAfter20RoundsBecomesWinner() {
        game.advanceTurns(2*20 + 2);
        assertThat(game.getWinner(), is(nullValue()));

        //red wins first attack
        game.placeUnitManually(new Position(7,8), GameConstants.ARCHER, Player.RED);
        game.placeUnitManually(new Position(7,7), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(7,8), new Position(7,7));
        assertThat(game.getWinner(), is(nullValue()));

        //red wins second attack
        game.placeUnitManually(new Position(7,6), GameConstants.SETTLER, Player.BLUE);
        game.moveUnit(new Position(7,7), new Position(7,6));
        assertThat(game.getWinner(), is(nullValue()));

        //blue wins first attack (after 20)
        game.placeUnitManually(new Position(8,8), GameConstants.ARCHER, Player.BLUE);
        game.placeUnitManually(new Position(8,7), GameConstants.SETTLER, Player.RED);
        game.moveUnit(new Position(8,8), new Position(8,7));
        assertThat(game.getUnitAt(new Position(8,7)).getOwner(), is(Player.BLUE));

        assertThat(game.getWinner(), is(nullValue()));

        //blue wins second attack (after 20)
        game.placeUnitManually(new Position(8,6), GameConstants.SETTLER, Player.RED);
        game.moveUnit(new Position(8,7), new Position(8,6));
        assertThat(game.getUnitAt(new Position(8,6)).getOwner(), is(Player.BLUE));

        assertThat(game.getWinner(), is(nullValue()));

        //blue wins third attack (after 20)
        game.placeUnitManually(new Position(8,5), GameConstants.SETTLER, Player.RED);
        game.moveUnit(new Position(8,6), new Position(8,5));
        assertThat(game.getUnitAt(new Position(8,5)).getOwner(), is(Player.BLUE));

        //blue is winner
        assertThat(game.getWinner(), is(Player.BLUE));
    }
}
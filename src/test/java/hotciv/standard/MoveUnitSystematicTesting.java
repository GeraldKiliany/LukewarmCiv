package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/** Skeleton class for MoveUnit systematic test cases

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
public class MoveUnitSystematicTesting {
    private Game game;

    /** Fixture for MoveUnitSystematicTesting. */

    @Before
    public void setUp() { game = new GameImpl( new MoveUnitFactory() ); }

    //Ben
    @Test
    public void redUnitUnableToMoveToTileWithFriendlyUnit() {
        Position twoZero = new Position(2,0);
        Position threeZero = new Position(3,0);
        game.placeUnitManually(twoZero, GameConstants.ARCHER, Player.RED);
        game.placeUnitManually(threeZero, GameConstants.LEGION, Player.RED);

        boolean didMove = game.moveUnit(twoZero, threeZero);
        assertThat(didMove, is(false));
        assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(threeZero).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void redUnitAbleToMoveToEmptyTile() {
        Position twoZero = new Position(2,0);
        Position threeZero = new Position(3,0);
        game.placeUnitManually(twoZero, GameConstants.ARCHER, Player.RED);

        boolean didMove = game.moveUnit(twoZero, threeZero);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(twoZero), is(nullValue()));
        assertThat(game.getUnitAt(threeZero).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void redUnitUnableToMoveToMountainTile() {
        Position twoOne = new Position(2,1);
        Position twoTwo = new Position(2,2);
        game.placeUnitManually(twoOne, GameConstants.SETTLER, Player.RED);
        assertThat(game.getTileAt(twoTwo).getTypeString(), is(GameConstants.MOUNTAINS));

        boolean didMove = game.moveUnit(twoOne, twoTwo);
        assertThat(didMove, is(false));
        assertThat(game.getUnitAt(twoOne).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(twoTwo), is(nullValue()));
    }

    @Test
    public void redUnitUnableToMoveToOceanTile() {
        Position oneOne = new Position(1,1);
        Position oneZero = new Position(1,0);
        game.placeUnitManually(oneOne, GameConstants.SETTLER, Player.RED);
        assertThat(game.getTileAt(oneZero).getTypeString(), is(GameConstants.OCEANS));

        boolean didMove = game.moveUnit(oneOne, oneZero);
        assertThat(didMove, is(false));
        assertThat(game.getUnitAt(oneOne).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(oneZero), is(nullValue()));
    }

    @Test
    public void redUnitAbleToMoveToPlainsTile() {
        Position fiveFour = new Position(5,4);
        Position fiveFive = new Position(5,5);
        game.placeUnitManually(fiveFour, GameConstants.SETTLER, Player.RED);
        assertThat(game.getTileAt(fiveFive).getTypeString(), is(GameConstants.PLAINS));

        boolean didMove = game.moveUnit(fiveFour, fiveFive);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(fiveFour), is(nullValue()));
        assertThat(game.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void redUnitAbleToMoveToForestTile() {
        Position fifteenFourteen = new Position(15,14);
        Position fifteenFifteen = new Position(15,15);
        game.placeUnitManually(fifteenFourteen, GameConstants.SETTLER, Player.RED);
        assertThat(game.getTileAt(fifteenFifteen).getTypeString(), is(GameConstants.FOREST));

        boolean didMove = game.moveUnit(fifteenFourteen, fifteenFifteen);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(fifteenFourteen), is(nullValue()));
        assertThat(game.getUnitAt(fifteenFifteen).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void redUnitAbleToMoveToHillsTile() {
        Position zeroZero = new Position(0,0);
        Position zeroOne = new Position(0,1);
        game.placeUnitManually(zeroZero, GameConstants.SETTLER, Player.RED);
        assertThat(game.getTileAt(zeroOne).getTypeString(), is(GameConstants.HILLS));

        boolean didMove = game.moveUnit(zeroZero, zeroOne);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(zeroZero), is(nullValue()));
        assertThat(game.getUnitAt(zeroOne).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void redUnitAbleToMoveOneTileLeft() {
        Position fiveFive = new Position(5,5);
        Position fourFive = new Position(4,5);
        game.placeUnitManually(fiveFive, GameConstants.SETTLER, Player.RED);

        boolean didMove = game.moveUnit(fiveFive, fourFive);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(fourFive).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(fiveFive), is(nullValue()));
    }

    @Test
    public void redUnitAbleToMoveOneTileRight() {
        Position fiveFive = new Position(5,5);
        Position sixFive = new Position(6,5);
        game.placeUnitManually(fiveFive, GameConstants.SETTLER, Player.RED);

        boolean didMove = game.moveUnit(fiveFive, sixFive);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(sixFive).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(fiveFive), is(nullValue()));
    }

    @Test
    public void redUnitAbleToMoveOneTileUp() {
        Position fiveFive = new Position(5,5);
        Position fiveFour = new Position(5,4);
        game.placeUnitManually(fiveFive, GameConstants.SETTLER, Player.RED);

        boolean didMove = game.moveUnit(fiveFive, fiveFour);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(fiveFour).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(fiveFive), is(nullValue()));
    }

    @Test
    public void redUnitAbleToMoveOneTileDown() {
        Position fiveFive = new Position(5,5);
        Position fiveSix = new Position(5,6);
        game.placeUnitManually(fiveFive, GameConstants.SETTLER, Player.RED);

        boolean didMove = game.moveUnit(fiveFive, fiveSix);
        assertThat(didMove, is(true));
        assertThat(game.getUnitAt(fiveSix).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(fiveFive), is(nullValue()));
    }

    @Test
    public void redUnitUnableToMoveTwoTilesDown() {
        Position fiveFive = new Position(5,5);
        Position fiveSeven = new Position(5,7);
        game.placeUnitManually(fiveFive, GameConstants.SETTLER, Player.RED);

        boolean didMove = game.moveUnit(fiveFive, fiveSeven);
        assertThat(didMove, is(false));
        assertThat(game.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(fiveSeven), is(nullValue()));
    }

    @Test
    public void redUnitUnableToMoveToDiagonalTile() {
        Position fiveFive = new Position(5,5);
        Position fourFour = new Position(4,4);
        game.placeUnitManually(fiveFive, GameConstants.SETTLER, Player.RED);

        boolean didMove = game.moveUnit(fiveFive, fourFour);
        assertThat(didMove, is(false));
        assertThat(game.getUnitAt(fiveFive).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(fourFour), is(nullValue()));
    }
}

package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/** Skeleton class for Integration Part 1 test cases

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
public class TestIntegrationPart1 {
    private Game alphaGame;
    private Game thetaGame;
    private GameObserverTestSpy observer;

    @Before
    public void setUp() {
        alphaGame = new GameImpl( new AlphaCivFactory() );
        thetaGame = new GameImpl( new ThetaCivFactory() );
        observer = new GameObserverTestSpy();
        alphaGame.addObserver(observer);
        thetaGame.addObserver(observer);
    }

    @Test
    public void endOfTurnUpdatesTestSpy() {
        alphaGame.endOfTurn();
        assertThat(observer.getObserved(), is("Turn Ended!"));
    }

    @Test
    public void cityProducesUnitOnItselfUpdatesTestSpy() {
        assertThat(alphaGame.getCityAt(new Position(1,1)), is(notNullValue()));
        assertThat(alphaGame.getUnitAt(new Position(1,1)), is(nullValue()));
        alphaGame.advanceTurns(5);
        assertThat(alphaGame.getUnitAt(new Position(1,1)), is(notNullValue()));
        assertThat(observer.getObserved(), is("World Changed!"));
    }

    @Test
    public void cityProducesUnitOnNeighborUpdatesTestSpy() {
        assertThat(alphaGame.getCityAt(new Position(1,1)), is(notNullValue()));
        assertThat(alphaGame.getUnitAt(new Position(0,1)), is(nullValue()));
        alphaGame.advanceTurns(10);
        assertThat(alphaGame.getUnitAt(new Position(0,1)), is(notNullValue()));
        assertThat(observer.getObserved(), is("World Changed!"));
    }

    @Test
    public void moveUnitToEmptyTileUpdatesTestSpy() {
        alphaGame.placeUnitManually(new Position(0,0), GameConstants.ARCHER, Player.RED);
        assertThat(observer.getObserved(), is(""));
        alphaGame.moveUnit(new Position(0,0), new Position(0,1));
        assertThat(observer.getObserved(), is("World Changed!"));
    }

    @Test
    public void moveUnitToEnemyUnitUpdatesTestSpy() {
        alphaGame.placeUnitManually(new Position(0,0), GameConstants.ARCHER, Player.RED);
        alphaGame.placeUnitManually(new Position(0,1), GameConstants.SETTLER, Player.BLUE);
        assertThat(observer.getObserved(), is(""));
        alphaGame.moveUnit(new Position(0,0), new Position(0,1));
        assertThat(observer.getObserved(), is("World Changed!"));
    }

    @Test
    public void settlerPlacesCityUpdatesTestSpy() {
        thetaGame.placeUnitManually(new Position(0,0), GameConstants.SETTLER, Player.RED);
        assertThat(observer.getObserved(), is(""));
        thetaGame.performUnitActionAt(new Position(0,0));
        assertThat(observer.getObserved(), is("World Changed!"));
    }

    @Test
    public void UFORemovesCityUpdatesTestSpy() {
        assertThat(thetaGame.getCityAt(new Position(1,1)), is(notNullValue()));
        thetaGame.placeUnitManually(new Position(1,1), GameConstants.UFO, Player.RED);
        assertThat(observer.getObserved(), is(""));
        thetaGame.performUnitActionAt(new Position(1,1));
        assertThat(observer.getObserved(), is("World Changed!"));
    }

    @Test
    public void UFOActionOnForestUpdatesTestSpy() {
        assertThat(thetaGame.getTileAt(new Position(1,9)).getTypeString(), is(GameConstants.FOREST));
        thetaGame.placeUnitManually(new Position(1,9), GameConstants.UFO, Player.RED);
        assertThat(observer.getObserved(), is(""));
        thetaGame.performUnitActionAt(new Position(1,9));
        assertThat(thetaGame.getTileAt(new Position(1,9)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(observer.getObserved(), is("World Changed!"));
    }

    @Test
    public void UFOActionOnOceanDoesNotUpdateTestSpy() {
        assertThat(thetaGame.getTileAt(new Position(0,0)).getTypeString(), is(GameConstants.OCEANS));
        thetaGame.placeUnitManually(new Position(0,0), GameConstants.UFO, Player.RED);
        assertThat(observer.getObserved(), is(""));
        thetaGame.performUnitActionAt(new Position(0,0));
        assertThat(thetaGame.getTileAt(new Position(0,0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(observer.getObserved(), is(""));
    }

    @Test
    public void changeTileFocusFromZeroZeroToZeroOne() {
        alphaGame.setTileFocus(new Position(0,0));
        alphaGame.setTileFocus(new Position(0,1));
    }
}

class GameObserverTestSpy implements GameObserver {
    private String observed = "";

    public void worldChangedAt(Position pos) { observed = "World Changed!"; }
    public void turnEnds(Player nextPlayer, int age) { observed = "Turn Ended!"; }
    public void tileFocusChangedAt(Position position) { observed = "Tile Focus Changed!"; }

    public String getObserved() { return observed; }
}
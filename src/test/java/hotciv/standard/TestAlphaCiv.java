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
public class TestAlphaCiv {
  private Game game;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl();
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    //assertThat(game, is(notNullValue()));
    // TODO: reenable the assert below to get started...
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  //matt
  @Test
  public void turnsShouldAlternate(){
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  //matt
  @Test
  public void gameStartsAt4000BC(){
    assertThat(game.getAge(),is(-4000));
  }

  //matt
  @Test
  public void gameAges100YearsPerRound(){
    assertThat(game.getAge(),is(-4000));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(),is(-4000+100));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(),is(-4000+200));
  }

  //matt
  @Test
  public void winnerNullWhenGameNotOver(){
    assertNull(game.getWinner());
  }

  //matt
  @Test
  public void redWinsAt3000BC(){
    while(game.getAge() != -3000) {
      assertNull(game.getWinner());
      game.endOfTurn();
    }
    assertThat(game.getAge(),is(-3000));
    assertThat(game.getWinner(), is(Player.RED));
  }

  //matt
  @Test public void redCityAtOneOne(){
    assertThat(game.getCityAt(new Position(1,1)).getOwner(), is(Player.RED));
  }

  //matt
  @Test public void blueCityAtFourOne(){
    assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
  }

  //Added by Gerald
  @Test
  public void tileAtOriginShouldBePlains() {
    Position origin = new Position(0, 0);
    assertThat(game.getTileAt(origin).getTypeString(), is("plains"));


  }

  //Added by Ben
  @Test
  public void unitAtTwoZeroShouldBeArcherRed() {
    Position twoZero = new Position(2, 0);
    assertThat(game.getUnitAt(twoZero).getTypeString(), is("archer"));
    assertThat(game.getUnitAt(twoZero).getOwner(), is(Player.RED));
  }

  //Added by Ben
  @Test
  public void unitAtFourThreeShouldBeSettlerRed() {
    Position fourThree = new Position(4,3);
    assertThat(game.getUnitAt(fourThree).getTypeString(), is("settler"));
    assertThat(game.getUnitAt(fourThree).getOwner(), is(Player.RED));
  }

  //Added by Ben
  @Test
  public void unitAtThreeTwoShouldBeLegionBlue() {
    Position threeTwo = new Position(3,2);
    assertThat(game.getUnitAt(threeTwo).getTypeString(), is("legion"));
    assertThat(game.getUnitAt(threeTwo).getOwner(), is(Player.BLUE));
  }

  //Gerald
  @Test
  public void multipleTilesArePlains(){
    Position origin = new Position(0,0);
    Position thirdRow = new Position(2,0);
    Position thirdColumn = new Position(3,0 );
    assertThat(game.getTileAt(origin).getTypeString(), is("plains"));
    assertThat(game.getTileAt(thirdRow).getTypeString(), is("plains"));
    assertThat(game.getTileAt(thirdColumn).getTypeString(), is("plains"));

  }
  //Gerald
  @Test
  public void tileAtRow1Col0isOcean(){
    Position row1Col0 = new Position(1,0);
    assertThat(game.getTileAt(row1Col0).getTypeString(), is("ocean"));



  }
  //Gerald
  @Test
  public void tileAtRow0Col1isHills(){
    Position row0Col1 = new Position(0,1);
    assertThat(game.getTileAt(row0Col1).getTypeString(), is("hills"));

  }
  //Gerald
  @Test
  public void tileAtRow2Col2isMountains(){
    Position row2Col2 = new Position(2,2);
    assertThat(game.getTileAt(row2Col2).getTypeString(), is("mountain"));
  }

  //matt
  @Test
  public void citiesAreAlwaysPopulationOne(){
    assertThat(game.getCityAt(new Position(1,1)).getSize(), is(1));
    assertThat(game.getCityAt(new Position(4,1)).getSize(), is(1));
  }

  //Gerald
  @Test
  public void moveArcherFromTwoZeroToThreeZero(){
    Position twoZero = new Position(2, 0);
    Position threeZero = new Position(3, 0);
    assertThat(game.getUnitAt(twoZero).getTypeString(), is("archer"));
    assertThat(game.getUnitAt(threeZero), is("archer"));
    game.moveUnit(twoZero,threeZero);
    assertThat(game.getUnitAt(threeZero).getTypeString(),is("archer"));
  }

}

//end of file

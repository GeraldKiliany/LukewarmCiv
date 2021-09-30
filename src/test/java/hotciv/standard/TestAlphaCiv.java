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
    MapStrategy AlphaMapStrat = new AlphaMapStrategy();
    game = new GameImpl(AlphaMapStrat);
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


  //Added by Ben
  @Test
  public void unitAtTwoZeroShouldBeArcherRed() {
    Position twoZero = new Position(2, 0);
    assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(twoZero).getOwner(), is(Player.RED));
  }

  //Added by Ben
  @Test
  public void unitAtFourThreeShouldBeSettlerRed() {
    Position fourThree = new Position(4,3);
    assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getUnitAt(fourThree).getOwner(), is(Player.RED));
  }

  //Added by Ben
  @Test
  public void unitAtThreeTwoShouldBeLegionBlue() {
    Position threeTwo = new Position(3,2);
    assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(threeTwo).getOwner(), is(Player.BLUE));
  }

  //Added by Ben
  @Test
  public void changingUnitFromSettlerRedToLegionBlue() {
    Position twoZero = new Position(2,0);
    assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(twoZero).getOwner(), is(Player.RED));

    game.getUnitAt(twoZero).setTypeString(GameConstants.LEGION);
    assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.LEGION));

    game.getUnitAt(twoZero).setOwner(Player.BLUE);
    assertThat(game.getUnitAt(twoZero).getOwner(), is(Player.BLUE));
  }

  //Added by Gerald
/*  @Test
  public void tileAtOriginShouldBePlains() {
    Position origin = new Position(0, 0);
    assertThat(game.getTileAt(origin).getTypeString(), is("plains"));

  }
*/
  //Gerald
  @Test
  public void multipleTilesArePlains(){
    Position origin = new Position(0,0);
   // Position thirdRow = new Position(2,0);
    Position thirdColumn = new Position(3,0 );
    assertThat(game.getTileAt(origin).getTypeString(), is(GameConstants.PLAINS));
   // assertThat(game.getTileAt(thirdRow).getTypeString(), is("plains"));
    assertThat(game.getTileAt(thirdColumn).getTypeString(), is(GameConstants.PLAINS));

  }


  //Gerald
  @Test
  public void tileAtRow1Col0isOcean(){
    Position row1Col0 = new Position(1,0);
    assertThat(game.getTileAt(row1Col0).getTypeString(), is(GameConstants.OCEANS));



  }


  //Gerald
  @Test
  public void tileAtRow0Col1isHills(){
    Position row0Col1 = new Position(0,1);
    assertThat(game.getTileAt(row0Col1).getTypeString(), is(GameConstants.HILLS));

  }






  //Gerald
  @Test
  public void tileAtRow2Col2isMountains(){
    Position row2Col2 = new Position(2,2);
    assertThat(game.getTileAt(row2Col2).getTypeString(), is(GameConstants.MOUNTAINS));
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
    assertThat(game.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(threeZero), is(nullValue()));
    game.moveUnit(twoZero,threeZero);
    assertThat(game.getUnitAt(threeZero).getTypeString(),is(GameConstants.ARCHER));
  }

  //Gerald
  @Test
  public void legionAttackingSettlerWins(){
    Position threeTwo = new Position(3, 2);
    Position fourThree = new Position(4, 3);
    Position threeThree = new Position(3,3);
    assertThat(game.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(fourThree).getTypeString(),is(GameConstants.SETTLER));
    game.moveUnit(threeTwo,threeThree);
    assertThat(game.getUnitAt(threeThree).getTypeString(), is(GameConstants.LEGION));
    game.moveUnit(threeThree,fourThree);
    assertThat(game.getUnitAt(fourThree).getTypeString(), is(GameConstants.LEGION));


  }

  //Ben
  @Test
  public void redArcherAttackingBlueSettlerShouldWinAndReplace() {
    Position eightZero = new Position(8, 0);
    Position eightOne = new Position(8, 1);

    game.getUnitAt(eightZero).setOwner(Player.RED);
    game.getUnitAt(eightZero).setTypeString(GameConstants.ARCHER);
    game.getUnitAt(eightOne).setOwner(Player.BLUE);
    game.getUnitAt(eightOne).setTypeString(GameConstants.SETTLER);

    //red attacks blue
    game.moveUnit(eightZero, eightOne);

    //red has replaced blue's position
    assertThat(game.getUnitAt(eightOne).getOwner(), is(not(Player.BLUE)));
    assertThat(game.getUnitAt(eightOne).getTypeString(), is(not(GameConstants.SETTLER)));
    assertThat(game.getUnitAt(eightOne).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(eightOne).getTypeString(), is(GameConstants.ARCHER));

    //origin is empty after red completes attack
    assertThat(game.getUnitAt(eightZero), is(nullValue()));
  }

  //Ben
  @Test
  public void redLegionAttackingRedArcherNeitherShouldMove() { //TODO refactor to use only interface methods, age world
    Position eightZero = new Position(8, 0);
    Position eightOne = new Position(8, 1);

    game.getUnitAt(eightZero).setOwner(Player.RED);
    game.getUnitAt(eightZero).setTypeString(GameConstants.LEGION);
    game.getUnitAt(eightOne).setOwner(Player.RED);
    game.getUnitAt(eightOne).setTypeString(GameConstants.ARCHER);

    //red attacks red
    game.moveUnit(eightZero, eightOne);

    //neither unit has moved
    assertThat(game.getUnitAt(eightZero).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(eightZero).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(eightOne).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(eightOne).getTypeString(), is(GameConstants.ARCHER));
  }

  //matt
  @Test
  public void citiesProduce6ProductionPerRound(){
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(0));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(0));
    game.endOfTurn();
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(6));
    game.endOfTurn();
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(6));



  }

  //Gerald
  @Test
  public void cityIsProducingArcher(){
    game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
    assertThat(game.getCityAt(new Position(1,1)).getProduction(),is(GameConstants.ARCHER));
  }

//  //Gerald
  @Test
  public void cityIsProducingLegion(){
    game.changeProductionInCityAt(new Position(1,1),GameConstants.LEGION);
    assertThat(game.getCityAt(new Position(1,1)).getProduction(),is(GameConstants.LEGION));
  }
//
  //Gerald
  @Test
  public void cityIsProducingArcherThenSettler(){
    game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
    assertThat(game.getCityAt(new Position(1,1)).getProduction(),is(GameConstants.ARCHER));
    game.changeProductionInCityAt(new Position(1,1),GameConstants.SETTLER);
    assertThat(game.getCityAt(new Position(1,1)).getProduction(),is(GameConstants.SETTLER));
  }
//
  //matt
  @Test
  public void shouldPlaceUnit(){
    assertNull(game.getUnitAt(new Position(1,1)));
    assertNull(game.getUnitAt (new Position(4,1)));

    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();

    //unit on red city
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12));
    game.endOfTurn();
    assertThat(game.getUnitAt(new Position(1,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10 + 6));

//    //unit on blue city
      assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12));
      game.endOfTurn();
      assertThat(game.getUnitAt(new Position(4,1)).getTypeString(),is(GameConstants.ARCHER));
       assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10 + 6));
//
//    game.endOfTurn();
//    game.endOfTurn();
//
//    //unit on north red city
//    game.endOfTurn();
//    assertThat(game.getUnitAt(new Position(0,1)).getTypeString(),is(GameConstants.ARCHER));
//    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6));
//
//    //unit on north blue city
//    game.endOfTurn();
//    assertThat(game.getUnitAt(new Position(3,1)).getTypeString(),is(GameConstants.ARCHER));
//    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6));
//
//    //unit northeast red city
//    game.endOfTurn();
//    assertThat(game.getUnitAt(new Position(0,2)).getTypeString(),is(GameConstants.ARCHER));
//    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6 - 10 + 6));
//
//    //legion northeast of blue, unit east of blue
//    game.endOfTurn();
//    assertThat(game.getUnitAt(new Position(4,2)).getTypeString(),is(GameConstants.ARCHER));
//    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6 - 10 + 6));
  }
//
//  @Test
  public void citiesShouldProduceArcherOnCityTileAfter3Rounds(){
    assertNull(game.getUnitAt(new Position(1,1)));
    assertNull(game.getUnitAt (new Position(4,1)));

    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();

    //unit on red city
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12));
    game.endOfTurn();
    assertThat(game.getUnitAt(new Position(1,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10 + 6));

    //unit on blue city
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12));
    game.endOfTurn();
    assertThat(game.getUnitAt(new Position(4,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10 + 6));
  }
//
//
  @Test
  public void citiesShouldProduceArcherOnNorthTileAfter5Rounds(){

    assertNull(game.getUnitAt(new Position(1,1)));
    assertNull(game.getUnitAt (new Position(4,1)));

    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();

    //unit on north red city
    game.endOfTurn();
    assertThat(game.getUnitAt(new Position(0,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6));

    //unit on north blue city
    game.endOfTurn();
    assertThat(game.getUnitAt(new Position(3,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6));

  }
//
  @Test
  public void citiesProduceArcherOnCorrectTileAfter6Rounds(){
    assertNull(game.getUnitAt(new Position(1,1)));
    assertNull(game.getUnitAt (new Position(4,1)));

    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    //unit northeast red city
    game.endOfTurn();
    assertThat(game.getUnitAt(new Position(0,2)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6 - 10 + 6));

    //legion northeast of blue, unit east of blue
    game.endOfTurn();
    assertThat(game.getUnitAt(new Position(4,2)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(new Position(3,2)).getTypeString(),is(GameConstants.LEGION));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6 - 10 + 6));




  }


  //TODO: Add test for moving units works only for adjacent tiles
  //TODO: Utilize strategy pattern (possibly for getting unit resource cost, more)
  //TODO: Use composition for Tile types, Unit types?
  //TODO: Split testing suite into unit testing and integration testing
  //Units for
}

//end of file

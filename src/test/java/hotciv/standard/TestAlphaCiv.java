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
    MapStrategy mapStrategy = new AlphaMapStrategy();
    AgingStrategy agingStrategy = new AlphaCivAgingStrategy();
    WinnerStrategy winnerStrategy = new AlphaCivWinnerStrategy();
    UnitActionStrategy unitActionStrategy = new AlphaCivUnitActionStrategy();
    StartCitiesStrategy startCitiesStrategy = new AlphaStartCitiesStrategy();
    game = new GameImpl(mapStrategy,winnerStrategy, agingStrategy, unitActionStrategy, startCitiesStrategy);
    //game = new GameImpl();
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
    game.advanceTurns(1);
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    game.advanceTurns(1);
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.advanceTurns(1);
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
    game.advanceTurns(2);
    assertThat(game.getAge(),is(-4000+100));
    game.advanceTurns(2);
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
      game.advanceTurns(1);
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

  //Gerald
  @Test
  public void multipleTilesArePlains(){
    Position zeroZero = new Position(0,0);
    Position threeZero = new Position(3,0 );
    assertThat(game.getTileAt(zeroZero).getTypeString(), is(GameConstants.PLAINS));
    assertThat(game.getTileAt(threeZero).getTypeString(), is(GameConstants.PLAINS));

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
    assertThat(game.getUnitAt(threeThree), is(nullValue()));

  }

  //Ben
  @Test
  public void redLegionAttackingRedArcherNeitherShouldMove() { //TODO refactor to use only interface methods, age world

    //Show there are no units on these tiles at start
    Position oneOne = new Position(1,1);
    Position zeroOne = new Position(0,1);
    assertThat(game.getUnitAt(oneOne), is(nullValue()));
    assertThat(game.getUnitAt(zeroOne), is(nullValue()));


    //Age world five rounds so there are two units owned by Red next to each other
    game.advanceTurns(10);


    assertThat(game.getUnitAt(oneOne).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(zeroOne).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(oneOne).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(zeroOne).getOwner(), is(Player.RED));

    game.moveUnit(zeroOne,oneOne);
    assertThat(game.getUnitAt(oneOne).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(zeroOne).getTypeString(), is(GameConstants.ARCHER));


  }

  //matt
  @Test
  public void citiesProduce6ProductionPerRound(){
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(0));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(0));
    game.advanceTurns(1);
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(6));
    game.advanceTurns(1);
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

    game.advanceTurns(4);

    //unit on red city
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12));
    game.advanceTurns(1);
    assertThat(game.getUnitAt(new Position(1,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10 + 6));

    //unit on blue city
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12));
    game.advanceTurns(1);
    assertThat(game.getUnitAt(new Position(4,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10 + 6));
  }

  @Test
  public void citiesShouldProduceArcherOnCityTileAfter3Rounds(){
    assertNull(game.getUnitAt(new Position(1,1)));
    assertNull(game.getUnitAt (new Position(4,1)));

    game.advanceTurns(4);

    //unit on red city
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12));
    game.advanceTurns(1);
    assertThat(game.getUnitAt(new Position(1,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10 + 6));

    //unit on blue city
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12));
    game.advanceTurns(1);
    assertThat(game.getUnitAt(new Position(4,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10 + 6));
  }

  @Test
  public void citiesShouldProduceArcherOnNorthTileAfter5Rounds(){

    assertNull(game.getUnitAt(new Position(1,1)));
    assertNull(game.getUnitAt (new Position(4,1)));

    game.advanceTurns(8);

    //unit on north red city
    game.advanceTurns(1);
    assertThat(game.getUnitAt(new Position(0,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6));

    //unit on north blue city
    game.advanceTurns(1);
    assertThat(game.getUnitAt(new Position(3,1)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6));

  }
//
  @Test
  public void citiesProduceArcherOnCorrectTileAfter6Rounds(){
    assertNull(game.getUnitAt(new Position(1,1)));
    assertNull(game.getUnitAt (new Position(4,1)));

    game.advanceTurns(10);
    //unit northeast red city
    game.advanceTurns(1);
    assertThat(game.getUnitAt(new Position(0,2)).getTypeString(),is(GameConstants.ARCHER));
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(),is(12 - 10  + 6 + 6 - 10 + 6 - 10 + 6));

    //legion northeast of blue, unit east of blue
    game.advanceTurns(1);
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

package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

/** Skeleton implementation of HotCiv.
 
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

  Release 2.0
  This is the hotfix for release 2.1
*/

public class GameImpl implements Game {

  //Object Fields
  private int mapRows = GameConstants.WORLDSIZE;
  private int mapCols = GameConstants.WORLDSIZE;
  private Player currPlayer = Player.RED;
  private int age = -4000;
  private Unit[][] unitTiles = new UnitImpl[mapRows][mapCols];
  private MapStrategy currMapStrat;
  private Map<Position,Tile> CivMap;
  private WinnerStrategy winnerStrategy;
  private AgingStrategy agingStrategy;
  private UnitActionStrategy unitActionStrategy;
  private Map<Position,CityImpl> citiesMap;

  //default Constructor
  public GameImpl(){
    this.currMapStrat = new AlphaMapStrategy();
    this.CivMap = currMapStrat.setMap();
    this.winnerStrategy = new AlphaCivWinnerStrategy();
    this.agingStrategy = new AlphaCivAgingStrategy();
    this.unitActionStrategy = new AlphaCivUnitActionStrategy();
    this.citiesMap = new AlphaStartCitiesStrategy().setStartCities();



  }
  public GameImpl(
          MapStrategy argMapStrategy,
          WinnerStrategy argWinnerStrategy,
          AgingStrategy argAgingStrategy,
          UnitActionStrategy argUnitActionStrategy,
          StartCitiesStrategy argStartCitiesStrategy
  )
  {
    this.currMapStrat = argMapStrategy;
    this.CivMap = currMapStrat.setMap();
    this.winnerStrategy = argWinnerStrategy;
    this.agingStrategy = argAgingStrategy;
    this.unitActionStrategy = argUnitActionStrategy;
    this.citiesMap = argStartCitiesStrategy.setStartCities();

    Player redPlayer = Player.RED;
    Player bluePlayer = Player.BLUE;

    unitTiles[2][0] = new UnitImpl(GameConstants.ARCHER, redPlayer);
    unitTiles[4][3] = new UnitImpl(GameConstants.SETTLER, redPlayer);
    unitTiles[3][2] = new UnitImpl(GameConstants.LEGION, bluePlayer);

  }

  //accessors
  public Tile getTileAt( Position p ) { return CivMap.get(p); }
  public Unit getUnitAt( Position p ) { return unitTiles[p.getRow()][p.getColumn()]; }
  public City getCityAt( Position p ) { return citiesMap.get(p); } //TODO update all functions to work with cityMap
  public Player getPlayerInTurn() {return currPlayer;}
  public Player getWinner() { return winnerStrategy.getWinner(age, citiesMap); }
  public int getAge() {return age;}

  //mutators
  public boolean moveUnit( Position from, Position to ) {

    if(getUnitAt(from).getMoveCount() == 0) { return false; }

    if(getUnitAt(to) == null) {
      unitTiles[to.getRow()][to.getColumn()]= unitTiles[from.getRow()][from.getColumn()];
      unitTiles[from.getRow()][from.getColumn()]= null;
    }
    else if(getUnitAt(to).getOwner() != getUnitAt(from).getOwner()){
      unitTiles[to.getRow()][to.getColumn()]= unitTiles[from.getRow()][from.getColumn()];
      unitTiles[from.getRow()][from.getColumn()]= null;
    }

    return true;
  }

  public void endOfTurn() {
    for (int i=0;i<mapRows;i++) {
      for (int j = 0; j < mapCols; j++) {
        if (citiesMap.get(new Position(i,j)) != null) {
          if (citiesMap.get(new Position(i,j)).getOwner() == currPlayer) {
            placeUnit(citiesMap.get(new Position(i,j)));
            citiesMap.get(new Position(i,j)).incrementTreasury(6);
          }
        }
      }
    }

    if (currPlayer == Player.RED)
      currPlayer = Player.BLUE;
    else {
      currPlayer = Player.RED;
      age = agingStrategy.getNewAge(age);
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {
    if(!citiesMap.get(p).getProduction().equals(unitType)){
      citiesMap.get(p).setProduction(unitType);

   }
  }
  public void performUnitActionAt( Position p ) {
    unitActionStrategy.performUnitActionAt(p, citiesMap, unitTiles);
  }

  public boolean placeUnit(CityImpl city){
    int c = city.getPosition().getColumn();
    int r = city.getPosition().getRow();

    //finding which tile to place the unit at
    int i = 0;
    int radius = 1;
    int ct = c;
    int rt = r;
    int tilesChecked = 0;
    while(unitTiles[rt][ct] != null) { //TODO iterate over map using iterator for loop
      if (i > 7){
        radius++;
        i=0;
      }
      switch(i){ //TODO possibly use utility for checking neighbors
        case 0: //north
          ct = c;
          rt = r - radius;
          break;
        case 1: //northeast
          ct = c + radius;
          rt = r - radius;
          break; //east
        case 2: //east
          ct = c + radius;
          rt = r;
          break;
        case 3: //southeast
          ct = c + radius;
          rt = r + radius;
          break;
        case 4: //south
          ct = c;
          rt = r + radius;
          break;
        case 5: //southwest
          ct = c - radius;
          rt = r + radius;
          break;
        case 6: //west
          ct = c - radius;
          rt = r;
          break;
        case 7: //northwest
          ct = c - radius;
          rt = r - radius;
          break;
      }
      if (rt < 0 || ct < 0 || rt >=16 || ct >= 16){
        rt = 0;
        ct = 0;
      }
      i++;
      tilesChecked++;
      if (tilesChecked > GameConstants.WORLDSIZE*GameConstants.WORLDSIZE+2)
        return false;
    }



    //remove the cost of the unitType from the city's treasury
    if (city.getProduction() == GameConstants.ARCHER && city.getTreasury() >= 10) //TODO use.equals for strings
      city.decrementTreasury(10);
    else if  (city.getProduction() == GameConstants.LEGION && city.getTreasury() >= 15)
      city.decrementTreasury(15);
    else if (city.getProduction() == GameConstants.SETTLER && city.getTreasury() >= 30)
      city.decrementTreasury(30);
    else
      return false;

  unitTiles[rt][ct] = new UnitImpl(city.getProduction(),currPlayer);
  return true;
  }

  public void advanceTurns( int numberOfTurns ) {
    for (int enfOfTurnsCalled=0; enfOfTurnsCalled<numberOfTurns; enfOfTurnsCalled++)
      { endOfTurn(); }
  }
}
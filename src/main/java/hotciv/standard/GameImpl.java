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
  private Tile[][] gameTiles = new TileImpl[mapRows][mapCols];
  private CityImpl[][] cities = new CityImpl[mapRows][mapCols];
  private Unit[][] unitTiles = new UnitImpl[mapRows][mapCols];
  private MapStrategy currMapStrat;
  private Map<Position,Tile> CivMap;
  private WinnerStrategy winnerStrategy;
  private AgingStrategy agingStrategy;
  private UnitActionStrategy unitActionStrategy;

  //constructor -  may add setMethods instead of passing the strategies as arguments
  public GameImpl(
          MapStrategy argMapStrategy,
          WinnerStrategy argWinnerStrategy,
          AgingStrategy argAgingStrategy,
          UnitActionStrategy argUnitActionStrategy
  )
  {
    this.currMapStrat = argMapStrategy;
    this.CivMap = currMapStrat.setMap();
    this.winnerStrategy = argWinnerStrategy;
    this.agingStrategy = argAgingStrategy;
    this.unitActionStrategy = argUnitActionStrategy;

/* comment out until strategy works correctly
  public GameImpl() {
    winnerStrategy = new AlphaCivWinnerStrategy();
    agingStrategy = new AlphaCivAgingStrategy();
    unitActionStrategy = new AlphaCivUnitActionStrategy();


    //Setting up map as all plains by default
    for (int currRow = 0; currRow < mapRows; currRow++) {
      for (int currCol = 0; currCol < mapCols; currCol++) {

        gameTiles[currRow][currCol] = new TileImpl(GameConstants.PLAINS);
      }
    }
    //Comment out adding non plains tiles to show TDD iteration

    gameTiles[1][0] = new TileImpl(GameConstants.OCEANS);
    gameTiles[0][1] = new TileImpl(GameConstants.HILLS);
    gameTiles[2][2] = new TileImpl(GameConstants.MOUNTAINS);
*/
    cities[1][1] = new CityImpl(Player.RED, new Position(1,1));
    cities[4][1] = new CityImpl(Player.BLUE, new Position(4,1));

    Player redPlayer = Player.RED;
    Player bluePlayer = Player.BLUE;

    //unitTiles[2][0] = new UnitImpl("archer", redPlayer);
    unitTiles[2][0] = new UnitImpl();
    unitTiles[2][0].setTypeString(GameConstants.ARCHER);
    unitTiles[2][0].setOwner(Player.RED);

    unitTiles[4][3] = new UnitImpl(GameConstants.SETTLER, redPlayer);
    unitTiles[3][2] = new UnitImpl(GameConstants.LEGION, bluePlayer);

    unitTiles[8][0] = new UnitImpl(); //origin
    unitTiles[8][1] = new UnitImpl(); //rightOrigin
  }

  //accessors
  public Tile getTileAt( Position p ) { return CivMap.get(p); }
  public Unit getUnitAt( Position p ) { return unitTiles[p.getRow()][p.getColumn()]; }
  public City getCityAt( Position p ) { return cities[p.getRow()][p.getColumn()]; }
  public Player getPlayerInTurn() {return currPlayer;}
  public Player getWinner() { return winnerStrategy.getWinner(age, cities); }
  public int getAge() {return age;}

  //mutators
  public boolean moveUnit( Position from, Position to ) {

    if(getUnitAt(to) == null) {
      unitTiles[to.getRow()][to.getColumn()]= unitTiles[from.getRow()][from.getColumn()];
      unitTiles[from.getRow()][from.getColumn()]= null;
    }
    else if(getUnitAt(to).getOwner() != getUnitAt(from).getOwner()){
      unitTiles[to.getRow()][to.getColumn()]= unitTiles[from.getRow()][from.getColumn()];
      unitTiles[from.getRow()][from.getColumn()]= null;
    }


    return false;}
  public void endOfTurn() {
    for (int i=0;i<mapRows;i++) {
      for (int j = 0; j < mapCols; j++) {
        if (cities[i][j] != null) {
          if (cities[i][j].getOwner() == currPlayer) {
            placeUnit(cities[i][j]);
            cities[i][j].incrementTreasury(6);
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
    if(cities[p.getRow()][p.getColumn()].getProduction() != unitType){
      cities[p.getRow()][p.getColumn()].setProduction(unitType);

   }
  }
  public void performUnitActionAt( Position p ) {}

  public boolean placeUnit(CityImpl city){
    int c = city.getPosition().getColumn();
    int r = city.getPosition().getRow();

    //finding which tile to place the unit at
    int i = 0;
    int radius = 1;
    int ct = c;
    int rt = r;
    while(unitTiles[rt][ct] != null) {
      if (i > 7){
        radius++;
        i=0;
      }
      switch(i){
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
      i++;
    }



    //remove the cost of the unitType from the city's treasury
    if (city.getProduction() == GameConstants.ARCHER && city.getTreasury() >= 10)
      city.incrementTreasury(-10);
    else if  (city.getProduction() == GameConstants.LEGION && city.getTreasury() >= 15)
      city.incrementTreasury(-15);
    else if (city.getProduction() == GameConstants.SETTLER && city.getTreasury() >= 30)
      city.incrementTreasury(-30);
    else
      return false;

  unitTiles[rt][ct] = new UnitImpl(city.getProduction(),currPlayer);
  return true;
  }
}
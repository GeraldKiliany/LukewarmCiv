package hotciv.standard;

import hotciv.framework.*;

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

*/

public class GameImpl implements Game {
  //matt
  private Player currPlayer = Player.RED;
  private int age = -4000;

  //Gerald
  int mapRows = 8, mapCols = 8; //Assume map for AlphaCiv is 8 by 8

 /* public Tile getTileAt(Position p) { return originTile}; //The "Fake it" version of method, used in TDD process
 private Tile originTile = new TileImpl("plains"); */

  public Tile getTileAt( Position p ) { return gameTiles[p.getRow()][p.getColumn()]; }
  private Tile[][] gameTiles = new Tile[mapRows][mapCols];
  //matt
  private CityImpl[][] cities = new CityImpl[mapRows][mapCols];

  public GameImpl() {
    for (int currRow = 0; currRow < mapRows; currRow++) {
      for (int currCol = 0; currCol < mapCols; currCol++) {
        gameTiles[currRow][currCol] = new TileImpl("plains");
      }
    }
    //Comment out adding non plains tiles to show TDD iteration
    gameTiles[1][0] = new TileImpl("ocean");
    gameTiles[0][1] = new TileImpl("hills");
    gameTiles[2][2] = new TileImpl("mountain");
    cities[1][1] = new CityImpl(Player.RED);
    cities[4][1] = new CityImpl(Player.BLUE);

    Player redPlayer = Player.RED;
    Player bluePlayer = Player.BLUE;

    //unitTiles[2][0] = new UnitImpl("archer", redPlayer);
    unitTiles[2][0] = new UnitImpl();
    unitTiles[2][0].setTypeString("archer");
    unitTiles[2][0].setOwner(Player.RED);

    unitTiles[4][3] = new UnitImpl("settler", redPlayer);
    unitTiles[3][2] = new UnitImpl("legion", bluePlayer);
  }

  //Ben
  public Unit getUnitAt( Position p ) { return unitTiles[p.getRow()][p.getColumn()]; }
  private Unit[][] unitTiles = new Unit[mapRows][mapCols];

  //matt
  public City getCityAt( Position p ) { return cities[p.getRow()][p.getColumn()]; }
  public Player getPlayerInTurn() {return currPlayer;}
  public Player getWinner() {
    return (age==-3000)?Player.RED:null;
  }
  public int getAge() {return age;}


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

  //matt
  public void endOfTurn() {
    for (int i=0;i<mapRows;i++){
      for (int j=0;j<mapCols;j++){
        if (cities[i][j] != null && cities[i][j].getOwner() == currPlayer)
          cities[i][j].incrementTreasury(6);
      }
    }
    if (currPlayer == Player.RED)
      currPlayer = Player.BLUE;
    else {
      currPlayer = Player.RED;
      age+=100;
    }

  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}

}


//end of file
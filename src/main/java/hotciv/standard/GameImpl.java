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
  private CityImpl[][] cities = new CityImpl[8][8];

  //Gerald
  int mapRows = 8, mapCols = 8; //Assume map for AlphaCiv is 8 by 8
  private Tile originTile = new TileImpl("plains");
  public Tile getTileAt( Position p ) { return gameTiles[p.getRow()][p.getColumn()]; }
  private Tile gameTiles[][] = new Tile[mapRows][mapCols];

  public GameImpl() {

    for (int currRow = 0; currRow < mapRows; currRow++) {
      for (int currCol = 0; currCol < mapCols; currCol++) {
        gameTiles[currRow][currCol] = new TileImpl("plains");
      }
    }
    gameTiles[1][0] = new TileImpl("ocean");
    gameTiles[0][1] = new TileImpl("hills");
    gameTiles[2][2] = new TileImpl("mountain");
    cities[1][1] = new CityImpl(Player.RED);
    cities[4][1] = new CityImpl(Player.BLUE);
  }




  //Ben
  private Player testUnitPlayer = Player.RED;
  private Unit originUnit = new UnitImpl("archer", testUnitPlayer);
  public Unit getUnitAt( Position p ) { return originUnit; }

  //matt
  public City getCityAt( Position p ) { return cities[p.getRow()][p.getColumn()]; }
  public Player getPlayerInTurn() {return currPlayer;}
  public Player getWinner() { return (age==-3000)?Player.RED:null; }
  public int getAge() {return age;}


  public boolean moveUnit( Position from, Position to ) {return false;}

  //matt
  public void endOfTurn() {
    age+=100;
    if (currPlayer == Player.RED)
      currPlayer = Player.BLUE;
    else
      currPlayer = Player.RED;
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}

}


//end of file
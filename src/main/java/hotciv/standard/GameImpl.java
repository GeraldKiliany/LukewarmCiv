package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
  private Player currPlayer = Player.RED;
  private int age = -4000;
  private MapStrategy mapStrategy;
  private WinnerStrategy winnerStrategy;
  private AgingStrategy agingStrategy;
  private UnitActionStrategy unitActionStrategy;
  private Map<Position,CityImpl> cities;
  private Map<Position, Unit> units = new HashMap<Position, Unit>();
  private Map<Position,Tile> tiles;
  private AttackingStrategy attackStrategy;

  private GameFactory factory;

  static public int redAttacksWon = 0;
  static public int blueAttacksWon = 0;

  static public int numberOfRoundsPassed = 0;

  //default Constructor
  /*public GameImpl(){
    this.mapStrategy = new AlphaCivMapStrategy();
    this.tiles = mapStrategy.setMap();
    this.winnerStrategy = new AlphaCivWinnerStrategy();
    this.agingStrategy = new AlphaCivAgingStrategy();
    this.unitActionStrategy = new AlphaCivUnitActionStrategy();
    this.cities = new AlphaCivStartCitiesStrategy().setStartCities();
    this.units = new AlphaCivStartUnitsStrategy().setStartUnits();

  }*/
  //default Constructor
  public GameImpl(){
    this.factory = new AlphaCivFactory();
    this.mapStrategy = factory.createMapStrategy();
    this.tiles = mapStrategy.setMap();
    this.winnerStrategy = factory.createWinnerStrategy();
    this.agingStrategy = factory.createAgingStrategy();;
    this.unitActionStrategy = factory.createUnitActionStrategy();
    this.cities = factory.createStartCitiesStrategy().setStartCities();
    this.units = factory.createStartUnitsStrategy().setStartUnits();
  }

  public GameImpl( GameFactory factory ) {
    this.factory = factory;
    this.mapStrategy = factory.createMapStrategy();
    this.tiles = mapStrategy.setMap();
    this.winnerStrategy = factory.createWinnerStrategy();
    this.agingStrategy = factory.createAgingStrategy();;
    this.unitActionStrategy = factory.createUnitActionStrategy();
    this.cities = factory.createStartCitiesStrategy().setStartCities();
    this.units = factory.createStartUnitsStrategy().setStartUnits();
    this.attackStrategy = factory.createAttackingStrategy();
  }
  /*public GameImpl(
          MapStrategy argMapStrategy,
          WinnerStrategy argWinnerStrategy,
          AgingStrategy argAgingStrategy,
          UnitActionStrategy argUnitActionStrategy,
          StartCitiesStrategy argStartCitiesStrategy,
          StartUnitsStrategy argStartUnitsStrategy
  )
  {
    this.mapStrategy = argMapStrategy;
    this.tiles = mapStrategy.setMap();
    this.winnerStrategy = argWinnerStrategy;
    this.agingStrategy = argAgingStrategy;
    this.unitActionStrategy = argUnitActionStrategy;
    this.cities = argStartCitiesStrategy.setStartCities();
    this.units = argStartUnitsStrategy.setStartUnits();
  }*/

  //accessors
  public Tile getTileAt( Position p ) { return tiles.get(p); }
  public Unit getUnitAt( Position p ) { return units.get(p); }
  public City getCityAt( Position p ) { return cities.get(p); } //TODO update all functions to work with cityMap
  public Player getPlayerInTurn() {return currPlayer;}
  public Player getWinner() { return winnerStrategy.getWinner(age, cities); }
  public int getAge() {return age;}
  private int unitCost(String unitType) {
    if (unitType.equals(GameConstants.ARCHER)) {
      return 10;
    }
    else if (unitType.equals(GameConstants.LEGION)) {
      return 15;
    }
    else return 30;
  }

  //mutators
  public boolean moveUnit( Position from, Position to ) {
//TODO: Refactor to use variable for units
    if(getUnitAt(from).getMoveCount() == 0) { return false; }

    if(getUnitAt(to) == null) {
      units.put(to,units.get(from));
      units.put(from, null);
    }
    else if(getUnitAt(to).getOwner() != getUnitAt(from).getOwner()){
      boolean successfulAttack = attackStrategy.attack(getUnitAt(from),getUnitAt(to));
      if(successfulAttack) {
        if (getUnitAt(from).getOwner() == Player.RED) {
          redAttacksWon++;
        } else if (getUnitAt(to).getOwner() == Player.BLUE) {
          blueAttacksWon++;
        }

        units.put(to, units.get(from));
        units.put(from, null);
      }
    }

    return true;
  }
  public void endOfTurn() {
    for(Position p : cities.keySet()) {
      CityImpl currCity = cities.get(p);
      if (currCity.getOwner() == currPlayer) {
        placeUnit(currCity, p);
        currCity.incrementTreasury(6);
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
    if(!cities.get(p).getProduction().equals(unitType)){
      cities.get(p).setProduction(unitType);

   }
  }
  public void performUnitActionAt( Position p ) {
    unitActionStrategy.performUnitActionAt(p, this);
  }
  public boolean placeUnit(CityImpl city, Position p){
    String currUnit = city.getProduction();
    int currUnitCost = unitCost(currUnit);
    boolean sufficientTreasury = (city.getTreasury() >= currUnitCost);
    if(sufficientTreasury){
      if(units.get(p) == null) {
        city.decrementTreasury(currUnitCost);
        units.put(p, new UnitImpl(currUnit,currPlayer));
        return true;
      }
      for(Position neighbors : Utility.get8neighborhoodOf(p)) {
        if (!units.containsKey(neighbors)){
          //remove the cost of the unitType from the city's treasury
          city.decrementTreasury(currUnitCost);
          units.put(neighbors, new UnitImpl(currUnit, currPlayer));
          return true;

        }
      }
    }
    return false;
  }
  public void advanceTurns( int numberOfTurns ) {
    for (int enfOfTurnsCalled=0; enfOfTurnsCalled<numberOfTurns; enfOfTurnsCalled++)
      { endOfTurn(); }
  }
  public void placeCity( Position p, Player owner ) { cities.put(p, new CityImpl(owner, p)); }
  public void removeUnit( Position p ) { units.remove(p); }

  public int rollDie(){ return (int) Math.random()*6;}
}
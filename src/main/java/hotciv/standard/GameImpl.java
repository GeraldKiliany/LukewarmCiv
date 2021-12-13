package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
//Release 3.0
import java.util.Map;
import java.util.Objects;


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

  private GameObserver observer;
  private Tile tileFocus;

  private int redAttacksWon = 0;
  private int blueAttacksWon = 0;
  private int numberOfRoundsPassed = 0;

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
//TODO rename all strategies to use descriptive names & not civ version (maybe)
  //accessors
  public Tile getTileAt( Position p ) { return tiles.get(p); }
  public Unit getUnitAt( Position p ) { return units.get(p); }
  public City getCityAt( Position p ) { return cities.get(p); } //TODO update all functions to work with cityMap
  public Player getPlayerInTurn() {return currPlayer;}
  public Player getWinner() { return winnerStrategy.getWinner(age, cities, this); }
  public int getAge() {return age;}
  private int unitCost(String unitType) {
    if (unitType.equals(GameConstants.ARCHER)) {
      return 10;
    } else if (unitType.equals(GameConstants.LEGION)) {
      return 15;
    } else if (unitType.equals(GameConstants.SETTLER)) {
      return 30;
    } else {
      return 60; //ufo
    }
  }

  //mutators
  public boolean moveUnit( Position from, Position to ) {

    Unit fromUnit = getUnitAt(from);
    Unit toUnit = getUnitAt(to);
    Tile toTile = getTileAt(to);

    int fromRow = from.getRow(), fromCol = from.getColumn();
    int toRow = to.getRow(), toCol = to.getColumn();
    int distRow = Math.abs(toRow - fromRow), distCol = Math.abs(toCol - fromCol);

    boolean sameLocation = (distRow == 0 && distCol == 0);
    boolean leftRight = (distRow == 1 && distCol == 0);
    boolean upDown = (distRow == 0 && distCol == 1);

    if (sameLocation) { return false; }
    if (!leftRight && !upDown) { return false; }

    //Check that unit at from can move to specified tile
    if(fromUnit == null) { return false; }
    boolean wrongOwner = (fromUnit.getOwner() != getPlayerInTurn());
    if (wrongOwner) {return false;}
    if(fromUnit.getMoveCount() == 0) { return false; }
    boolean toOceanTile = toTile.getTypeString().equals(GameConstants.OCEANS);
    boolean toMountainTile = toTile.getTypeString().equals(GameConstants.MOUNTAINS);
    boolean isUFO = fromUnit.getTypeString().equals(GameConstants.UFO);

    if(!isUFO){
      if(toOceanTile || toMountainTile){
        return false;
      }
    }
    //Check if to location has a unit and attack if is not owned by player
    if(getUnitAt(to) == null) {
      if (fromUnit.getTypeString().equals(GameConstants.UFO)){
      }
      else if (tiles.get(to).getTypeString().equals(GameConstants.FOREST)){
        tiles.put(to, new TileImpl(GameConstants.PLAINS));
      }
      else if (getCityAt(to) != null) {
        getCityAt(to).setOwner(getUnitAt(from).getOwner());
      }
      fromUnit.setMoveCount(fromUnit.getMoveCount()-1);
      units.put(to,units.get(from));
      units.remove(from);
      if (observer != null) {
        observer.worldChangedAt(from);
        observer.worldChangedAt(to);
      }
    }
    else if(toUnit.getOwner() == fromUnit.getOwner()){ return false; }
    else if(toUnit.getOwner() != fromUnit.getOwner()){
      boolean successfulAttack = attackStrategy.attack(this, from, to);
      if (fromUnit.getTypeString().equals(GameConstants.UFO)) {

      }
      if (!successfulAttack) {
        units.put(from, units.get(to));
        units.put(to, units.get(from));
        units.remove(from);
        if (observer != null) {
          observer.worldChangedAt(from);
          observer.worldChangedAt(to);
        }
        return false;
      }
      else {
        if (getCityAt(to) != null) {
          getCityAt(to).setOwner(getUnitAt(from).getOwner());
          if (observer != null) {
            observer.worldChangedAt(to); //city changes player color
          }
        }

        if (factory.factoryType().equals("ZetaCivFactory")) {
          if (numberOfRoundsPassed > 20) {
            if (fromUnit.getOwner() == Player.RED) {
              redAttacksWon++;
            } else if (fromUnit.getOwner() == Player.BLUE) {
              blueAttacksWon++;
            }
          }
        } else {
          if (fromUnit.getOwner() == Player.RED) {
            redAttacksWon++;
          } else if (fromUnit.getOwner() == Player.BLUE) {
            blueAttacksWon++;
          }
        }

        units.put(to, units.get(from));
        units.remove(from);
        if (observer != null) {
          observer.worldChangedAt(from);
          observer.worldChangedAt(to);
        }
      }
    }

    return true;
  }
  public void endOfTurn() {
    //update GUI

    for(Position p : cities.keySet()) {
      CityImpl currCity = cities.get(p);
      if (currCity.getOwner() == currPlayer) {
        placeUnit(currCity, p);
        currCity.incrementTreasury(6);
      }
    }
    for(Position p : units.keySet()) {
      Unit currUnit = units.get(p);
      if(currUnit.getTypeString().equals(GameConstants.UFO)){
        currUnit.setMoveCount(2);
      }
      else if(currUnit.getDefensiveStrength() < 5){
        currUnit.setMoveCount(1);
      }
    }
    if (currPlayer == Player.RED)
      currPlayer = Player.BLUE;
    else {
      currPlayer = Player.RED;
      age = agingStrategy.getNewAge(age);
      numberOfRoundsPassed++;
    }
    if (observer != null) {
      observer.turnEnds(currPlayer, age);
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
        if (observer != null) {
          observer.worldChangedAt(p);
        }
        return true;
      }
      for(Position neighbors : Utility.get8neighborhoodOf(p)) {
        if (!units.containsKey(neighbors)){
          //remove the cost of the unitType from the city's treasury
          city.decrementTreasury(currUnitCost);
          units.put(neighbors, new UnitImpl(currUnit, currPlayer));
          if (observer != null) {
            observer.worldChangedAt(neighbors);
          }
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
  public void placeCity( Position p, Player owner ) {
    cities.put(p, new CityImpl(owner, p));
    if (observer != null) {
      observer.worldChangedAt(p);
    }
  }
  public void placeUnitManually ( Position p, String unitType, Player owner ) {
    units.put(p, new UnitImpl(unitType, owner));
  }

  public void removeUnit( Position p ) {
    units.remove(p);
    if (observer != null) {
      observer.worldChangedAt(p);
    }
  }
  public void removeCity( Position p ) {
    cities.remove(p);
    if (observer != null) {
      observer.worldChangedAt(p);
    }
  }
  public void replaceTile( Position p, String tileType ) {
    tiles.replace(p, new TileImpl(tileType));
    if (observer != null) {
      observer.worldChangedAt(p);
    }
  }

  public void decrementCityPopulation( Position p ) {
    City city = getCityAt(p);
    city.setSize( city.getSize() - 1 );
  }

  public int getRedAttacksWon() { return redAttacksWon; }
  public int getBlueAttacksWon() { return blueAttacksWon; }
  public int getNumberOfRoundsPassed() { return numberOfRoundsPassed; }

  @Override
  public void addObserver(GameObserver observer) {
    this.observer = observer;
  }

  @Override
  public void setTileFocus(Position position) {
    tileFocus = tiles.get(position);
    if (observer != null)
      observer.tileFocusChangedAt(position);
  }

}
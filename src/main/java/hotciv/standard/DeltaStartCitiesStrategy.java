package hotciv.standard;

import hotciv.framework.*;
import java.util.*;



public class DeltaStartCitiesStrategy implements StartCitiesStrategy{

    Map<Position,CityImpl> startCitiesMap = new HashMap<Position, CityImpl>();
    public Map<Position,CityImpl> setStartCities(){
        startCitiesMap.put(new Position(8,12), new CityImpl(Player.RED, new Position(8,12)) );
        startCitiesMap.put(new Position(4,5), new CityImpl(Player.BLUE, new Position(4,5)) );


        return startCitiesMap;
    }



}

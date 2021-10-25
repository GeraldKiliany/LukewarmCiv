package hotciv.standard;

import hotciv.framework.*;
import java.util.*;

public class AlphaCivStartCitiesStrategy implements StartCitiesStrategy {
    Map<Position,CityImpl> startCitiesMap = new HashMap<Position, CityImpl>();
    public Map<Position,CityImpl> setStartCities(){
        startCitiesMap.put(new Position(1,1), new CityImpl(Player.RED, new Position(1,1)) );
        startCitiesMap.put(new Position(4,1), new CityImpl(Player.BLUE, new Position(4,1)) );


        return startCitiesMap;
    }
}

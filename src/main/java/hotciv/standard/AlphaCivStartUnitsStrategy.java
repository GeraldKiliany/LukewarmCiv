package hotciv.standard;

import hotciv.framework.*;
import java.util.*;

public class AlphaCivStartUnitsStrategy implements StartUnitsStrategy {
    Map<Position,Unit> startUnitsMap = new HashMap<Position, Unit>();
    public Map<Position,Unit> setStartUnits(){
        startUnitsMap.put(new Position(2,0), new UnitImpl(GameConstants.ARCHER, Player.RED));
        startUnitsMap.put(new Position(4,3), new UnitImpl(GameConstants.SETTLER, Player.RED));
        startUnitsMap.put(new Position(3,2), new UnitImpl(GameConstants.LEGION, Player.BLUE));

        return startUnitsMap;
    }
}

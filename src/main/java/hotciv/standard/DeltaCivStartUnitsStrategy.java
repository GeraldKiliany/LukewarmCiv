package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

public class DeltaCivStartUnitsStrategy implements StartUnitsStrategy {
    Map<Position, Unit> startUnitsMap = new HashMap<Position, Unit>();

    public Map<Position, Unit> setStartUnits() {
        startUnitsMap.put(new Position(3, 8), new UnitImpl(GameConstants.ARCHER, Player.RED));
        startUnitsMap.put(new Position(5, 5), new UnitImpl(GameConstants.SETTLER, Player.RED));
        startUnitsMap.put(new Position(4, 4), new UnitImpl(GameConstants.LEGION, Player.BLUE));

        return startUnitsMap;
    }
}


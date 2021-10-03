package hotciv.framework;

import hotciv.standard.CityImpl;

import java.util.Map;

public interface UnitActionStrategy {
    public void performUnitActionAt(Position p, Map<Position,CityImpl> argCitiesMap, Unit[][] unitTiles);
}

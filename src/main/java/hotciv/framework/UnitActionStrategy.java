package hotciv.framework;

import hotciv.standard.CityImpl;

public interface UnitActionStrategy {
    public void performUnitActionAt(Position p, CityImpl[][] cities, Unit[][] unitTiles);
}

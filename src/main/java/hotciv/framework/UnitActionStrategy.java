package hotciv.framework;

import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

import java.util.Map;

public interface UnitActionStrategy {
    public void performUnitActionAt(Position p, Map<Position,Unit> unitTiles, GameImpl game);
}

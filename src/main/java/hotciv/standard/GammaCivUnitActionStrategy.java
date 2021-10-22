package hotciv.standard;

import hotciv.framework.*;

import java.util.Map;

public class GammaCivUnitActionStrategy implements UnitActionStrategy {
    public void performUnitActionAt(Position p, Map<Position,CityImpl> argCitiesMap, Map<Position, Unit> unitTiles) {
        int r = p.getRow();
        int c = p.getColumn();
        Unit unit = unitTiles.get(p);
        Map<Position, CityImpl> citiesMap = argCitiesMap;

        if (unit == null) { return; } //does nothing if there is no unit

        String unitType = unit.getTypeString();

        switch (unitType) {
            case GameConstants.SETTLER:
                unitTiles.put(p, null);
                citiesMap.put(p, new CityImpl(unit.getOwner(), p));
                break;
            case GameConstants.ARCHER:
                //fortification
                if (unit.getMoveCount() != 0) {
                    unit.setDefensiveStrength(unit.getDefensiveStrength() * 2);
                    unit.setMoveCount(0);
                }
                //de-fortification
                else {
                    unit.setDefensiveStrength(unit.getDefensiveStrength() / 2);
                    unit.setMoveCount(1);
                }
                break;
            default:
                break;
        }

        return;
    }
}


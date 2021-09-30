package hotciv.standard;

import hotciv.framework.*;

public class GammaCivUnitActionStrategy implements UnitActionStrategy {
    public void performUnitActionAt(Position p, CityImpl[][] cities, Unit[][] unitTiles) {
        int r = p.getRow();
        int c = p.getColumn();
        Unit unit = unitTiles[r][c];

        if (unit == null) { return; } //does nothing if there is no unit

        String unitType = unit.getTypeString();

        switch (unitType) {
            case GameConstants.SETTLER:
                unitTiles[r][c] = null;
                cities[r][c] = new CityImpl(unit.getOwner(), p);
                break;
            case GameConstants.ARCHER:
                unit.setDefensiveStrength(unit.getDefensiveStrength()*2);
                unit.setMoveCount(0);
                break;
            default:
                break;
        }
        //System.out.println(unitType);

        return;
    }
}


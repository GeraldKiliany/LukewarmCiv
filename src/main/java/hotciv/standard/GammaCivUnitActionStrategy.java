package hotciv.standard;

import hotciv.framework.*;

public class GammaCivUnitActionStrategy implements UnitActionStrategy {
    public void performUnitActionAt(Position p, CityImpl[][] cities, Unit[][] unitTiles) {
        String unitType = unitTiles[p.getRow()][p.getColumn()].getTypeString();

        switch (unitType) {
            case GameConstants.SETTLER:
                break;
            case GameConstants.ARCHER:
                break;
            default:
                break;
        }
        //System.out.println(unitType);

        return;
    }
}


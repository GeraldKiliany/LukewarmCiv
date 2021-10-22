package hotciv.standard;

import hotciv.framework.*;

import java.util.Map;

public class GammaCivUnitActionStrategy implements UnitActionStrategy {
    public void performUnitActionAt(Position p, Map<Position, Unit> units, GameImpl game) {
        int r = p.getRow();
        int c = p.getColumn();
        Unit unit = units.get(p);

        if (unit != null) {
            String unitType = unit.getTypeString();

            switch (unitType) {
                case GameConstants.SETTLER:
                    units.remove(p);
                    game.placeCity(p, unit.getOwner());
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
        }

        return;
    }
}


package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.framework.UnitActionStrategy;

public class ThetaCivActionStrategy implements UnitActionStrategy {
    public void performUnitActionAt(Position p, GameImpl game) {
        Unit unit = game.getUnitAt(p);

        if (unit != null) {
            String unitType = unit.getTypeString();

            switch (unitType) {
                case GameConstants.SETTLER:
                    game.removeUnit(p);
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
                case GameConstants.UFO:
                    if (game.getCityAt(p) != null) {
                        game.decrementCityPopulation(p);
                        if (game.getCityAt(p).getSize() == 0) { game.removeCity(p); }
                    }
                default:
                    break;
            }
        }

        return;
    }
}

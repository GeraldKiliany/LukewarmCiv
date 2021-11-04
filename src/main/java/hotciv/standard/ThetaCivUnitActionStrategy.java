package hotciv.standard;

import hotciv.framework.*;

public class ThetaCivUnitActionStrategy implements UnitActionStrategy {
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
                    } else {
                        if (game.getTileAt(p).getTypeString().equals(GameConstants.FOREST)) {
                            game.replaceTile(p, GameConstants.PLAINS);
                        }
                    }
                default:
                    break;
            }
        }

        return;
    }
}

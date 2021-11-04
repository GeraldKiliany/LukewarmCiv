package hotciv.standard;

import hotciv.framework.*;

public class SemiCivFactory implements GameFactory {
    public MapStrategy createMapStrategy() {
        return new DeltaCivMapStrategy();
    }

    public WinnerStrategy createWinnerStrategy() {
        return new EpsilonCivWinnerStrategy();
    }

    public AgingStrategy createAgingStrategy() {
        return new BetaCivAgingStrategy();
    }

    public UnitActionStrategy createUnitActionStrategy() {
        return new GammaCivUnitActionStrategy();
    }

    public StartCitiesStrategy createStartCitiesStrategy() {
        return new DeltaCivStartCitiesStrategy();
    }

    public StartUnitsStrategy createStartUnitsStrategy() {
        return new DeltaCivStartUnitsStrategy();
    }

    public AttackingStrategy createAttackingStrategy() {
        return new EpsilonCivAttackingStrategy();
    }

    public String factoryType() {
        return "SemiCivFactory";
    }
}
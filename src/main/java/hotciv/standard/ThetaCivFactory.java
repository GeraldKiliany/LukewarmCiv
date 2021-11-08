package hotciv.standard;

import hotciv.framework.*;

public class ThetaCivFactory implements GameFactory{
    public MapStrategy createMapStrategy() { return new DeltaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return new AlphaCivWinnerStrategy(); }
    public AgingStrategy createAgingStrategy() { return new AlphaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new ThetaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new AlphaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy() { return new EpsilonCivAttackingStrategy(); }
    public String factoryType() { return "GammaCivFactory"; }
}

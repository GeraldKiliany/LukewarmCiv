package hotciv.standard;

import hotciv.framework.*;

public class DeltaCivFactory implements GameFactory  {
    public MapStrategy createMapStrategy() { return new DeltaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return null; }
    public AgingStrategy createAgingStrategy() { return null; }
    public UnitActionStrategy createUnitActionStrategy() { return null; }
    public StartCitiesStrategy createStartCitiesStrategy() { return new DeltaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy() { return new AlphaCivAttackingStrategy(); }
    public String factoryType() { return "DeltaCivFactory"; }
}

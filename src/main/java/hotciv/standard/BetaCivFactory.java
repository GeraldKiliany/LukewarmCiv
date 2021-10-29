package hotciv.standard;

import hotciv.framework.*;

public class BetaCivFactory implements GameFactory  {
    public MapStrategy createMapStrategy() { return new AlphaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return new BetaCivWinnerStrategy(); }
    public AgingStrategy createAgingStrategy() { return new BetaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new AlphaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new AlphaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy() { return new AlphaCivAttackingStrategy(); }
}

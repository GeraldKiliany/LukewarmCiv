package hotciv.standard;

import hotciv.framework.*;

public class ZetaCivFactory implements GameFactory  {
    public MapStrategy createMapStrategy() { return new AlphaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() {
        return new ZetaCivWinnerStrategy( new BetaCivWinnerStrategy(), new EpsilonCivWinnerStrategy() );
    }
    public AgingStrategy createAgingStrategy() { return new AlphaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new AlphaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new AlphaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy() { return new AlphaCivAttackingStrategy(); }
}

package hotciv.standard;

import hotciv.framework.*;

public class AlphaCivFactory implements GameFactory  {
    public MapStrategy createMapStrategy() { return new AlphaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return new AlphaCivWinnerStrategy(); }
    public AgingStrategy createAgingStrategy() { return new AlphaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new AlphaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new AlphaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
}

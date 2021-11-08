package hotciv.standard;

import hotciv.framework.*;

public class EpsilonCivFactory implements GameFactory  {
    boolean useTestStub;
    public EpsilonCivFactory() {this.useTestStub = true;}
    public EpsilonCivFactory(boolean useTestStub) {this.useTestStub = useTestStub;}
    public MapStrategy createMapStrategy() { return new AlphaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return new EpsilonCivWinnerStrategy(); }
    public AgingStrategy createAgingStrategy() { return new AlphaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new AlphaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new AlphaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy(){ return new EpsilonCivAttackingStrategy(useTestStub); }
    public String factoryType() { return "EpsilonCivFactory"; }
}
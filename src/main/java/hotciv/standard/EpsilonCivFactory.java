package hotciv.standard;

import hotciv.framework.*;

public class EpsilonCivFactory implements GameFactory  {
    boolean useTestStub;
    public EpsilonCivFactory(boolean useTestStub) {this.useTestStub = useTestStub;}
    public EpsilonCivFactory() {this.useTestStub = true;}
    public boolean getUseTestStub(){return useTestStub;}
    public MapStrategy createMapStrategy() { return new AlphaCivMapStrategy(); }
    public WinnerStrategy createWinnerStrategy() { return new EpsilonCivWinnerStrategy(); }
    public AgingStrategy createAgingStrategy() { return new AlphaCivAgingStrategy(); }
    public UnitActionStrategy createUnitActionStrategy() { return new AlphaCivUnitActionStrategy(); }
    public StartCitiesStrategy createStartCitiesStrategy() { return new AlphaCivStartCitiesStrategy(); }
    public StartUnitsStrategy createStartUnitsStrategy() { return new AlphaCivStartUnitsStrategy(); }
    public AttackingStrategy createAttackingStrategy(){ return new EpsilonCivAttackingStrategy(useTestStub); }
}
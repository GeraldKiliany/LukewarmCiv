package hotciv.framework;

/**
 * The factory for creating the objects that configure
 * a game for the particular civilization to operate in.
 * */

public interface GameFactory {
    public MapStrategy createMapStrategy();
    public WinnerStrategy createWinnerStrategy();
    public AgingStrategy createAgingStrategy();
    public UnitActionStrategy createUnitActionStrategy();
    public StartCitiesStrategy createStartCitiesStrategy();
    public StartUnitsStrategy createStartUnitsStrategy();
    public AttackingStrategy createAttackingStrategy();
}

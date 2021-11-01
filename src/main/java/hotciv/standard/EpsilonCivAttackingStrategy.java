package hotciv.standard;
import hotciv.framework.*;

public class EpsilonCivAttackingStrategy implements AttackingStrategy {
    int attackerSupport;
    int defenderSupport;

    int attackerTerrain;
    int defenderTerrain;
    int attackerStrength;
    int defenderStrength;

    AttackDecisionStrategy attackWinnerStrategy;

    public EpsilonCivAttackingStrategy(){
        attackWinnerStrategy = new EpsilonCivFixedAttackDecisionStrategy();
    }
    public EpsilonCivAttackingStrategy(boolean useTestStub){
        if (useTestStub){
            attackWinnerStrategy = new EpsilonCivFixedAttackDecisionStrategy();
        }
        else{
            attackWinnerStrategy = new EpsilonCivRandomAttackDecisionStrategy();
        }
    }
    public boolean attack(Game game, Position from, Position to) {

        //return attackWinnerStrategy.determineWinner(game, from, to);

        attackerSupport = Utility2.getFriendlySupport(game,from,game.getUnitAt(from).getOwner());
        defenderSupport = Utility2.getFriendlySupport(game,to,game.getUnitAt(to).getOwner());
        attackerTerrain = Utility2.getTerrainFactor(game,from);
        defenderTerrain = Utility2.getTerrainFactor(game,to);

        Unit attacker = game.getUnitAt(from);
        Unit defender = game.getUnitAt(to);

        attackerStrength = (attacker.getAttackingStrength()+attackerSupport)*attackerTerrain;
        defenderStrength = (defender.getDefensiveStrength()+defenderSupport)*defenderTerrain;


        //roll die, change with stub
        boolean outcome = (attackerStrength*rollDie() > (defenderStrength*rollDie()));
        return outcome;
    }
    public int rollDie(){ return (int) (Math.random()*6);}
}

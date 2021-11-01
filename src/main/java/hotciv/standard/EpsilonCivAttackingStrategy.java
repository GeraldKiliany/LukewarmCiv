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



    public boolean attack(Game game, Position from, Position to) {

        //return attackWinnerStrategy.determineWinner(game, from, to);

        attackerSupport = Utility2.getFriendlySupport(game,from,game.getUnitAt(from).getOwner());
        defenderSupport = Utility2.getFriendlySupport(game,to,game.getUnitAt(to).getOwner());
        attackerTerrain = Utility2.getTerrainFactor(game,to);
        defenderTerrain = Utility2.getTerrainFactor(game,to);

        Unit attacker = game.getUnitAt(from);
        Unit defender = game.getUnitAt(to);

        attackerStrength = (attacker.getAttackingStrength()+attackerSupport)*attackerTerrain;
        defenderStrength = (defender.getDefensiveStrength()+defenderSupport)*defenderTerrain;


        boolean outcome = (attackerStrength*(Math.random()*6)) > (defenderStrength*(Math.random()*6));
        return outcome;
    }



    public int getAttackerSupport(){
        return attackerSupport;
    }
    public int getDefenderSupport(){
        return defenderSupport;
    }
    public int getAttackerStrength() { return attackerStrength; }
    public int getDefenderStrength() { return defenderStrength; }
    //public boolean strongerAttacker() { return attackerStrength > defenderStrength; }
    public int rollDie(){ return (int) (Math.random()*6);}
}

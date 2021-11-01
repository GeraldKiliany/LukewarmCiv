package hotciv.standard;
import hotciv.framework.*;

public class EpsilonCivFixedAttackDecisionStrategy implements AttackDecisionStrategy{



    public boolean determineWinner(Game game, Position from, Position to){
        int attackerSupport = Utility2.getFriendlySupport(game,from,game.getUnitAt(from).getOwner());
        int defenderSupport = Utility2.getFriendlySupport(game,to,game.getUnitAt(to).getOwner());
        int attackerTerrain = Utility2.getTerrainFactor(game,to);
        int defenderTerrain = Utility2.getTerrainFactor(game,to);

        Unit attacker = game.getUnitAt(from);
        Unit defender = game.getUnitAt(to);

        int attackerStrength = (attacker.getAttackingStrength()+attackerSupport)*attackerTerrain;
        int defenderStrength = (defender.getDefensiveStrength()+defenderSupport)*defenderTerrain;


        boolean outcome = attackerStrength > defenderStrength;
        return outcome;
    }
}

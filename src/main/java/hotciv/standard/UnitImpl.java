package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public  class UnitImpl implements Unit {

    String type;
    Player owner;
    int moveCount;
    int defensiveStrength;
    int attackingStrength;

    public UnitImpl () {
        type = null;
        owner = null;
        moveCount = 0;
        defensiveStrength = 0;
        attackingStrength = 0;
    }

    public UnitImpl(String unitType, Player unitOwner) {
        type = unitType;
        owner = unitOwner;

        switch (unitType) {
            case GameConstants.ARCHER:
                moveCount = 1;
                defensiveStrength = 3;
                attackingStrength = 2;
                break;
            case GameConstants.LEGION:
                moveCount = 1;
                defensiveStrength = 2;
                attackingStrength = 4;
                break;
            case GameConstants.SETTLER:
                moveCount = 1;
                defensiveStrength = 3;
                attackingStrength = 0;
                break;
            default:
                moveCount = 0;
                defensiveStrength = 0;
                attackingStrength = 0;
                break;
        }
    }

    public String getTypeString() {
        return type;
    }
    public Player getOwner() { return owner; }
    public void setTypeString( String unitType ) { type = unitType; }
    public void setOwner( Player newOwner ) { owner = newOwner; }
    public int getMoveCount() { return moveCount; }
    public void setMoveCount(int newMoveCount) { moveCount = newMoveCount; }
    public int getDefensiveStrength() { return defensiveStrength; }
    public void setDefensiveStrength(int newDefensiveStrength) { defensiveStrength = newDefensiveStrength; }
    public int getAttackingStrength() { return attackingStrength; }


}

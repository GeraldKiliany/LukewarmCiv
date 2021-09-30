package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public  class UnitImpl implements Unit {

    String type;
    Player owner;
    int moveCount;
    int defensiveStrength;

    public UnitImpl () {
        type = null;
        owner = null;
        moveCount = 0;
        defensiveStrength = 0;
    }

    public UnitImpl(String unitType, Player unitOwner, int unitMoveCount, int unitDefensiveStrength) {
        type = unitType;
        owner = unitOwner;
        moveCount = unitMoveCount;
        defensiveStrength = unitDefensiveStrength;
    }

    public String getTypeString() {
        return type;
    }
    public Player getOwner() { return owner; }
    public void setTypeString( String unitType ) { type = unitType; }
    public void setOwner( Player newOwner ) { owner = newOwner; }
    public int getMoveCount() { return moveCount; }
    public int getDefensiveStrength() { return defensiveStrength; }
}

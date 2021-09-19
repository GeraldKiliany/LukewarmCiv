package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public  class UnitImpl implements Unit {

    String type;
    Player owner;

    public UnitImpl () {
        type = "";
        owner = null;
    }

    public UnitImpl(String unitType, Player unitOwner) {
        type = unitType;
        owner = unitOwner;
    }

    public String getTypeString() {
        return type;
    }
    public Player getOwner() { return owner; }
    public void setTypeString( String unitType ) { type = unitType; }
    public void setOwner( Player newOwner ) { owner = newOwner; }
}

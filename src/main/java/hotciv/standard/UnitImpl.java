package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public  class UnitImpl implements Unit {

    public UnitImpl(String unitType, Player unitOwner) {
        type = unitType;
        owner = unitOwner;
    }
    String type;
    Player owner;

    public String getTypeString() {
        return type;
    }
    public Player getOwner() { return owner; }
    public void setTypeString( String unitType ) {
        type = unitType;
    }
}

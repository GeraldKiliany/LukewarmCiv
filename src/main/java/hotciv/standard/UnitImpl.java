package hotciv.standard;

import hotciv.framework.Unit;

public  class UnitImpl implements Unit {

    public UnitImpl(String unitType) {
        type = unitType;
    }
    String type;

    public String getTypeString() {
        return type;
    }
}

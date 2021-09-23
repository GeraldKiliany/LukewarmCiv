package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City {
    //matt
    private Player owner;
    private int size = 1;
    private int treasury = 0;
    private Unit unitProduced;
    private Position position;

    public CityImpl(Player player,Position p){
        position = p;
        owner = player;
        unitProduced = new UnitImpl();
        unitProduced.setOwner(player);
        unitProduced.setTypeString(GameConstants.ARCHER);
    }

    public Player getOwner(){return owner;}
    public int getSize(){return size;}
    public int getTreasury(){return treasury;}
    public String getProduction(){return unitProduced.getTypeString();}
    public String getWorkforceFocus(){return null;}
    public void incrementTreasury(int a){
        treasury += a;
    }

    public Position getPosition(){return position;}

    public void setProduction(String unitType){this.unitProduced.setTypeString(unitType);}
}

package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City {
    //matt
    private Player owner;
    private int size = 1;

    public CityImpl(Player player){
        owner = player;
    }

    public Player getOwner(){return owner;}
    public int getSize(){return size;}
    public int getTreasury(){return 0;}
    public String getProduction(){return null;}
    public String getWorkforceFocus(){return null;}
}

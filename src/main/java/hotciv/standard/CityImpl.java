package hotciv.standard;

import hotciv.framework.*;

public class CityImpl implements City {
    //matt
    private Player owner;


    public CityImpl(Player player){
        owner = player;
    }

    public Player getOwner(){return owner;}
    public int getSize(){return 0;}
    public int getTreasury(){return 0;}
    public String getProduction(){return null;}
    public String getWorkforceFocus(){return null;}
}

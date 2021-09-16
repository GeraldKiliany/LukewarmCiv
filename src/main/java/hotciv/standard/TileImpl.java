package hotciv.standard;

import hotciv.framework.Tile;

public class TileImpl implements Tile {

    public TileImpl(String tileType){

        type = tileType;
    }
    String type;

   public String getTypeString(){

        return type;
   }
}

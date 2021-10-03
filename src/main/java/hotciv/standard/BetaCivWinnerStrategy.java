package hotciv.standard;

import hotciv.framework.*;

import java.util.Map;

public class BetaCivWinnerStrategy implements WinnerStrategy{
    public Player getWinner(int age, Map<Position, CityImpl> cities){
        Boolean flag = false;
        Player potentialWinner = null;
        for (int row = 0;row<GameConstants.WORLDSIZE;row++){
            for (int col = 0;col<GameConstants.WORLDSIZE;col++){
                Position p = new Position(row,col);
                if (cities.get(p) != null){
                    if (!flag){
                        potentialWinner = cities.get(p).getOwner();
                        flag = true;
                    }
                    else{
                        if (potentialWinner != cities.get(p).getOwner())
                            return null;
                    }
                }
            }
        }
        return potentialWinner;
    }
}

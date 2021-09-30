package hotciv.standard;

import hotciv.framework.*;

public class BetaCivWinnerStrategy implements WinnerStrategy{
    public Player getWinner(int age, City[][] cities){
        Boolean flag = false;
        Player potentialWinner = null;
        for (int row = 0;row<GameConstants.WORLDSIZE;row++){
            for (int col = 0;col<GameConstants.WORLDSIZE;col++){
                if (cities[row][col] != null){
                    if (!flag){
                        potentialWinner = cities[row][col].getOwner();
                        flag = true;
                    }
                    else{
                        if (potentialWinner != cities[row][col].getOwner())
                            return null;
                    }
                }
            }
        }
        return potentialWinner;
    }
}

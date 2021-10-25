package hotciv.standard;

import hotciv.framework.*;

import java.util.Map;

public class BetaCivWinnerStrategy implements WinnerStrategy{
    public Player getWinner(int age, Map<Position, CityImpl> cities){
        Boolean allCitiesSameOwner = false;
        Player potentialWinner = null;
        for(Position p : cities.keySet()) {
                if (cities.get(p) != null){
                    if (!allCitiesSameOwner){
                        potentialWinner = cities.get(p).getOwner();
                        allCitiesSameOwner = true;
                    }
                    else{
                        if (potentialWinner != cities.get(p).getOwner())
                            return null;
                    }
                }

        }
        return potentialWinner;
    }
}

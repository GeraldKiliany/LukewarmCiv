package hotciv.framework;

import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

import java.util.Map;

public interface WinnerStrategy {
    //returns null if no player has won
    public Player getWinner(int age, Map<Position, CityImpl> cities, GameImpl game);
}

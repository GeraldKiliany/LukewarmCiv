package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinnerStrategy;

import java.util.Map;

import static hotciv.standard.GameImpl.blueAttacksWon;
import static hotciv.standard.GameImpl.redAttacksWon;

public class EpsilonCivWinnerStrategy implements WinnerStrategy {
    public Player getWinner(int age, Map<Position, CityImpl> cities) {
        if (redAttacksWon >= 3) { return Player.RED; }
        if (blueAttacksWon >= 3) { return Player.BLUE; }

        return null;
    }
}

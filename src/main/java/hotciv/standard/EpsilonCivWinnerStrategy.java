package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinnerStrategy;

import java.util.Map;

public class EpsilonCivWinnerStrategy implements WinnerStrategy {
    public Player getWinner(int age, Map<Position, CityImpl> cities, GameImpl game) {
        if (game.getRedAttacksWon() >= 3) { return Player.RED; }
        if (game.getBlueAttacksWon() >= 3) { return Player.BLUE; }

        return null;
    }
}

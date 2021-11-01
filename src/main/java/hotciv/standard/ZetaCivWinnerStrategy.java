package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinnerStrategy;

import java.util.Map;

public class ZetaCivWinnerStrategy implements WinnerStrategy {
    private WinnerStrategy BetaCivWinnerStrategy, EpsilonCivWinnerStrategy, currentState;

    public ZetaCivWinnerStrategy( WinnerStrategy BetaCivWinnerStrategy, WinnerStrategy EpsilonCivWinnerStrategy ) {
        this.BetaCivWinnerStrategy = BetaCivWinnerStrategy;
        this.EpsilonCivWinnerStrategy = EpsilonCivWinnerStrategy;
        this.currentState = EpsilonCivWinnerStrategy;
    }

    public Player getWinner(int age, Map<Position, CityImpl> cities, GameImpl game) {
        if ( game.getNumberOfRoundsPassed() > 20 ) {
            currentState = EpsilonCivWinnerStrategy;
        } else {
            currentState = BetaCivWinnerStrategy;
        }
        return currentState.getWinner(age, cities, game);
    }
}

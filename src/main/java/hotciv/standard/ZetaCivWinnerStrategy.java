package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinnerStrategy;

import java.util.Map;

import static hotciv.standard.GameImpl.numberOfRoundsPassed;

public class ZetaCivWinnerStrategy implements WinnerStrategy {
    private WinnerStrategy BetaCivWinnerStrategy, EpsilonCivWinnerStrategy, currentState;

    public ZetaCivWinnerStrategy( WinnerStrategy BetaCivWinnerStrategy, WinnerStrategy EpsilonCivWinnerStrategy ) {
        this.BetaCivWinnerStrategy = BetaCivWinnerStrategy;
        this.EpsilonCivWinnerStrategy = EpsilonCivWinnerStrategy;
        this.currentState = null;
    }

    public Player getWinner(int age, Map<Position, CityImpl> cities) {
        if ( moreThan20Rounds() ) {
            //redAttacksWon = 0;
            //blueAttacksWon = 0;

            currentState = EpsilonCivWinnerStrategy;
        } else {
            currentState = BetaCivWinnerStrategy;
        }
        return currentState.getWinner(age, cities);
    }

    private boolean moreThan20Rounds() { return numberOfRoundsPassed > 20; }
}

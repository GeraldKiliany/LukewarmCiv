package hotciv.standard;

import hotciv.framework.*;

import java.util.Map;

public class AlphaCivWinnerStrategy implements WinnerStrategy {
    public Player getWinner(int age, Map<Position,CityImpl> cities, GameImpl game){return (age==-3000)?Player.RED:null;}
}

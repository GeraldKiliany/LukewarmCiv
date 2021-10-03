package hotciv.standard;

import hotciv.framework.*;

import java.util.Map;

public class AlphaCivWinnerStrategy implements WinnerStrategy {
    public Player getWinner(int age, Map<Position,CityImpl> cities){return (age==-3000)?Player.RED:null;}
}

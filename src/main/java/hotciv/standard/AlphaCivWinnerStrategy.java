package hotciv.standard;

import hotciv.framework.*;

public class AlphaCivWinnerStrategy implements WinnerStrategy {
    public Player getWinner(int age, City[][] cities){return (age==-3000)?Player.RED:null;}
}

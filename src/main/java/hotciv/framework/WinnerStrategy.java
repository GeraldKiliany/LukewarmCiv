package hotciv.framework;

public interface WinnerStrategy {
    //returns null if no player has won
    public Player getWinner(int age, City[][] cities);
}

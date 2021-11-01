package hotciv.framework;

public interface AttackDecisionStrategy {
    public boolean determineWinner(Game game, Position from, Position to);
}

package hotciv.standard;
import hotciv.framework.*;

public class TranscriptionDecorator implements Game {
    private Game game;
    boolean transcription = false;

  public TranscriptionDecorator(Game argGame){
        this.game = argGame;
    }

    public Tile getTileAt(Position p ) {
      return game.getTileAt(p);
    }

    public Unit getUnitAt(Position p ) {
      return game.getUnitAt(p);
    }

    public City getCityAt(Position p ) {
      return game.getCityAt(p);
    }

    public Player getPlayerInTurn() {
      return game.getPlayerInTurn();
    }

    public Player getWinner() {
      return game.getWinner();
    }

    public int getAge() {
      return game.getAge();
    }

    public boolean moveUnit( Position from, Position to ) {
      return game.moveUnit(from,to);
    }

    public void endOfTurn() {
      game.endOfTurn();
    }

    public void changeWorkForceFocusInCityAt( Position p, String balance ) {
      game.changeProductionInCityAt(p,balance);
    }

    public void changeProductionInCityAt( Position p, String unitType ) {
      game.changeProductionInCityAt(p,unitType);
      System.out.print(game.getPlayerInTurn() + " changed production in city at " + p.getRow() + " " + p.getColumn() + " to " + unitType);
    }

    public void performUnitActionAt( Position p ) {
      game.performUnitActionAt(p);
      System.out.print(game.getPlayerInTurn() + " performed unit action at " + p.getRow() + " " + p.getColumn());
    }

    public void advanceTurns( int numberOfTurns ) {
        game.advanceTurns(numberOfTurns);
    }

    public void placeUnitManually( Position p, String unitType, Player owner ) {
      game.placeUnitManually(p, unitType, owner);
    }


    public void setTranscription(Boolean transcribing) {
        this.transcription = transcribing;
    }
}





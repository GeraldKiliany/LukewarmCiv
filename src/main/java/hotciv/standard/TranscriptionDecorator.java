package hotciv.standard;
import hotciv.framework.*;

public class TranscriptionDecorator implements Game {
    private Game game;
    boolean transcription = true;

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
    Player winningPlayer = game.getWinner();
    if(transcription){
      System.out.print(winningPlayer + " is the winner.");
    }
      return winningPlayer;
    }

    public int getAge() {
      return game.getAge();
    }

    public boolean moveUnit( Position from, Position to ) {

      boolean successfulMove = game.moveUnit(from, to);
      if (transcription) {
        if (successfulMove) {
          System.out.print(game.getPlayerInTurn() + "successfully moved unit from " + from + " to " + to);
        } else {
          System.out.print(game.getPlayerInTurn() + "could not move unit ");
        }
      }
        return successfulMove;
      }


    public void endOfTurn() {
    if(transcription) {
      System.out.print(game.getPlayerInTurn() + "ended their turn. ");
    }
      game.endOfTurn();

    }

    public void changeWorkForceFocusInCityAt( Position p, String balance ) {

      game.changeWorkForceFocusInCityAt(p, balance);
      if (transcription) {
        System.out.print(game.getPlayerInTurn() + " changed workforce focus in city at " + p + " to " + balance);
      }
    }

    public void changeProductionInCityAt( Position p, String unitType ) {
      game.changeProductionInCityAt(p, unitType);
      if (transcription) {
        System.out.print(game.getPlayerInTurn() + " changed production in city at " + p + " to " + unitType);
      }
    }

    public void performUnitActionAt( Position p ) {
      game.performUnitActionAt(p);
      if(transcription) {
        System.out.print(game.getPlayerInTurn() + " performed unit action at " + p);
      }
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

    public boolean transcriptionOn(){
      return this.transcription;
    }
}





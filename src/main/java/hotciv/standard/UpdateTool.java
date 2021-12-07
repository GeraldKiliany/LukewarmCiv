package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class UpdateTool extends NullTool {
    private Game game;
    private int count = 0;
    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        if (count == 1){
            System.out.println("Moving unit");
            game.moveUnit(new Position(2,0), new Position(1,1));
        }
        if (count == 2){
            System.out.println("Ending turn");
            game.endOfTurn();
        }
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        count++;
    }
}

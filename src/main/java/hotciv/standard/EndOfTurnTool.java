package hotciv.standard;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class EndOfTurnTool extends NullTool {
    private DrawingEditor editor;
    private Game game;
    public EndOfTurnTool(Game g) {
        game = g;
    }
    public void mouseDown(MouseEvent e, int x, int y) {
        if(x >= GfxConstants.TURN_SHIELD_X && x <= GfxConstants.TURN_SHIELD_X + GfxConstants.TILESIZE &&
                y >= GfxConstants.TURN_SHIELD_Y && y <= GfxConstants.TURN_SHIELD_Y + GfxConstants.TILESIZE){
            game.endOfTurn();
        }
    }
}

package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class ActionTool extends NullTool {
    private DrawingEditor editor;
    private Game game;

    public ActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    public void mouseDown(MouseEvent e, int x, int y) {
        Position pos = GfxConstants.getPositionFromXY(x,y);

        if (e.isShiftDown() && game.getUnitAt(pos) != null) {
            if (game.getUnitAt(pos).getOwner() == game.getPlayerInTurn()) {
                editor.showStatus("Unit at (" + pos.getRow() + ", " + pos.getColumn() + ") performed action.");
                game.performUnitActionAt(GfxConstants.getPositionFromXY(x,y));
            } else {
                editor.showStatus("You do not own selected unit.");
            }
        } else {
            editor.showStatus("Shift-Click on unit to see Game's performAction method being called.");
        }
    }
}

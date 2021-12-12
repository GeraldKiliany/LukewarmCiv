package hotciv.standard;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class ActionTool extends NullTool {
    private DrawingEditor editor;
    private Game game;
    private boolean shiftKeyPressed = false;
    private KeyListener listener;

    public ActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    public void mouseDown(MouseEvent e, int x, int y) {
        if (shiftKeyPressed) {
            if (game.getUnitAt(GfxConstants.getPositionFromXY(x,y)) != null) {
                game.performUnitActionAt(GfxConstants.getPositionFromXY(x,y));
            }
        }
        shiftKeyPressed = false;
    }

    public void mouseUp(MouseEvent e, int x, int y) {
        editor.showStatus("Shift-Click on unit to see Game's performAction method being called.");
    }

    public void keyDown(KeyEvent evt, int key) {
        shiftKeyPressed = evt.isShiftDown();
    }
}

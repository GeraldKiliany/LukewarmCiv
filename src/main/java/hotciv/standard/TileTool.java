package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.CivDrawing;
import hotciv.view.GfxConstants;
import minidraw.framework.*;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;
import minidraw.standard.handlers.DragTracker;
import minidraw.standard.handlers.SelectAreaTracker;
import minidraw.standard.handlers.StandardRubberBandSelectionStrategy;

import java.awt.event.MouseEvent;

public class TileTool extends NullTool {

    protected DrawingEditor editor;
    protected int fAnchorX, fAnchorY;
    Game game;
    Position tilePos;

    public TileTool(DrawingEditor argEditor, Game argGame) {
        this.game = argGame;
        this.editor = argEditor;

    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor().drawing();
        model.lock();

        tilePos = GfxConstants.getPositionFromXY(x, y);
        game.setTileFocus(tilePos);
        editor.showStatus("Set tile focus to " + tilePos.toString());

       // civDrawing.tileFocusChangedAt()
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y){
        Drawing model = editor().drawing();
        model.unlock();
    }
    public DrawingEditor editor() {
        return editor;
    }
}



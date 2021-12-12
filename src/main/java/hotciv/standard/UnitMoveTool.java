package hotciv.standard;

import java.awt.event.MouseEvent;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.*;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;
import minidraw.standard.handlers.*;

public class UnitMoveTool extends AbstractTool implements Tool {

    private Game game;
    private Position from;
    private Position to;
    boolean unitSelected = false;

    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Figure draggedFigure;

    RubberBandSelectionStrategy selectionStrategy;

    public UnitMoveTool(DrawingEditor editor,
                         RubberBandSelectionStrategy selectionStrategy, Game game) {
        super(editor);
        fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
        this.selectionStrategy = selectionStrategy;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor().drawing();

        model.lock();

        draggedFigure = model.findFigure(e.getX(), e.getY());

        if (draggedFigure != null) {
            fChild = createDragTracker(draggedFigure);
        } else {
            if (!e.isShiftDown()) {
                model.clearSelection();
            }
            fChild = createAreaTracker();
        }
        fChild.mouseDown(e, x, y);

        from = GfxConstants.getPositionFromXY(x,y);
        to = null;

        if (game.getUnitAt(from) != null) {
            unitSelected = true;
            editor.showStatus("UnitMoveTool: Valid unit selected at [" + from.getRow() + ", " + from.getColumn() + "].");
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        fChild.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        fChild.mouseMove(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        editor().drawing().unlock();

        fChild.mouseUp(e, x, y);
        fChild = cachedNullTool;
        draggedFigure = null;

        if (unitSelected) {
            to = GfxConstants.getPositionFromXY(x,y);

            boolean validMove = game.moveUnit(from, to);
            if (validMove) {
                editor.showStatus("UnitMoveTool: Unit moved to [" + to.getRow() + ", " + to.getColumn() + "].");
            } else {
                editor.showStatus("UnitMoveTool: Invalid move.");
            }
        }

        unitSelected = false;
    }

    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor(), f);
    }

    protected Tool createAreaTracker() {
        return new SelectAreaTracker(editor(), selectionStrategy);
    }

}

package hotciv.standard;

import hotciv.standard.*;
import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;
import minidraw.standard.handlers.DragTracker;
import minidraw.standard.handlers.SelectAreaTracker;
import minidraw.standard.handlers.StandardRubberBandSelectionStrategy;


import java.awt.event.MouseEvent;

public class SemiCivTool extends AbstractTool implements Tool {

    private StandardRubberBandSelectionStrategy standardRubberBandSelectionStrategy;
    private DrawingEditor editor;
    private Game game;

    private Position from;
    private Position to;
    boolean unitSelected = false;
    private EndOfTurnTool endOfTurnTool;
    private UnitMoveTool unitMoveTool;
    private ActionTool actionTool;
    private TileTool tileTool;
    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Figure draggedFigure;
    RubberBandSelectionStrategy selectionStrategy;

    public SemiCivTool(DrawingEditor e, StandardRubberBandSelectionStrategy s, Game g) {
        super(e);
        this.fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
        standardRubberBandSelectionStrategy = s;
        editor = e;
        game = g;
        endOfTurnTool = new EndOfTurnTool(game);
        unitMoveTool = new UnitMoveTool(editor, standardRubberBandSelectionStrategy, game);
        actionTool = new ActionTool(editor, game);
        tileTool = new TileTool(editor, game);
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        tileTool.mouseDown(e, x, y);
        endOfTurnTool.mouseDown(e, x, y);
        actionTool.mouseDown(e, x, y);

        Drawing model = editor().drawing();

        model.lock();

        draggedFigure = model.findFigure(e.getX(), e.getY());

        if (draggedFigure != null) {
            this.fChild = createDragTracker(draggedFigure);
        } else {
            if (!e.isShiftDown()) {
                model.clearSelection();
            }
            this.fChild = createAreaTracker();
        }
        fChild.mouseDown(e, x, y);

        from = GfxConstants.getPositionFromXY(x, y);
        to = null;

        if (game.getUnitAt(from) != null) {
            unitSelected = true;
            editor.showStatus("UnitMoveTool: Valid unit selected at [" + from.getRow() + ", " + from.getColumn() + "].");
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        unitMoveTool.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        unitMoveTool.mouseMove(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        editor().drawing().unlock();


        fChild.mouseUp(e, x, y);
        fChild = cachedNullTool;
        draggedFigure = null;

        if (unitSelected) {
            to = GfxConstants.getPositionFromXY(x, y);

            boolean validMove = game.moveUnit(from, to);
            if (validMove) {
                editor.showStatus("UnitMoveTool: Unit moved to [" + to.getRow() + ", " + to.getColumn() + "].");
            } else {
                editor.showStatus("UnitMoveTool: Invalid move.");
            }
        }
    }

    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor(), f);
    }

    protected Tool createAreaTracker() {
        return new SelectAreaTracker(editor(), selectionStrategy);
    }


}
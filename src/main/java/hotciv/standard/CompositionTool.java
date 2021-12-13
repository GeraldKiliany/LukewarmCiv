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
import minidraw.standard.handlers.StandardRubberBandSelectionStrategy;


import java.awt.event.MouseEvent;

public class CompositionTool extends NullTool {

    private StandardRubberBandSelectionStrategy standardRubberBandSelectionStrategy;
    private DrawingEditor editor;
    private Game game;

    private EndOfTurnTool endOfTurnTool;
    private UnitMoveTool unitMoveTool;
    private ActionTool actionTool;
    private TileTool tileTool;

    public CompositionTool(DrawingEditor e, StandardRubberBandSelectionStrategy s,Game g){
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
        tileTool.mouseDown(e,x,y);
        endOfTurnTool.mouseDown(e,x,y);
        unitMoveTool.mouseDown(e,x,y);
        actionTool.mouseDown(e,x,y);
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y){
        unitMoveTool.mouseDrag(e,x,y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y){
        unitMoveTool.mouseMove(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        unitMoveTool.mouseUp(e, x, y);
    }
}

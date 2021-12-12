package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.RubberBandSelectionStrategy;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class CompositionTool extends AbstractTool implements Tool {

    protected DrawingEditor editor;
    protected int fAnchorX, fAnchorY;
    Game game;
    Position tilePos;

    private Position from;
    private Position to;
    boolean unitSelected = false;

    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Figure draggedFigure;
    RubberBandSelectionStrategy selectionStrategy;


    public CompositionTool(DrawingEditor editor,
                           RubberBandSelectionStrategy selectionStrategy, Game game){
        super(editor);
        fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
        this.selectionStrategy = selectionStrategy;
        this.game = game;
        this.editor = editor;

    }
    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        //End of turn functionality: click on turn shield
        if(x >= GfxConstants.TURN_SHIELD_X && x <= GfxConstants.TURN_SHIELD_X + GfxConstants.TILESIZE &&
                y >= GfxConstants.TURN_SHIELD_Y && y <= GfxConstants.TURN_SHIELD_Y + GfxConstants.TILESIZE){
            game.endOfTurn();
            editor.showStatus("Ending turn");
        }
        else {
            //Set Tile Focus functionality: click on a tile
            tilePos = GfxConstants.getPositionFromXY(x, y);
            game.setTileFocus(tilePos);
            editor.showStatus("Set tile focus to " + tilePos.toString());
        }

        //editor.drawing().findFigure(x,y);
    }


}

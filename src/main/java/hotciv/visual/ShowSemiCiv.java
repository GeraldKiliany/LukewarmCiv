package hotciv.visual;

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

/** Template code for exercise FRS 36.39.

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Computer Science Department
 Aarhus University

 This source code is provided WITHOUT ANY WARRANTY either
 expressed or implied. You may study, use, modify, and
 distribute it for non-commercial purposes. For any
 commercial use, see http://www.baerbak.com/
 */
public class ShowSemiCiv {

    public static void main(String[] args) {
        // need to make our own StubGame
        Game game = new GameImpl( new SemiCivFactory());

        // need to make our own factory
        DrawingEditor editor =
                new MiniDrawApplication( "Semi Civ Unit Integration",
                        new HotCivFactory5(game) );
        editor.open();
        editor.showStatus("");

        // TODO: Make the SemiCiv tool
        //editor.setTool( new SelectionTool(editor) );
        editor.setTool( new SemiCivTool(editor, new StandardRubberBandSelectionStrategy(), game) );
    }
}
package io;

import components.Doily;
import gui.PenStroke;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DrawingHandler extends MouseInputAdapter {

    Doily doily;
    int strokeNumber;
    boolean mousePressed;
    private List<PenStroke> lineList;
    private PenStroke currentStroke;
    private Graphics2D g2d;
    private Stack<PenStroke> undoStack;
    private Stack<PenStroke> redoStack;

    public DrawingHandler() {
        lineList = new ArrayList<PenStroke>();
        mousePressed = false;
        strokeNumber = 0;
        undoStack = new Stack<PenStroke>();
        redoStack = new Stack<PenStroke>();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D.Float point = new Point2D.Float(e.getX(), e.getY());

        PenStroke toRemove = new PenStroke();
        if (doily.isErasing()) {
            for (PenStroke stroke : lineList) {
                for (Point2D.Float linePoint : stroke) {
                    //Checks to see if any existing point in a stroke has an X and Y coordinate within the eraser's radius (its set size)
                    if (Math.abs(point.getX() - linePoint.getX()) <= doily.getPenSize() && Math.abs(point.getY() - linePoint.getY()) <= doily.getPenSize()) {
                        //Marks this to be removed, as we can't remove the stroke while in the foreach statement.
                        toRemove = stroke;
                        //Adds the stroke to be removed to the redo stack, so the user can press redo and bring the line back
                        redoStack.add(toRemove);
                        strokeNumber--;
                    }
                    //The same as above, but it checks for points that have been reflected too.
                    if (doily.isReflectSet() && Math.abs(doily.getWidth() - point.getX() - linePoint.getX()) <= doily.getPenSize() && Math.abs(point.getY() - linePoint.getY()) <= doily.getPenSize()) {
                        toRemove = stroke;
                        redoStack.add(toRemove);
                        strokeNumber--;
                    }
                }
            }
            lineList.remove(toRemove);
        } else {
            //If erase mode is not set, add the current point to the stroke being drawn.
            currentStroke.addPoint(point);
            lineList.set(strokeNumber, currentStroke);
        }

        doily.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        doily = (Doily) e.getSource();
        g2d = (Graphics2D) doily.getGraphics();
        currentStroke = new PenStroke();
        currentStroke.setColour(doily.getColour());
        currentStroke.setThickness(doily.getPenSize());
        lineList.add(currentStroke);

        if (doily.isErasing()) {

        } else {
            currentStroke.addPoint(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (doily.isErasing()) {

        } else {
            lineList.set(strokeNumber, currentStroke);
            doily.repaint();
        }
        undoStack.add(currentStroke);
        strokeNumber++;
    }

    public List<PenStroke> getLines() {
        return lineList;
    }

    public void clearStrokes() {
        lineList = new ArrayList<PenStroke>();
        strokeNumber = 0;
        undoStack.clear();
        redoStack.clear();
        doily.repaint();
    }

    public void undoLast() {
        if (undoStack.empty()) {
            return;
        }
        PenStroke toRemove = undoStack.pop();
        lineList.remove(toRemove);
        redoStack.add(toRemove);
        doily.repaint();
        strokeNumber--;
    }

    public void redoLast() {
        if (redoStack.empty()) {
            return;
        }
        PenStroke toAdd = redoStack.pop();
        lineList.add(toAdd);
        undoStack.add(toAdd);
        doily.repaint();
        strokeNumber++;
    }
}

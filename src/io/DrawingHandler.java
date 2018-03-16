package io;

import components.Doily;
import gui.PenStroke;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DrawingHandler extends MouseInputAdapter {

    private Doily doily;
    private int strokeNumber= 0;
    private List<PenStroke> lineList = new ArrayList<>();
    private PenStroke currentStroke;
    private Stack<PenStroke> undoStack = new Stack<>();
    private Stack<PenStroke> redoStack = new Stack<>();

    /**
     * Implements our logic for when the mouse is used.
     * Case 1: Erasing - We iterate through every single Point2D.Float making up the drawing, we then see if our mouse is within the eraser's size
     * from the Point2D's X and Y coordinates, including those in every repeated sector of the doily, or reflections.
     * Case 2: Drawing - Much easier! We just add the mouse point to the current pen stroke.
     * @param mouse: MouseEvent causing the method to be triggered
     */
    @Override
    public void mouseDragged(MouseEvent mouse) {

        PenStroke toRemove = new PenStroke();
        if (doily.isErasing()) {
            //Boolean to track if anything is removed. Ensures our tracked number of strokes on the Doily is accurate.
            boolean removed = false;
            for (PenStroke stroke : lineList) {
                for (Point2D.Float linePoint : stroke) {
                    //For-loop to check the mouse's coordinates against every sector repetition of a stroke or its reflection.
                    for (double i = doily.getDegreeDifference(); i <= 360; i += doily.getDegreeDifference()) {
                        //Rotates the Point2D coordinates and stores it in a new temporary Point2D used for comparison.
                        Point2D.Float strokePoint = rotatePoint(linePoint, i);

                        //Checks to see if any existing point in a stroke has an X and Y coordinate within the eraser's radius (its set size)
                        if (Math.abs(mouse.getX() - strokePoint.getX()) <= doily.getPenSize() && (Math.abs(mouse.getY() - strokePoint.getY()) <= doily.getPenSize())) {
                            //Marks this to be removed, as we can't remove the stroke while in the foreach statement.
                            toRemove = stroke;
                            //Adds the stroke to be removed to the redo stack, so the user can press redo and bring the line back
                            redoStack.add(toRemove);
                            removed = true;
                        }
                        //The same as above, but it checks for points that have been reflected too.
                        if (doily.isReflectSet() && Math.abs(doily.getWidth() - mouse.getX() - strokePoint.getX()) <= doily.getPenSize() && Math.abs(mouse.getY() - strokePoint.getY()) <= doily.getPenSize()) {
                            toRemove = stroke;
                            redoStack.add(toRemove);
                            removed = true;
                        }
                    }
                }
            }
            //If any stroke should be removed, remove it and reduce the stroke count.
            if (removed) {
                lineList.remove(toRemove);
                strokeNumber--;
            }
        } else {
            //If erase mode is not set, add the current point to the stroke being drawn.
            currentStroke.addPoint(mouse.getX(), mouse.getY());
            lineList.set(strokeNumber, currentStroke);
        }

        doily.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Make sure that we have our Doily and g2d references are initialised
        doily = (Doily) e.getSource();
        //Initialise our current stroke, set its colour and thickness.
        currentStroke = new PenStroke();
        currentStroke.setColour(doily.getColour());
        currentStroke.setThickness(doily.getPenSize());
        lineList.add(currentStroke);

        if (!doily.isErasing()) {
            currentStroke.addPoint(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!doily.isErasing()) {
            //Once we release the mouse, update our stroke that we first set when the mouse was pressed down.
            lineList.set(strokeNumber, currentStroke);
            undoStack.add(currentStroke);
            strokeNumber++;
            doily.repaint();
        }
    }

    /**
     * Returns an ArrayList containing all the doily's pen strokes.
     * @return List<PenStroke> : ArrayList of pen strokes
     */
    public List<PenStroke> getLines() {
        return lineList;
    }

    /**
     * Functionality for clear button. Removes all strokes from the Doily
     */
    public void clearStrokes() {
        lineList = new ArrayList<>();
        strokeNumber = 0;
        undoStack.clear();
        redoStack.clear();
        doily.repaint();
    }

    /**
     * Functionality for undo button. Will push removed strokes to a stack to allow them to be later added back with the redo button.
     */
    public void undoLast() {
        //Do nothing if the stack is empty.
        if (undoStack.empty()) {
            return;
        }
        PenStroke toRemove = undoStack.pop();
        lineList.remove(toRemove);
        redoStack.add(toRemove);
        doily.repaint();
        strokeNumber--;
    }

    /**
     * Functionality for redo button. Will allow previously removed strokes with undo button to be redraw.
     */
    public void redoLast() {
        //Do nothing if the stack is empty
        if (redoStack.empty()) {
            return;
        }
        PenStroke toAdd = redoStack.pop();
        lineList.add(toAdd);
        undoStack.add(toAdd);
        doily.repaint();
        strokeNumber++;
    }

    /**
     * Rotates a point in respect to the Doily's centre point by theta degrees.
     * @param linePoint : Point2D.Float point to rotate
     * @param theta : Angle in degrees to rotate the Point2D.Float by
     * @return Rotated Point2D.Float object
     */
    private Point2D.Float rotatePoint(Point2D.Float linePoint, double theta) {
        AffineTransform rotation = new AffineTransform();
        rotation.rotate(Math.toRadians(theta), doily.getCentreX(), doily.getCentreY());
        Point2D.Float strokePoint = new Point2D.Float();
        rotation.transform(linePoint, strokePoint);
        return strokePoint;
    }
}
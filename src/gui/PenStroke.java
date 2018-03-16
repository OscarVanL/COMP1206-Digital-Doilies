package gui;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PenStroke implements Iterable<Point2D.Float> {
    private Color colour;
    private int thickness;
    private List<Point2D.Float> points = new ArrayList<>();

    /**
     * Adds a new Point2D.Float element to the current Pen Stroke
     * @param x : X Coordinate of point
     * @param y : Y Coordinate of point
     */
    public void addPoint(float x, float y) {
        points.add(new Point2D.Float(x, y));
    }

    /**
     * Sets the current pen stroke's colour
     * @param colour : Color object to set as the pen's colour
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Gets the current pen stroke's colour
     * @return Color : Color object for pen's colour
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Sets the pen thickness for this pen stroke
     * @param thickness : Integer thickness of pen
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     * Gets the pen's thickness for this stroke
     * @return int : Integer thickness of pen
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * Returns an iterator to iterate through all the Point2D.Floats in this pen stroke
     * @return Iterator<Point2D.Float> : Iterator to allow us to iterate through the points
     */
    @Override
    public Iterator<Point2D.Float> iterator() {
        return new PointIterator();
    }

    /**
     * An implementation of the Iterator interface, allowing us to use a for each loop on our pen stroke
     */
    class PointIterator implements Iterator<Point2D.Float> {

        private int index = 0;

        /**
         * Returns whether there is another element
         * @return boolean : True if there is another element, False if there is not.
         */
        public boolean hasNext() {
            return index < points.size();
        }

        /**
         * Gets the next element in our List
         * @return Point2D.Float : Next element in our list, a Point2D.Float
         */
        public Point2D.Float next() {
            return points.get(index++);
        }
    }
}

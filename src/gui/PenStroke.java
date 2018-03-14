package gui;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PenStroke implements Iterable<Point2D.Float> {
    private Color colour;
    private int thickness;
    private List<Point2D.Float> points = new ArrayList<Point2D.Float>();

    public void addPoint(float x, float y) {
        points.add(new Point2D.Float(x, y));
    }
    public void addPoint(Point2D.Float point) {
        points.add(point);
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getThickness() {
        return thickness;
    }

    @Override
    public Iterator<Point2D.Float> iterator() {
        return new PointIterator();
    }

    class PointIterator implements Iterator<Point2D.Float> {

        private int index = 0;

        public boolean hasNext() {
            return index < points.size();
        }

        public Point2D.Float next() {
            return points.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove from PenStroke not supported operation");

        }
    }
}

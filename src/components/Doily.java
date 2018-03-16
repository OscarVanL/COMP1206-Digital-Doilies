package components;
import gui.Gallery;
import gui.PenStroke;
import io.DrawingHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Doily extends JPanel {
	private int height;
	private int width;
	private Gallery gallery;
	private double radius;
	private int centreX;
	private int centreY;
	private DrawingHandler drawingHandler;
	private Color colour = Color.WHITE;
	private boolean eraserSet = false;
	private int penSize = 1;
	private int sectors = 0;
	private boolean linesVisible = false;
	private boolean reflectSet = false;
	private BufferedImage doilyImage;
	
	public Doily(int width, int height, double diameter, Gallery gallery) {
		this.width = width;
		this.height = height;
		this.gallery = gallery;
		radius = diameter / 2;
		centreX = width/2;
		centreY = height/2;
		doilyImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		setPreferredSize(new Dimension(width, height));
		drawingHandler = new DrawingHandler();
		addMouseListener(drawingHandler);
		addMouseMotionListener(drawingHandler);
	}

	//Paints the doily.
	public void paint(Graphics g) {
		displaySectorLines(g);
		displayStrokes(g);
	}

	/**
	 * Displays the relevant number of Sector Lines if more than 1 sector is set, and if these are set to display.
	 * @param g : Graphics instance of Doily
	 */
	private void displaySectorLines(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		if (linesVisible) {
			g.setColor(Color.WHITE);
		}

		if (sectors > 1) {
			//Truncate to 2 decimal places, otherwise final sector line does not draw in some cases
			double degreeDifference = Math.floor(360 / (float) sectors * 100) / 100;
			//For-loop is shifted by 90 degrees to improve sector line visuals
			for (double i = 90 + degreeDifference; i <= 450; i += degreeDifference) {
				int endX = centreX + (int) (Math.cos(Math.toRadians(i)) * radius);
				int endY = centreX + (int) (Math.sin(Math.toRadians(i)) * radius);
				g.drawLine(centreX, centreY, endX, endY);
			}
		}
	}

	/**
	 * Draws the pen strokes, for each stroke it is repeated across each sector and also reflected if set.
	 * @param g : Graphics object passed by the paint() method (which calls this method).
	 */
	private void displayStrokes(Graphics g) {
		Point2D.Float lastPoint = null;
		Point2D.Double reflectedPoint = null;
		Point2D.Double lastReflectedPoint = null;

		//For each stroke in our DrawingHandler (all strokes)
		for (PenStroke stroke : drawingHandler.getLines()) {

			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(stroke.getColour());

			//For-loop to enable repetition across sectors
			for (int i=0; i<=sectors; i++) {
				//Rotate our g2d instance by the relevant number of degrees
				g2d.rotate(Math.toRadians(getDegreeDifference()), centreX, centreY);

				//For each coordinate point in each of our Strokes
				for (Point2D.Float point : stroke) {
					//We define a 'last point' for each stroke, this is so we can join the points together to form curvy lines.
					//If this is not yet defined, just define it as itself for now.
					if (lastPoint == null) {
						lastPoint = point;
					}
					g2d.setStroke(new BasicStroke(stroke.getThickness()));
					Line2D.Float line = new Line2D.Float(lastPoint, point);
					g2d.draw(line);

					//If we have the reflect checkbox on, the drawing of reflected points is handled here.
					if (reflectSet) {
						reflectedPoint = new Point2D.Double((width - point.getX()), point.getY());
						if (lastReflectedPoint == null) {
							lastReflectedPoint = reflectedPoint;
						}
						Line2D.Float reflectedLine = new Line2D.Float(lastReflectedPoint, reflectedPoint);
						g2d.draw(reflectedLine);
					}

					//Update the last point
					lastPoint = point;
					lastReflectedPoint = reflectedPoint;
				}
				//After finishing a stroke, remove the last point, so that different strokes aren't connected together
				lastPoint = null;
				lastReflectedPoint = null;
			}
			//Dispose and create a new g2d object between strokes, as they may have different colours and thicknesses.
			g2d.dispose();
		}
	}

	/**
	 * Gets the doily's central X coordinate.
	 * @return int : Doily's central X Coordinate
	 */
	public int getCentreX() {
		return centreX;
	}

	/**
	 * Gets the doily's central Y coordinate.
	 * @return int : Doily's central Y Coordinate
	 */
	public int getCentreY() {
		return centreY;
	}

	/**
	 * Sets the pen colour, called by the ControlPanel when the user selects a new colour
	 * @param c : Colour passed from the ControlPanel
	 */
	public void setColour(Color c) {
		this.colour = c;
	}

	/**
	 * Returns the colour that the pen is set to
	 * @return Color : Current colour that the pen is set to
	 */
	public Color getColour() {
		return this.colour;
	}

	/**
	 * Sets the state of the eraser
	 * @param eraser : True if the eraser should be set, False if the pen should be used instead
	 */
	public void setEraser(boolean eraser) {
		this.eraserSet = eraser;
	}

	/**
	 * Gets whether the eraser is being used
	 * @return boolean : True if the eraser is being used, False if the pen is being used
	 */
	public boolean isErasing() {
		return eraserSet;
	}

	/**
	 * Sets the size of the pen used for drawing
	 * @param size : Integer size of the pen
	 */
	public void setPenSize(int size) {
		this.penSize = size;
	}

	/**
	 * Gets the size of the pen
	 * @return int : Integer size of the pen
	 */
	public int getPenSize() {
		return penSize;
	}

	/**
	 * Sets whether the doily's sector lines should be visible
	 * @param linesVisible : True if the lines should be visible, False if they should not.
	 */
	public void setLinesVisible(boolean linesVisible) {
		this.linesVisible = linesVisible;
		this.repaint();
	}

	/**
	 * Returns whether the doily's sector lines are visible or not
	 * @return boolean : True if the lines are visible, False if they are not
	 */
	public boolean linesVisible() {
		return linesVisible;
	}

	/**
	 * Sets whether reflection should occur inside sectors
	 * @param reflectSet : True if reflection should be enabled, False if reflection should be disabled
	 */
	public void setReflect(boolean reflectSet) {
		this.reflectSet = reflectSet;
		this.repaint();
	}

	/**
	 * Gets whether there is reflection inside sectors
	 * @return boolean : True if reflection is enabled, False if reflection is not enabled
	 */
	public boolean isReflectSet() {
		return reflectSet;
	}

	/**
	 * Sets the number of sectors to display in the doily
	 * @param sectors : Integer number of sectors to display
	 */
	public void setSectors(int sectors) {
		this.sectors = sectors;
		this.repaint();
	}

	/**
	 * Gets the number of sectors inside the doily
	 * @return int : Integer number of sectors in the doily
	 */
	public int getSectors() {
		return sectors;
	}

	/**
	 * Gets the degrees separating each of the sectors. For example, 3 sectors mean a 120 degree difference
	 * @return double : Degrees of separation between sectors
	 */
	public double getDegreeDifference() {
		double degreeDifference = 360;
		if (sectors > 1) {
			degreeDifference = 360 / (float) sectors;
		}
		return degreeDifference;
	}

	/**
	 * Undoes a drawing operation
	 */
	public void undo() {
		drawingHandler.undoLast();
	}

	/**
	 * Redoes an undo operation or reverses an erase
	 */
	public void redo() {
		drawingHandler.redoLast();
	}

	/**
	 * Clears all content from the current doily being edited. Leaves gallery doilies unaffected.
	 */
	public void clear() {
		drawingHandler.clearStrokes();
	}

	/**
	 * Saves the current doily to the gallery
	 */
	public void save() {
	    Graphics2D g2d = doilyImage.createGraphics();
	    this.paintAll(g2d);
	    gallery.saveImage(doilyImage);
	}
}

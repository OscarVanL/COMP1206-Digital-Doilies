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
	private Color colour = Color.WHITE;;
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
	 * Displays the relevant number of Sector Lines if more than 1 sectors is set, and if these are set to display.
	 * @param g : Graphics instance of Doily
	 */
	private void displaySectorLines(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, height, width);

		if (linesVisible) {
			g.setColor(Color.WHITE);
		}

		if (sectors > 1) {
			//Truncate to 2 decimal places, otherwise final sector line does not draw in some cases
			double degreeDifference = Math.floor(360 / (float) sectors * 100) / 100;
			for (double i = 90 + degreeDifference; i <= 450; i += degreeDifference) {
				System.out.println(i);
				int endX = centreX + (int) (Math.cos(Math.toRadians(i)) * radius);
				int endY = centreX + (int) (Math.sin(Math.toRadians(i)) * radius);
				g.drawLine(centreX, centreY, endX, endY);
			}
		}
	}

	private void displayStrokes(Graphics g) {
		Point2D.Float lastPoint = null;
		Point2D.Double reflectedPoint = null;
		Point2D.Double lastReflectedPoint = null;

		//For each stroke in our DrawingHandler (all strokes)
		for (PenStroke stroke : drawingHandler.getLines()) {

			double degreeDifference = 360;
			if (sectors > 1) {
				degreeDifference = 360 / (float) sectors;
			}

			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(stroke.getColour());

			//For-loop to enable repetition across sectors
			for (double i = degreeDifference; i <= 360; i += degreeDifference) {
				//Rotate our g2d instance by the relevant number of degrees
				g2d.rotate(Math.toRadians(degreeDifference), centreX, centreY);

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

	public void setColour(Color c) {
		this.colour = c;
	}

	public Color getColour() {
		return this.colour;
	}

	public void setEraser(boolean eraser) {
		this.eraserSet = eraser;
	}

	public boolean isErasing() {
		return eraserSet;
	}

	public void setPenSize(int size) {
		this.penSize = size;
	}

	public int getPenSize() {
		return penSize;
	}

	public void setLinesVisible(boolean linesVisible) {
		this.linesVisible = linesVisible;
		this.repaint();
	}

	public boolean linesVisible() {
		return linesVisible;
	}

	public void setReflect(boolean reflectSet) {
		this.reflectSet = reflectSet;
		this.repaint();
	}

	public boolean isReflectSet() {
		return reflectSet;
	}

	public void setSectors(int sectors) {
		this.sectors = sectors;
		this.repaint();
	}

	public int getSectors() {
		return sectors;
	}

	public void undo() {
		drawingHandler.undoLast();
	}

	public void redo() {
		drawingHandler.redoLast();
	}

	public void clear() {
		drawingHandler.clearStrokes();
	}

	public void save() {
	    Graphics2D g2d = doilyImage.createGraphics();
	    this.paintAll(g2d);
	    gallery.saveImage(doilyImage);
	}
}

package components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Doily extends Component implements MouseListener {
	private int height;
	private int width;
	private double radius;
	private int centreX;
	private int centreY;
	private int sectors;
	private boolean linesVisible;
	private boolean reflectSet;
	
	public Doily(int width, int height, double diameter) {
		this.width = width;
		this.height = height;
		this.radius = diameter / 2;
		this.centreX = width/2;
		this.centreY = height/2;
		this.sectors = 0;
		this.linesVisible = false;
		this.reflectSet = false;
		this.setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
		setListener();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, height, width);
		if (linesVisible) {
			g.setColor(Color.WHITE);
		}
		if (sectors > 1) {
			int degreeDifference = 360 / sectors;
			for (int i = 0; i < 360; i += degreeDifference) {
				int endX = centreX + (int) (Math.cos(Math.toRadians(i)) * radius);
				int endY = centreX + (int) (Math.sin(Math.toRadians(i)) * radius);
				g.drawLine(centreX, centreY, endX, endY);
			}
		}
	}

	private void setListener() {

	}

	public void setSectors(int sectors) {
		this.sectors = sectors;
		this.repaint();
	}

	public void setLinesVisible(boolean linesVisible) {
		this.linesVisible = linesVisible;
		this.repaint();
	}

	public void setReflect(boolean reflectSet) {
		this.reflectSet = reflectSet;
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed");

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}

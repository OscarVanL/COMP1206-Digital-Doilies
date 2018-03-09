package components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

public class Doily extends Component {
	private int height;
	private int width;
	private int diameter;
	
	public Doily(int width, int height, int diameter) {
		this.width = width;
		this.height = height;
		this.diameter = diameter;
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, height, width);
	}
}

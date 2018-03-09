import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

import components.Doily;
import gui.ControlPanel;

public class UserInterface {
	private JFrame frame;
	private Container controlContainer;
	private Container galleryContainer;
	private int width;
	private int height;
	
	public static void main(String args[]) {
		UserInterface ui = new UserInterface(800, 800);
	}
	
	UserInterface(int width, int height) {
		this.width = width;
		this.height = height;
		this.frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		Doily doily = new Doily(width, height/*(int)(height*0.75)*/, width);
		ControlPanel control = new ControlPanel(width, (int)(height*0.15));
		frame.add(doily, BorderLayout.CENTER);
		frame.add(control, BorderLayout.NORTH);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

}

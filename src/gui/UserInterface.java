package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import components.Doily;

public class UserInterface {
	private JFrame frame;
	private Container container;
	
	public static void main(String args[]) {
		UserInterface ui = new UserInterface(750, 800);
	}
	
	UserInterface(int width, int height) {
		this.frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		Doily doily = new Doily(width, width, (width * 0.9));
		ControlPanel control = new ControlPanel(width, (int)(height*0.14), doily);
		Gallery gallery = new Gallery((int)(height*0.2), (int)(height*0.91));
		container.add(control, BorderLayout.NORTH);
		container.add(doily, BorderLayout.CENTER);
		container.add(gallery, BorderLayout.EAST);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}


}

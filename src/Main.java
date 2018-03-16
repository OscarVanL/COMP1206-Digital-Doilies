
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import components.Doily;
import gui.ControlPanel;
import gui.Gallery;

public class Main {
	
	public static void main(String args[]) {
		Main ui = new Main();
		JFrame frame = new JFrame("Doily Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		Gallery gallery = new Gallery(160, 675);
		Doily doily = new Doily(750, 750, 680, gallery);
		ControlPanel control = new ControlPanel(750, 125, doily);
		container.add(control, BorderLayout.NORTH);
		container.add(doily, BorderLayout.CENTER);
		container.add(gallery, BorderLayout.EAST);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}


}

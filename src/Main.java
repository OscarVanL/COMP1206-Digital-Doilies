
import java.awt.*;
import javax.swing.JFrame;
import components.Doily;
import gui.ControlPanel;
import gui.Gallery;

public class Main {
	
	public static void main(String args[]) {
		JFrame window = new JFrame("Doily Generator");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("doily-icon.png"));
		Container container = window.getContentPane();
		container.setLayout(new BorderLayout());

		Gallery gallery = new Gallery(160, 675);
		Doily doily = new Doily(750, 750, 700, gallery);
		ControlPanel control = new ControlPanel(750, 125, doily);
		container.add(control, BorderLayout.NORTH);
		container.add(doily, BorderLayout.CENTER);
		container.add(gallery, BorderLayout.EAST);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}

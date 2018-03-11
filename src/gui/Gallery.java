package gui;

import javax.swing.*;
import java.awt.*;

public class Gallery extends Container {

    private int width;
    private int height;

    public Gallery(int width, int height) {
        this.width = width;
        this.height = height;
        this.setLayout(new BorderLayout());


        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setPreferredSize(new Dimension(width, height));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i=0; i<12; i++) {
            JPanel contentBox = new JPanel();
            contentBox.add(Box.createRigidArea(new Dimension((width - 50), (width - 50))));
            contentBox.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
            panel.add(contentBox);
            panel.add(Box.createRigidArea(new Dimension(0,5)));
        }

        JButton remove = new JButton("Remove");

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(remove, BorderLayout.SOUTH);

        remove.addActionListener(e -> System.out.println("Redo button pressed"));
    }

}

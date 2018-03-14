package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GalleryImage extends JPanel {

    private JLabel content;
    private Image scaledImage;
    private boolean imageSet;
    private int usableDimension;

    GalleryImage(int usableDimension) {
        this.usableDimension = usableDimension;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension (usableDimension-6, usableDimension-6));
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        content = new JLabel();
        add(content, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Handles double clicking of a gallery image (to open it)
                if (e.getClickCount() == 2) {
                    System.out.println("Double click!!!!");
                }
            }
        });
    }

    /**
     * Sets the image to be displayed in the frame. This is resized to the frame's usableDimensions
     * @param image : BufferedImage containing non-scaled/resized image of doily.
     */
    public void setImage(BufferedImage image) {
        imageSet = true;
        this.scaledImage = image.getScaledInstance(usableDimension, usableDimension, Image.SCALE_SMOOTH);
        content.setIcon(new ImageIcon(scaledImage));
        repaint();
    }

    public void removeImage() {
        imageSet = false;
        content.setIcon(null);
        repaint();
    }

    public void setBorderColour(Color c) {
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, c));
    }

    public boolean imageSet() {
        return imageSet;
    }

}

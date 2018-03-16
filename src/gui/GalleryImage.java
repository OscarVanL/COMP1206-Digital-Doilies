package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GalleryImage extends JPanel {

    private JLabel content = new JLabel();
    private BufferedImage nonScaledImage;
    private Image scaledImage;
    private boolean imageSet = false;
    private int usableDimension;

    /**
     * Creates a new empty GalleryImage, one of twelve in the Gallery.
     * @param usableDimension : integer size of the GalleryImage, it is square, so both height and width are this size.
     */
    GalleryImage(int usableDimension) {
        this.usableDimension = usableDimension;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension (usableDimension-6, usableDimension-6));
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        add(content, BorderLayout.CENTER);

        //Allows us to double click on saved gallery images to open it to view larger in a new window.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Handles double clicking of a gallery image (to open it)
                if (e.getClickCount() == 2 && imageSet) {
                    JFrame galleryPreview = new JFrame("Gallery Preview");
                    galleryPreview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    galleryPreview.getContentPane().add(new JLabel(new ImageIcon(nonScaledImage)));
                    galleryPreview.pack();
                    galleryPreview.setVisible(true);
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
        nonScaledImage = image;
        this.scaledImage = image.getScaledInstance(usableDimension, usableDimension, Image.SCALE_SMOOTH);
        content.setIcon(new ImageIcon(scaledImage));
        repaint();
    }

    /**
     * Removes any doily image contained in this gallery slot, and repaints it to show an empty gallery slot.
     */
    public void removeImage() {
        imageSet = false;
        content.setIcon(null);
        repaint();
    }

    /**
     * Sets the colour of the border around the image. Blue is used to indicate it is selected, Black is used to indicate it is not.
     * @param c : Colour to set the border to
     */
    public void setBorderColour(Color c) {
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, c));
    }

    /**
     * Returns whether the Gallery slot has a set image
     * @return boolean : True if there is an image, False if there is not
     */
    public boolean imageSet() {
        return imageSet;
    }

}

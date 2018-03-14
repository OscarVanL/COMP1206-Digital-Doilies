package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Gallery extends JPanel {
    private int usableDimension;
    private int storedImages = 0;
    private List<GalleryImage> frames = new ArrayList<GalleryImage>();
    private GalleryImage selectedGallery;
    private JPanel panel;

    public Gallery(int width, int height) {
        setLayout(new BorderLayout());

        //Initialise the Scrolling Pane
        panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(width, height));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //Gets the space remaining after the space occupied by the scroll bar.
        usableDimension = width - (int) scrollPane.getVerticalScrollBar().getPreferredSize().getWidth();
        selectedGallery = new GalleryImage(usableDimension);

        //Draws our (empty) frames.
        drawAllFrames();

        JButton remove = new JButton("Remove");
        //Sets the listener and logic for removing GalleryImages
        remove.addActionListener(e -> {
            int removedImageIndex = frames.indexOf(selectedGallery);
            //Once GalleryImages become empty, filled GalleryImages take their place in the gallery queue
            for (int i=removedImageIndex; i<storedImages-1; i++) {
                System.out.println("test");
                if (frames.get(i+1).imageSet()) {
                    System.out.println("swapping");
                    frames.set(i, frames.get(i+1));
                    frames.set(i+1, selectedGallery);
                }
            }
            //Only remove an image and deduct from the storedImages if the user has actually selected a frame.
            if (selectedGallery.imageSet()) {
                selectedGallery.removeImage();
                storedImages--;
            }
            //Set the border colour to Black, as the image has been deselected.
            selectedGallery.setBorderColour(Color.BLACK);
            drawAllFrames();
            System.out.println("Remove button pressed");
        });


        this.add(scrollPane, BorderLayout.CENTER);
        this.add(remove, BorderLayout.SOUTH);
    }

    /**
     * Adds all of the GalleryImages to the Gallery and sets their mouse listener.
     */
    public void drawAllFrames() {
        panel.removeAll();
        for (int i=0; i<12; i++) {
            GalleryImage galleryImage = new GalleryImage(usableDimension);
            frames.add(galleryImage);

            panel.add(frames.get(i));
            panel.add(Box.createRigidArea(new Dimension(0,5)));

            //Mouse listener that handles the colouring of selected frame borders, and sets a variable of the selected GalleryImage
            galleryImage.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (GalleryImage image : frames) {
                        image.setBorderColour(Color.BLACK);
                    }
                    galleryImage.setBorderColour(Color.BLUE);
                    selectedGallery = galleryImage;
                }
            });
        }
    }

    /**
     * Stores Doilies that have been saved into GalleryImages
     * @param image: BufferedImage containing non-scaled/resized image of doily.
     */
    public void saveImage(BufferedImage image) {
        //Condition prevents us storing more than 12 images
        if (storedImages < 12) {
            frames.get(storedImages).setImage(image);
            storedImages++;
            repaint();
        } else {
            //Shows a dialog box telling the user that the gallery is full.
            JOptionPane.showMessageDialog(this, "The Gallery is full. Consider removing existing images and try again.");
        }
    }

}
